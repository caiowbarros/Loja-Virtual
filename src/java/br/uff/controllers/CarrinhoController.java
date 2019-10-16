/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.controllers;

import br.uff.dao.MySql;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 *
 * @author HP
 */
@WebServlet(name = "CarrinhoController", urlPatterns = {"/CarrinhoController"})
public class CarrinhoController extends HttpServlet {

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

            String carrinhoId = null;
            Cookie[] cookies = request.getCookies();
            // procura carrinhoId nos cookies setados
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie cookie = cookies[i];
                    if (cookie.getName().equals("carrinhoId")) {
                        carrinhoId = cookie.getValue().toString();
                        continue;
                    }
                }
            }

            String userId = null;
            // recupera userId e se tiver carrinhoId como cookie mas n tiver userId cadastrado p esse carrinho id cadastra user
            if (session.getAttribute("userId") != null) {
                userId = session.getAttribute("userId").toString();
                MySql db = null;
                try {
                    db = new MySql();
                    // verifica se carrinhoId recuperado pertence a outro usuario
                    if (carrinhoId != null) {
                        String[] bindVerifica = {carrinhoId};
                        String carrinhoIdVerificaUser = db.dbValor("user_id", "carts", "id=?", bindVerifica);
                        if (carrinhoIdVerificaUser != null) {
                            if (!carrinhoIdVerificaUser.equals(userId)) {
                                // define carrinhoId como null pois pertence a outro usuario
                                carrinhoId = null;
                            }
                        }
                    }
                    // pega id de carrinho se n estiver vendido, se user id bater e se n for null
                    String[] bindCarrinhoUser = {userId};
                    String CarrinhoUser = db.dbValor("id", "carts", "user_id=? AND id not in (SELECT cart_id FROM sales)", bindCarrinhoUser);
                    if (CarrinhoUser != null) {
                        carrinhoId = CarrinhoUser;
                    }
                    // se carrinho id n for null, define usuario se n tiver
                    if (carrinhoId != null) {
                        String[] bind = {userId, carrinhoId};
                        db.dbGrava("UPDATE carts set user_id=? WHERE id=? AND user_id IS NULL", bind);
                    }
                } catch (Exception ed) {
                    throw new Exception(ed.getMessage());
                } finally {
                    db.destroyDb();
                }
            } else {
                MySql db = null;
                try {
                    db = new MySql();
                    // verifica se carrinhoId pertence a algum usuario
                    if (carrinhoId != null) {
                        String[] bindVerifica = {carrinhoId};
                        String carrinhoIdVerificaUser = db.dbValor("user_id", "carts", "id=?", bindVerifica);
                        if (carrinhoIdVerificaUser != null) {
                            // define carrinhoId como null pois pertence a um usuario
                            carrinhoId = null;
                        }
                    }
                } catch (Exception ed) {
                    throw new Exception(ed.getMessage());
                } finally {
                    db.destroyDb();
                }
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
                MySql db = null;
                try {
                    db = new MySql();
                    String[] bind = {carrinhoId};
                    int verificaCarrinho = Integer.valueOf(db.dbValor("count(*)", "carts", "id=? AND id in (SELECT cart_id FROM sales)", bind));
                    //se encontrou carrinho vendido define carrinhoId como null
                    if (verificaCarrinho > 0) {
                        carrinhoId = null;
                    }
                } catch (Exception ed) {
                    throw new Exception(ed.getMessage());
                } finally {
                    db.destroyDb();
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
                String[] bind = {ip, createdAt, userId};
                MySql db = null;
                try {
                    db = new MySql();
                    db.dbGrava("INSERT INTO carts (ip,created_at,user_id) VALUES (?,?,?)", bind);
                    carrinhoId = db.dbValor("max(id)", "carts", "ip=? AND created_at=? AND user_id " + (userId == null ? " IS " : " = ") + " ?", bind);
                } catch (SQLException ed) {
                    throw new Exception(ed.getMessage());
                } finally {
                    db.destroyDb();
                }
            }

            // define cookie com carrinhoId
            int durMes = 2592000;
            Cookie ckCarrinhoId = new Cookie("carrinhoId", carrinhoId);
            ckCarrinhoId.setMaxAge(durMes);
            response.addCookie(ckCarrinhoId);

            if (request.getParameter("addProdutoId") != null) {
                MySql db = null;
                String addProdutoId = request.getParameter("addProdutoId");
                try {
                    db = new MySql();
                    String[] bind = {addProdutoId, carrinhoId};
                    int qtdValidador = Integer.valueOf(db.dbValor("count(*)", "carts_products", "product_id=? AND cart_id=?", bind));
                    if (qtdValidador == 0) {
                        db.dbGrava("INSERT INTO carts_products (product_id,cart_id,quantity) VALUES (?,?,1)", bind);
                        session.setAttribute("msg", "Produto inserido no carrinho com sucesso!");
                    } else {
                        session.setAttribute("msg", "Produto já inserido no carrinho!");
                    }
                } catch (SQLException ed) {
                    throw new Exception(ed.getMessage());
                } finally {
                    db.destroyDb();
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
                    String consultaEndereco = "SELECT id, `name` FROM address";
                    MySql db = null;
                    try {
                        db = new MySql();
                        ArrayList<ArrayList> enderecos = new ArrayList<>();
                        ResultSet ret = db.dbCarrega(consultaEndereco, null);
                        while (ret.next()) {
                            ArrayList<String> row = new ArrayList<>();
                            row.add(ret.getString("id"));
                            row.add(ret.getString("name"));
                            enderecos.add(row);
                        }
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
                        ResultSet retono = db.dbCarrega(consulta, bind);
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
                    } finally {
                        db.destroyDb();
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
                    MySql db = null;
                    String produtoId = request.getParameter("produtoId");
                    try {
                        db = new MySql();
                        String[] bind = {produtoId, carrinhoId};
                        db.dbGrava("DELETE FROM carts_products WHERE product_id=? AND cart_id=?", bind);
                        session.setAttribute("msg", "Produto removido do carrinho com sucesso!");
                    } catch (SQLException ed) {
                        throw new Exception(ed.getMessage());
                    } finally {
                        db.destroyDb();
                    }
                    break;
                }
                case "mudaQtd": {
                    MySql db = null;
                    String produtoId = request.getParameter("produtoId");
                    String qtdProduto = request.getParameter("qtdProduto");
                    try {
                        db = new MySql();
                        String[] bind = {qtdProduto, produtoId, carrinhoId};
                        db.dbGrava("UPDATE carts_products SET quantity=? WHERE product_id=? AND cart_id=?", bind);
                        session.setAttribute("msg", "Quantidade do produto alterada com sucesso!");
                    } catch (SQLException ed) {
                        throw new Exception(ed.getMessage());
                    } finally {
                        db.destroyDb();
                    }
                    break;
                }
                case "continuaPagamento": {
                    String end = request.getParameter("end");
                    if (end == null) {
                        session.setAttribute("msg", "Por favor selecione um endereço!");
                        response.sendRedirect("CarrinhoController");
                        return;
                    }
                    request.setAttribute("end", end);
                    String totalPrice = request.getParameter("totalPrice");
                    request.setAttribute("totalPrice", totalPrice);
                    request.getRequestDispatcher("CompraController").forward(request, response);
                    return;
                }
            }

            // define avaliacoes
            MySql dbCarrinho = null;
            try {
                String consulta = "SELECT\n"
                        + "	p.id,\n"
                        + "	p.`name`,\n"
                        + "	p.description,\n"
                        + "	p.img,\n"
                        + "	p.price,\n"
                        + "	p.quantity max,\n"
                        + "	c.quantity\n"
                        + "FROM\n"
                        + "	carts_products c\n"
                        + "LEFT JOIN products p ON (c.product_id = p.id)\n"
                        + "WHERE\n"
                        + "	c.cart_id = ?";
                String[] bind = {carrinhoId};
                dbCarrinho = new MySql();
                ArrayList<ArrayList> itens = new ArrayList<>();
                ResultSet ret = dbCarrinho.dbCarrega(consulta, bind);
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
                    // add row no grid
                    itens.add(row);
                }
                request.setAttribute("itens", itens);
            } catch (SQLException ex) {
                throw new Exception("Erro ao recuperar registros do banco: " + ex.getMessage());
            } finally {
                dbCarrinho.destroyDb();
            }

            // manda atributos para a pag do carrinho
            request.getRequestDispatcher("carrinho.jsp").forward(request, response);
            return;
        } catch (Exception ex) {
            session.setAttribute("msg", ex.getMessage());
            response.sendRedirect("ProdutosController");
            return;
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
