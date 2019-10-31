/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.controllers;

import br.uff.models.Product;
import br.uff.sql.ConnectionManager;
import br.uff.sql.SqlManager;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HP
 */
@WebServlet(name = "ProdutoAdmController", urlPatterns = {"/ProdutoAdmController"})
public class ProdutoAdmController extends HttpServlet {
    @Override
    public void init() {
        try {
            ConnectionManager.connect();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void destroy() {
        try {
            ConnectionManager.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        // pega sessao
        HttpSession session = request.getSession();
        SqlManager sql = new SqlManager(Product.class);

        try {
            // opcoes restritas a usuario ADM
            if (session.getAttribute("userRole").equals("1")) {

                if (request.getParameter("sel") != null) {
                    String selParameter = request.getParameter("sel");
                    Product produto;
                    produto = new Product("", "", "", "", 0, 0, "SYSDATE()");
                    if (!selParameter.equals("")) {
                        // define produto
                        try {
                            produto = (Product) sql.find(Integer.parseInt(selParameter));
                        } catch (SQLException ex) {
                            throw new Exception("Erro ao recuperar registros do banco: " + ex.getMessage());
                        }
                    }
                    // define atributo de produto
                    request.setAttribute("produto", produto);
                    session.setAttribute("sel", selParameter);
                    request.getRequestDispatcher("produto-cadastro.jsp").forward(request, response);
                    return;
                }

                // define produto selecionado
                String sel = null;
                if (session.getAttribute("sel") != null) {
                    sel = session.getAttribute("sel").toString();
                }

                // recupera acao solicitada se existir
                String action = "";

                if (request.getParameter("action") != null) {
                    action = request.getParameter("action");
                }

                switch (action) {
                    case "grava": {
                        // grava alteracoes do session.getAttribute("produtoId")
                        String name = request.getParameter("name");
                        String price = request.getParameter("price");
                        String description = request.getParameter("description");
                        String img = request.getParameter("src");
                        String categoryId = request.getParameter("categoryId");

                        try {
                            HashMap<String, Object> attrs = new HashMap() {{
                                put("name", name);
                                put("price", price);
                                put("description", description);
                                put("img", img);
                                put("category_id", categoryId);                                
                            }};
                            if (sel.equals("")) {
                                attrs.put("created_at", "SYSDATE()");
                                attrs.put("quantity", 0);
                                sql.insert().values(attrs).run();
                            } else {
                                sql.update().where("id="+sel).set(attrs);
                            }
                            session.setAttribute("msg", "Produto gravado com sucesso!");
                        } catch (SQLException e) {
                            throw new Exception("Falha ao gravar produto: " + e.getMessage());
                        }
                        break;
                    }
                    case "estoqueInsere": {
                        try {
                            String quantity = request.getParameter("quantity");
                            HashMap<String, Object> attrs = new HashMap() {{
                                put("quantity", quantity);
                            }};
                            sql.update().where("id="+sel).set(attrs);
                            session.setAttribute("msg", "Quantidade em estoque aumentada com sucesso!");
                        } catch (Exception ed) {
                            throw new Exception(ed.getMessage());
                        }
                        break;
                    }
                    case "del": {
                        try {
                            sql.delete().where("id="+sel);
                            session.setAttribute("msg", "Produto deletado com sucesso!");
                        } catch (Exception ed) {
                            throw new Exception(ed.getMessage());
                        }
                        break;
                    }
                    case "unsel": {
                        // apaga sel da sessao
                        session.setAttribute("sel", null);
                        break;
                    }
                }
            } else {
                throw new Exception("Usuário não permitido!");
            }

            // define grid
            try {
                ArrayList<ArrayList> grid = new ArrayList<>();
                ResultSet ret = SqlManager.bruteExecute("SELECT p.id,p.name,p.price,c.category_name,p.quantity FROM products p left join vw_category c on (p.category_id=c.id)", null);
                while (ret.next()) {
                    ArrayList<String> row = new ArrayList<>();
                    // preenche row
                    row.add(ret.getString("id"));
                    row.add(ret.getString("name"));
                    row.add(ret.getString("price"));
                    row.add(ret.getString("category_name"));
                    row.add(ret.getString("quantity"));
                    // add row no grid
                    grid.add(row);
                }
                request.setAttribute("grid", grid);
            } catch (SQLException ex) {
                throw new Exception("Erro ao recuperar registros do banco: " + ex.getMessage());
            }

            // manda p pag de grid de produtos
            request.getRequestDispatcher("produto-grid.jsp").forward(request, response);
        } catch (Exception e) {
            session.setAttribute("msg", e.getMessage());
            response.sendRedirect("UserController");
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoAdmController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoAdmController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
