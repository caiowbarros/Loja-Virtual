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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Base64;

/**
 *
 * @author HP
 */
@WebServlet(name = "ProdutoController", urlPatterns = {"/ProdutoController"})
public class ProdutoController extends HttpServlet {

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

            // se tem produto selecionado manda p pag d produto
            if (request.getParameter("produtoId") != null) {
                session.setAttribute("produtoId", request.getParameter("produtoId"));
                request.getRequestDispatcher("produto.jsp").forward(request, response);
                return;
            }

            // se n tem usuario logado manda p controller de user
            if (session.getAttribute("userId") == null) {
                response.sendRedirect("UserController?redirect=ProdutoController");
                return;
            }

            if (request.getParameter("fav") != null) {
                // favorita produto request.getParameter("fav")
                request.setAttribute("msg", "Produto favoritado com sucesso!");
                request.getRequestDispatcher("produto.jsp").forward(request, response);
                return;
            }

            // opcoes restritas a usuario ADM
            if (session.getAttribute("userRole").toString().equals("1")) {
                if (request.getParameter("sel") != null) {
                    // seleciona produtoId da sessao SE USUARIO FOR ADM
                    session.setAttribute("produtoId", request.getParameter("sel"));
                } else if (request.getParameter("unsel") != null) {
                    // apaga produtoId da sessao SE USUARIO FOR ADM
                    session.setAttribute("produtoId", null);
                } else if (request.getParameter("del") != null) {
                    // exclui produto do id request.getParameter("del") SE USUARIO FOR ADM
                    request.setAttribute("msg", "Produto deletado com sucesso!");
                } else if (request.getParameter("qtdEstoque") != null) {
                    // SE USUARIO FOR ADM vai p pag de incrementar qtd de estoques
                    request.getRequestDispatcher("produto-incrementa.jsp").forward(request, response);
                    return;
                }

                // se tem produtoId definido mostra cadastro caso contrario mostra grid SE USUARIO FOR ADM
                if (session.getAttribute("produtoId") == null) {
                    request.getRequestDispatcher("produto-grid.jsp").forward(request, response);
                    return;
                } else {

                    // recupera acao solicitada se existir
                    String action = request.getParameter("action");

                    // verifica acoes
                    if ("grava".equals(action)) {
                        PrintWriter out = response.getWriter();
                        response.setContentType("text/html");
                        //pega img passada
                        String img = request.getParameter("img");

                        //pega path da img
                        java.nio.file.Path mypath = java.nio.file.Paths.get(img);

                        //recupera bytes da imagem
                        byte[] mydata = java.nio.file.Files.readAllBytes(mypath);

                        // pega extensao
                        String extension = "";
                        int i = img.lastIndexOf('.');
                        int p = Math.max(img.lastIndexOf('/'), img.lastIndexOf('\\'));
                        if (i > p) {
                            extension = img.substring(i + 1);
                        }

                        // pega base 64 dos bytes recuperados
                        String src = "data:image/" + extension + ";base64," + Base64.getEncoder().encodeToString(mydata);

                        out.print("<img src='" + src + "' />");
                        // grava alteracoes do session.getAttribute("produtoId") SE USUARIO FOR ADM
                        //request.setAttribute("msg", "Produto gravado com sucesso!");
                        // manda p pag d produto
                        //request.getRequestDispatcher("produto-grid.jsp").forward(request, response);
                        return;
                    } else if ("estoqueInsere".equals(action)) {
                        // aumenta qtd em estoque do produto do session.getAttribute("produtoId") SE USUARIO FOR ADM
                        request.setAttribute("msg", "Quantidade em estoque aumentada com sucesso!");
                    }

                    request.getRequestDispatcher("produto-cadastro.jsp").forward(request, response);
                    return;
                }

            }
            response.sendRedirect("ProdutosController");
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
