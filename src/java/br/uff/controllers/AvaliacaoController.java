/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.controllers;

import br.uff.dao.MySql;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "AvaliacaoController", urlPatterns = {"/AvaliacaoController"})
public class AvaliacaoController extends HttpServlet {

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

            // seta produtoId na sessao se estiver passado por parametro antes de verificar login
            if (request.getParameter("produtoId") != null) {
                session.setAttribute("produtoId", request.getParameter("produtoId"));
            }

            // seta rating na sessao se estiver passado por parametro antes de verificar login
            if (request.getParameter("rating") != null) {
                session.setAttribute("rating", request.getParameter("rating"));
            }

            // se n tem usuario logado manda p controller de user
            if (session.getAttribute("userId") == null) {
                response.sendRedirect("UserController?redirect=AvaliacaoController");
                return;
            }

            String produtoId = null;
            if (session.getAttribute("produtoId") != null) {
                produtoId = session.getAttribute("produtoId").toString();
            } else {
                session.setAttribute("msg", "Por favor, selecione um produto.");
                response.sendRedirect("ProdutosController");
                return;
            }

            // recupera acao solicitada se existir
            String action = request.getParameter("action");

            switch (action) {
                case "avalia": {
                    // grava avaliacao do produto
                    MySql db = null;
                    try {
                        db = new MySql();
                        //RECUPERA VALUES
                        String userId = session.getAttribute("userId").toString();
                        String productId = session.getAttribute("produtoId").toString();
                        String rating = session.getAttribute("rating").toString();
                        String description = request.getParameter("description").toString();
                        String title = request.getParameter("title").toString();
                        String[] bind = {userId, productId, rating, description, title};
                        db.dbGrava("INSERT INTO user_produts_rating (user_id,product_id,rating,description,title,created_at) VALUES (?,?,?,?,?,SYSDATE())", bind);
                        // define msg a ser mostrada
                        session.setAttribute("msg", "Produto avaliado com sucesso!");

                    } catch (ClassNotFoundException | SQLException e) {
                        throw new Exception("Falha ao avaliar produto: " + e.getMessage());
                    } finally {
                        db.destroyDb();
                        session.setAttribute("rating", null);
                    }
                    response.sendRedirect("ProdutoController");
                    return;
                }
            }

            request.getRequestDispatcher("produto-avalia.jsp").forward(request, response);
            return;
            // se der erro vai p ControllerProdutos
        } catch (Exception ex) {
            session.setAttribute("msg", ex.getMessage());
            response.sendRedirect("ProdutoController");
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
