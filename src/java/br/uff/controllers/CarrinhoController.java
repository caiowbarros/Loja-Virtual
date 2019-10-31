/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.controllers;

import br.uff.models.Address;
import br.uff.models.BaseModel;
import br.uff.models.Cart;
import br.uff.models.CartsProducts;
import br.uff.sql.ConnectionManager;
import br.uff.sql.SqlManager;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 *
 * @author HP
 */
@WebServlet(name = "CarrinhoController", urlPatterns = {"/CarrinhoController"})
public class CarrinhoController extends HttpServlet {
    @Override
    public void init() {
        try {
            ConnectionManager.connect();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CarrinhoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void destroy() {
        try {
            ConnectionManager.close();
        } catch (SQLException ex) {
            Logger.getLogger(CarrinhoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // pega sessao
        HttpSession session = request.getSession();
        try {
            SqlManager sql = new SqlManager(Cart.class);
            SqlManager sqlCartsProducts = new SqlManager(CartsProducts.class);
            String carrinhoId = null;
            Cookie[] cookies = request.getCookies();
            // procura carrinhoId nos cookies setados
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie cookie = cookies[i];
                    if (cookie.getName().equals("carrinhoId")) {
                        carrinhoId = cookie.getValue().toString();
                        break;
                    }
                }
            }

            String userId = null;
            // recupera userId e se tiver carrinhoId como cookie mas n tiver userId cadastrado p esse carrinho id cadastra user
            if (session.getAttribute("userId") != null) {
                userId = session.getAttribute("userId").toString();
                try {
                    // verifica se carrinhoId recuperado pertence a outro usuario
                    if (carrinhoId != null) {
                        Cart cart = (Cart) sql.find(Integer.parseInt(carrinhoId));
                        if (cart != null) {
                            if (!String.valueOf(cart.getUserId()).equals(userId)) {
                                // define carrinhoId como null pois pertence a outro usuario
                                carrinhoId = null;
                            }
                        }
                    }
                    // pega id de carrinho se n estiver vendido, se user id bater e se n for null
                    // String[] bindCarrinhoUser = {userId};
                    // String carrinhoUser = db.dbValor("id", "carts", "user_id=? AND id not in (SELECT cart_id FROM sales)", bindCarrinhoUser);
                    ArrayList<BaseModel> carts = sql.select("carts.id")
                                                    .addJoins("left join sales s on s.cart_id = carts.id")
                                                    .where("s.id is null and carts.user_id=" + userId)
                                                    .run();
                    int id = -1;
                    if (carts.size() > 0){
                        Cart cart = (Cart) carts.get(0);
                        id = (int) cart.getAttribute("id");
                    }
                    if (id >= 0) {
                        if (String.valueOf(id).equals(carrinhoId)) {
                            // define aviso de recuperaçao de carrinho existente
                            // session.setAttribute("msg", "Um carrinho cadastrado anteriormente para seu usuário foi recuperado!");
                        } else {
                            HashMap<String, Object> attrs = new HashMap();
                            attrs.put("user_id", Integer.parseInt(userId));
                            sql.update().set(attrs).where("id=" + carrinhoId + " and user_id is null").run();
                        }
                    }
                } catch (Exception ed) {
                    throw new Exception(ed.getMessage());
                }
            } else {
                response.sendRedirect("UserController");
                return;
            }

            //verifica se carrinhoId eh um numero
            if (carrinhoId != null) {
                try {
                    int validador = Integer.valueOf(carrinhoId);
                } catch (Exception ec) {
                    carrinhoId = null;
                }
            }

            // verifica se carrinhoId ja esta vendido
            if (carrinhoId != null) {
                try {
                    boolean cartExists = sql.select().where("id="+carrinhoId).exists();
                    
                    if (cartExists) {
                        int carts = sql.select().where("id in (SELECT cart_id FROM sales) and id=" + carrinhoId).count();
                        //se encontrou carrinho vendido define carrinhoId como null
                        if (carts > 0) carrinhoId = null;
                    } else {
                        // se n tem carrinho no bd define como null
                        carrinhoId = null;
                    }
                } catch (Exception ed) {
                    throw new Exception(ed.getMessage());
                }
            }

            // se n achou carrinhoId define
            if (carrinhoId == null) {
                // formato da data p mysql
                String pattern = "yyyy-MM-dd HH:mm:ss";
                // define novo simpleDateFormat
                DateFormat df = new SimpleDateFormat(pattern);
                // pega o time pelo calendar
                Date data = Calendar.getInstance().getTime();
                // monta string createdAt
                String createdAt = df.format(data);
                String ip = request.getRemoteAddr().toString();
                try {
                    HashMap<String, Object> attrs = new HashMap() {{
                        put("ip", ip);
                        put("created_at", createdAt);
                    }};
                    attrs.put("user_id", Integer.parseInt((String) userId));
                    BaseModel cart = sql.insert().values(attrs).run();
                    carrinhoId = String.valueOf((int) cart.getAttribute("id"));
                } catch (SQLException ed) {
                    throw new Exception(ed.getMessage());
                }
            }

            // define cookie com carrinhoId
            int durMes = 2592000;
            Cookie ckCarrinhoId = new Cookie("carrinhoId", carrinhoId);
            ckCarrinhoId.setMaxAge(durMes);
            response.addCookie(ckCarrinhoId);
            // define variavel de sessao p ser recuperado
            session.setAttribute("carrinhoId", carrinhoId);

            if (request.getParameter("addProdutoId") != null) {
                String addProdutoId = request.getParameter("addProdutoId");
                try {
                    int qtdValidador = sqlCartsProducts.select()
                                                       .where("product_id=" + Integer.parseInt(addProdutoId) + " AND cart_id=" + Integer.parseInt(carrinhoId))
                                                       .count();
                    if (qtdValidador == 0) {
                        HashMap<String, Object> attrs = new HashMap() {{
                            put("product_id", addProdutoId);
                            put("quantity", 1);
                        }};
                        attrs.put("cart_id", carrinhoId);
                        sqlCartsProducts.insert().values(attrs).run();
                        session.setAttribute("msg", "Produto inserido no carrinho com sucesso!");
                    } else {
                        session.setAttribute("msg", "Produto já inserido no carrinho!");
                    }
                } catch (SQLException ed) {
                    throw new Exception(ed.getMessage());
                }
            }

            // recupera acao solicitada se existir
            String action = "";
            if (request.getParameter("action") != null) {
                action = request.getParameter("action");
            }

            switch (action) {
                case "finalizaCompra": {
                    String totalPrice = request.getParameter("totalPrice");
                    request.setAttribute("totalPrice", totalPrice);
                    try {
                        ArrayList<Address> enderecos = new ArrayList<>();
                        ArrayList<BaseModel> result = new SqlManager(Address.class).select()
                                                                                   .where("user_id="+userId)
                                                                                   .run();
                        result.forEach((r) -> {
                            enderecos.add((Address) r);
                        });
                        request.setAttribute("enderecos", enderecos);

                        String consulta = "SELECT\n"
                                + "	p.`name`,\n"
                                + "	p.img,\n"
                                + "	p.id,\n"
                                + "	c.quantity\n"
                                + "FROM\n"
                                + "	carts_products c\n"
                                + "LEFT JOIN products p ON (c.product_id = p.id)\n"
                                + "WHERE\n"
                                + "	c.cart_id = ?";
                        String[] bind = {carrinhoId};
                        ArrayList<ArrayList> produtos = new ArrayList<>();
                        ResultSet retono = SqlManager.bruteExecute(consulta, bind);
                        while (retono.next()) {
                            ArrayList<String> row = new ArrayList<>();
                            row.add(retono.getString("name"));
                            row.add(retono.getString("img"));
                            row.add(retono.getString("id"));
                            row.add(retono.getString("quantity"));
                            produtos.add(row);
                        }
                        session.setAttribute("produtos", produtos);
                    } catch (SQLException ex) {
                        throw new Exception("Erro ao recuperar registros do banco: " + ex.getMessage());
                    }
                    request.getRequestDispatcher("carrinho-confirma.jsp").forward(request, response);
                    return;
                }
                case "continuaCompra": {
                    // redireciona p controller de ProdutosController
                    response.sendRedirect("ProdutosController");
                    return;
                }
                case "removeProduto": {
                    String produtoId = request.getParameter("produtoId");
                    try {
                        String[] bind = {produtoId, carrinhoId};
                        sqlCartsProducts.delete().where("product_id=" + produtoId + " AND cart_id=" + carrinhoId).run();
                        session.setAttribute("msg", "Produto removido do carrinho com sucesso!");
                    } catch (SQLException ed) {
                        throw new Exception(ed.getMessage());
                    }
                    break;
                }
                case "mudaQtd": {
                    String produtoId = request.getParameter("produtoId");
                    String qtdProduto = request.getParameter("qtdProduto");
                    try {
                        HashMap<String, Object> attrs = new HashMap() {{
                            put("quantity", qtdProduto);
                        }};
                        sqlCartsProducts.update().set(attrs).where("product_id=" + produtoId + " AND cart_id=" + carrinhoId).run();
                        session.setAttribute("msg", "Quantidade do produto alterada com sucesso!");
                    } catch (SQLException ed) {
                        throw new Exception(ed.getMessage());
                    }
                    break;
                }
                case "continuaPagamento": {
                    String end = request.getParameter("end");
                    if (end == null) {
                        session.setAttribute("msg", "Por favor selecione um endereço!");
                        session.setAttribute("enderecoId", null);
                        request.setAttribute("totalPrice", null);
                        response.sendRedirect("CarrinhoController");
                        return;
                    } else {
                        session.setAttribute("enderecoId", end);
                        String totalPrice = request.getParameter("totalPrice");
                        session.setAttribute("totalPrice", totalPrice);
                    }
                    request.getRequestDispatcher("CompraController").forward(request, response);
                    return;
                }
            }

            // define avaliacoes
            try {
                String consulta = "SELECT\n"
                        + "	p.id,\n"
                        + "	p.`name`,\n"
                        + "	p.description,\n"
                        + "	p.img,\n"
                        + "	p.price,\n"
                        + "	p.quantity max,\n"
                        + "	c.quantity,\n"
                        + "	p.price * c.quantity total_price_product\n"
                        + "FROM\n"
                        + "	carts_products c\n"
                        + "LEFT JOIN products p ON (c.product_id = p.id)\n"
                        + "WHERE\n"
                        + "	c.cart_id = ?";
                String[] bind = {carrinhoId};
                ArrayList<ArrayList> itens = new ArrayList<>();
                ResultSet ret = SqlManager.bruteExecute(consulta, bind);
                while (ret.next()) {
                    ArrayList<String> row = new ArrayList<>();
                    // preenche row
                    row.add(ret.getString("id"));
                    row.add(ret.getString("name"));
                    row.add(ret.getString("description"));
                    row.add(ret.getString("img"));
                    row.add(ret.getString("price"));
                    row.add(ret.getString("quantity"));
                    row.add(ret.getString("max"));
                    row.add(ret.getString("total_price_product"));
                    // add row no grid
                    itens.add(row);
                }
                ResultSet rs = SqlManager.bruteExecute("SELECT c.cart_id, sum(p.price * c.quantity) total_price FROM carts_products c LEFT JOIN products p ON (c.product_id = p.id) WHERE c.cart_id = ? GROUP BY c.cart_id", bind);
                String totalPrice = null;
                if (rs.next()) totalPrice = rs.getString("total_price");
                request.setAttribute("totalPrice", totalPrice);
                request.setAttribute("itens", itens);
            } catch (SQLException ex) {
                throw new Exception("Erro ao recuperar registros do banco: " + ex.getMessage());
            }

            // manda atributos para a pag do carrinho
            request.getRequestDispatcher("carrinho.jsp").forward(request, response);
            return;
        } catch (Exception ex) {
            session.setAttribute("msg", ex.getMessage());
            response.sendRedirect("ProdutosController");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
