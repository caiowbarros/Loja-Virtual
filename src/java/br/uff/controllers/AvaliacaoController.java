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
        try {
            // pega sessao
            HttpSession session = request.getSession();

            // se n tem usuario logado manda p controller de user
            if (session.getAttribute("userId") == null) {
                response.sendRedirect("UserController?redirect=AvaliacaoController");
                return;
            }

            // verifica p ver se tem algum produtoId definido se n tiver, vai p pag d produtos, se tiver define produtoId
            String produtoId = "";
            if (session.getAttribute("produtoId") == null) {
                response.sendRedirect("ProdutosController");
                return;
            } else {
                produtoId = session.getAttribute("produtoId").toString();
            }

            // define atributos
            request.setAttribute("produtoId", produtoId);
            request.setAttribute("rating", request.getParameter("rating"));

            // recupera acao solicitada se existir
            String action = request.getParameter("action");

            if ("avalia".equals(action)) {
                // grava avaliacao do produto
                // define msg a ser mostrada
                request.setAttribute("msg", "Produto avaliado com sucesso!");
                //redireciona de volta p pag do produto avaliado
                request.getRequestDispatcher("ProdutoController?produtoId=" + produtoId).forward(request, response);
                return;
            }

            request.getRequestDispatcher("produto-avalia.jsp").forward(request, response);
            return;
            // se der erro vai p ControllerProdutos
        } catch (Exception ex) {
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
