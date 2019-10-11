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

        try {
            // opcoes restritas a usuario ADM
            if (session.getAttribute("userRole").equals("1")) {

                if (request.getParameter("sel") != null) {
                    session.setAttribute("sel", request.getParameter("sel"));
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

                        MySql db = null;
                        try {
                            db = new MySql("test", "root", "");
                            if (sel != null) {
                                String[] bind = {name, price, description, img, categoryId};
                                db.dbGrava("INSERT INTO products (name,price,description,img,category_id,created_at,quantity) VALUES (?,?,?,?,?,SYSDATE(),0)", bind);
                            } else {
                                String[] bind = {name, price, description, img, categoryId, sel};
                                db.dbGrava("UPDATE products set name=?,price=?,description=?,img=?,category_id=? WHERE id=?", bind);
                            }
                            session.setAttribute("msg", "Produto gravado com sucesso!");
                        } catch (ClassNotFoundException | SQLException e) {
                            throw new Exception("Falha ao gravar produto: " + e.getMessage());
                        } finally {
                            db.destroyDb();
                        }
                        break;
                    }
                    case "estoqueInsere": {
                        MySql db = null;
                        try {
                            db = new MySql("test", "root", "");
                            String quantity = request.getParameter("quantity");
                            String[] bindIncrease = {quantity, sel};
                            db.dbGrava("UPDATE products SET quantity = quantity + ? WHERE id=?", bindIncrease);
                            session.setAttribute("msg", "Quantidade em estoque aumentada com sucesso!");
                        } catch (Exception ed) {
                            throw new Exception(ed.getMessage());
                        } finally {
                            db.destroyDb();
                        }
                        break;
                    }
                    case "del": {
                        MySql db = null;
                        try {
                            db = new MySql("test", "root", "");
                            String[] bindDel = {sel};
                            db.dbGrava("DELETE FROM products WHERE id=?", bindDel);
                            session.setAttribute("msg", "Produto deletado com sucesso!");
                        } catch (Exception ed) {
                            throw new Exception(ed.getMessage());
                        } finally {
                            db.destroyDb();
                        }
                        break;
                    }
                    case "unsel": {
                        // apaga produtoId da sessao
                        session.setAttribute("sel", null);
                        break;
                    }
                }
            } else {
                throw new Exception("Usuário não permitido!");
            }

            // manda p pag de grid de produtos
            request.getRequestDispatcher("produto-grid.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            session.setAttribute("msg", e.getMessage());
            response.sendRedirect("UserController?redirect=ProdutoAdmController");
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
