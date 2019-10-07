/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.controllers;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ProdutosController", urlPatterns = {"/ProdutosController"})
public class ProdutosController extends HttpServlet {

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
        try {
            // pega sessao
            HttpSession session = request.getSession();

            // verifica se chama favoritos
            if (request.getParameter("fav") != null) {
                // se tem usuario logado mostra filtra produtos por favoritos
                if (session.getAttribute("userId") != null) {

                } else {
                    // se n tem usuario logado mostra msg solicitando login
                    request.setAttribute("msg", "Realize login para ver seus favoritos!");
                }
            }

            Integer ProdutosPag = 1;
            if (session.getAttribute("ProdutosPag") != null) {
                ProdutosPag = (Integer) session.getAttribute("ProdutosPag");
            } else {
                session.setAttribute("ProdutosPag", ProdutosPag);
            }

            // recupera acao solicitada se existir
            String action = request.getParameter("action");

            // verifica acoes
            if ("ant".equals(action)) {
                if (ProdutosPag > 0) {
                    ProdutosPag = ProdutosPag - 1;
                    session.setAttribute("ProdutosPag", ProdutosPag);
                }
            } else if ("prox".equals(action)) {
                ProdutosPag = ProdutosPag + 1;
                session.setAttribute("ProdutosPag", ProdutosPag);
            }

            request.getRequestDispatcher("produtos.jsp").forward(request, response);
            return;
        } catch (Exception ex) {
            response.sendRedirect("");
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
