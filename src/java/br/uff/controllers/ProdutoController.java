/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.controllers;

import br.uff.dao.MySql;
import java.io.IOException;
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
        // pega sessao
        HttpSession session = request.getSession();
        try {

            // se tem produto selecionado por parametro passa p sessao
            if (request.getParameter("produtoId") != null) {
                session.setAttribute("produtoId", request.getParameter("produtoId"));
            }

            // se fav esta definido por parametro passa p sessao
            if (request.getParameter("fav") != null) {
                session.setAttribute("fav", "1");
            }

            String fav = null;
            // recupera fav da sessao
            if (session.getAttribute("fav") != null) {
                fav = session.getAttribute("fav").toString();
            }

            // se produtoId for null manda p pag d produtos
            String produtoId = null;
            if (session.getAttribute("produtoId") != null) {
                produtoId = session.getAttribute("produtoId").toString();
            } else {
                session.setAttribute("msg", "Por favor, selecione um produto.");
                response.sendRedirect("ProdutosController");
                return;
            }

            // checa se eh para favoritar ou desfavoritar produto
            if (fav != null) {
                // VERIFICA SE ESTA LOGADO
                if (session.getAttribute("userId") == null) {
                    response.sendRedirect("UserController?redirect=ProdutoController");
                    return;
                } else {
                    MySql db = null;
                    try {
                        db = new MySql();
                        String userId = session.getAttribute("userId").toString();
                        String[] bindFav = {produtoId, userId};
                        String count = "0";
                        count = db.dbValor("count(*)", "favorite_products", "product_id=? AND user_id=?", bindFav);
                        // se ja tiver registro, remove, se nao tiver insere
                        if (!count.equals("0")) {
                            db.dbGrava("DELETE FROM favorite_products WHERE product_id=? AND user_id=?", bindFav);
                            session.setAttribute("msg", "Produto desfavoritado com sucesso!");
                        } else {
                            db.dbGrava("INSERT INTO favorite_products (product_id,user_id) VALUES (?,?)", bindFav);
                            session.setAttribute("msg", "Produto favoritado com sucesso!");
                        }
                    } catch (Exception ex) {
                        throw new Exception(ex.getMessage());
                    } finally {
                        db.destroyDb();
                        // define fav como null
                        session.setAttribute("fav", null);
                    }
                }
            }

            request.getRequestDispatcher("produto.jsp").forward(request, response);
            return;
        } catch (Exception ex) {
            session.setAttribute("msg", ex.getMessage());
            request.getRequestDispatcher("ProdutosController").forward(request, response);
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
