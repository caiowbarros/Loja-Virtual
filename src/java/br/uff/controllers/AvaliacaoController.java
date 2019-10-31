/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.controllers;

import br.uff.models.BaseModel;
import br.uff.models.UserProductsRating;
import br.uff.sql.ConnectionManager;
import br.uff.sql.SqlManager;
import java.io.IOException;
import java.sql.SQLException;
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
@WebServlet(name = "AvaliacaoController", urlPatterns = {"/AvaliacaoController"})
public class AvaliacaoController extends HttpServlet {
    @Override
    public void init() {
        try {
            ConnectionManager.connect();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AvaliacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void destroy() {
        try {
            ConnectionManager.close();
        } catch (SQLException ex) {
            Logger.getLogger(AvaliacaoController.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // pega sessao
        HttpSession session = request.getSession();
        try {
            SqlManager sql = new SqlManager(UserProductsRating.class);

            // seta produtoId na sessao se estiver passado por parametro antes de verificar login
            if (request.getParameter("produtoId") != null) {
                session.setAttribute("produtoId", request.getParameter("produtoId"));
            }

            // seta rating na sessao se estiver passado por parametro antes de verificar login
            if (request.getParameter("rating") != null) {
                session.setAttribute("rating", request.getParameter("rating"));
            }

            // se n tem usuario logado manda p controller de user
            String userId = null;
            if (session.getAttribute("userId") == null) {
                response.sendRedirect("UserController?redirect=AvaliacaoController");
                return;
            } else {
                userId = session.getAttribute("userId").toString();
            }

            String produtoId = null;
            if (session.getAttribute("produtoId") != null) {
                produtoId = session.getAttribute("produtoId").toString();
                String condition = "user_id = " + userId + " and product_id = " + produtoId;
                boolean isRated = sql.select().where(condition).exists();
                if (isRated) {
                    session.setAttribute("rating", null);
                    throw new Exception("Produto já avaliado!");
                }
            } else {
                session.setAttribute("msg", "Por favor, selecione um produto.");
                response.sendRedirect("ProdutosController");
                return;
            }

            // recupera acao solicitada se existir
            String action = "";
            if (request.getParameter("action") != null) {
                action = request.getParameter("action");
            }

            switch (action) {
                case "avalia": {
                    HashMap<String, Object> attrs = new HashMap() {{
                        put("rating", Integer.parseInt((String) session.getAttribute("rating")));
                        put("description", request.getParameter("description"));
                        put("title", request.getParameter("title"));
                        put("created_at", "SYSDATE()");
                    }};
                    attrs.put("user_id", Integer.parseInt((String) userId));
                    attrs.put("product_id", Integer.parseInt((String) produtoId));
                    try {
                        if (sql.insert().values(attrs).run() instanceof BaseModel)
                            session.setAttribute("msg", "Produto avaliado com sucesso!");
                    } catch (SQLException e) {
                        throw new Exception("Falha ao avaliar produto: " + e.getMessage());
                    } finally {
                        session.setAttribute("rating", null);
                    }
                    response.sendRedirect("ProdutoController");
                    return;
                }
            }

            request.getRequestDispatcher("produto-avalia.jsp").forward(request, response);
            // se der erro vai p ControllerProdutos
        } catch (Exception ex) {
            session.setAttribute("msg", ex.getMessage());
            response.sendRedirect("ProdutoController");
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
