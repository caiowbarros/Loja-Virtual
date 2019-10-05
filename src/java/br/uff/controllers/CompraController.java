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
@WebServlet(name = "CompraController", urlPatterns = {"/CompraController"})
public class CompraController extends HttpServlet {

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
                response.sendRedirect("UserController?redirect=CompraController");
                return;
            }
            
            if (request.getParameter("paypal_pag") != null) {
                // REGISTRA SALE NO BD
            } else if (request.getParameter("paypal_cancela") != null) {
                response.sendRedirect("CarrinhoController");
            } else if (request.getParameter("paypal_erro") != null) {
                // define msg a ser mostrada
                request.setAttribute("msg", "Um erro ocorreu no processamento do seu pagamento, por favor tente novamente!");
            }

            // recupera acao solicitada se existir
            String action = request.getParameter("action");

            if ("continuaCompra".equals(action)) {
                // redireciona p controller de ProdutosController
                response.sendRedirect("ProdutosController");
            }

            // manda atributos para a pagina definida, no caso carrinho.jsp
            request.getRequestDispatcher("compra-pagamento.jsp").forward(request, response);
        } catch (Exception ex) {
            response.sendRedirect("CarrinhoController");
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
