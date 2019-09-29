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
import javax.servlet.http.*;

/**
 *
 * @author HP
 */
@WebServlet(name = "EnderecoController", urlPatterns = {"/EnderecoController"})
public class EnderecoController extends HttpServlet {

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
                response.sendRedirect("UserController?redirect=EnderecoController");
                return;
            }

            if (request.getParameter("sel") != null) {
                session.setAttribute("enderecoId", request.getParameter("sel"));
            } else if (request.getParameter("unsel") != null) {
                session.setAttribute("enderecoId", null);
            } else if (request.getParameter("del") != null) {
                // exclui endereco do id request.getParameter("del") e do usuario session.getAttribute("userId")
                request.setAttribute("msg", "Endereço deletado com sucesso!");
            }

            // se tem enderecoId definido mostra cadastro caso contrario mostra grid
            if (session.getAttribute("enderecoId") == null) {
                request.getRequestDispatcher("endereco-grid.jsp").forward(request, response);
                return;
            } else {

                // recupera acao solicitada se existir
                String action = request.getParameter("action");

                // verifica acoes
                if ("grava".equals(action)) {
                    // grava alteracoes do session.getAttribute("enderecoId")
                    request.setAttribute("msg", "Endereço gravado com sucesso!");
                }

                request.getRequestDispatcher("endereco-cadastro.jsp").forward(request, response);
                return;
            }
        } catch (Exception ex) {
            response.sendRedirect("UserController");
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
