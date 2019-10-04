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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // pega sessao
            HttpSession session = request.getSession();

            // seta atributo de sessionId
            request.setAttribute("sessionId", session.getId());

            if (request.getParameter("addProdutoId") != null) {
                // ADD PRODUTO NO SESSIONID
            } else if (request.getParameter("confirmaCompra") != null) {
                // manda atributos para a pagina definida, no caso carrinho-confirma.jsp
                request.getRequestDispatcher("carrinho-confirma.jsp").forward(request, response);
            }

            // recupera acao solicitada se existir
            String action = request.getParameter("action");

            if ("mudaQtd".equals(action)) {
                // muda qtd d produtos no carrinho
                // define msg a ser mostrada
                request.setAttribute("msg", "Quantidade de Produtos alterada com sucesso!");
            } else if ("removeProdutoId".equals(action)) {
                // remove produtoId do carrinho de sessao id
                // define msg a ser mostrada
                request.setAttribute("msg", "Produto removido do carrinho com sucesso!");
            } else if ("finalizaCompra".equals(action)) {
                // manda atributos para a pagina definida, no caso compra-pagamento.jsp
                request.getRequestDispatcher("compra-pagamento.jsp").forward(request, response);
            } else if ("continuaCompra".equals(action)) {
                // redireciona p controller de ProdutosController
                response.sendRedirect("ProdutosController");
            }

            // manda atributos para a pagina definida, no caso carrinho.jsp
            request.getRequestDispatcher("carrinho.jsp").forward(request, response);
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
