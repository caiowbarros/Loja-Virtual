package br.uff.loja.application.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;
import br.uff.loja.core.interfaces.services.IAvaliacaoService;
import br.uff.loja.core.services.AvaliacaoService;

@WebServlet("/avaliacao")
public class AvaliacaoServlet extends HttpServlet {
    private final IAvaliacaoService avaliacaoService;
    private static final String PRODUTOID = "produtoId";
    private static final String RATING = "rating";

    public AvaliacaoServlet() {
        avaliacaoService = new AvaliacaoService();
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
            // seta produtoId na sessao se estiver passado por parametro antes de verificar login
            if (request.getParameter(PRODUTOID) != null) {
                session.setAttribute(PRODUTOID, request.getParameter(PRODUTOID));
            }

            // seta rating na sessao se estiver passado por parametro antes de verificar login
            if (request.getParameter(RATING) != null) {
                session.setAttribute(RATING, request.getParameter(RATING));
            }

            // se n tem usuario logado manda p controller de user
            String userId = null;
            if (session.getAttribute("userId") == null) {
                response.sendRedirect("usuario?redirect=avaliacao");
                return;
            } else {
                userId = session.getAttribute("userId").toString();
            }

            String produtoId = null;
            if (session.getAttribute(PRODUTOID) != null) {
                produtoId = session.getAttribute(PRODUTOID).toString();
            } else {
                session.setAttribute("msg", "Por favor, selecione um produto.");
                response.sendRedirect("produtos");
                return;
            }

            // recupera acao solicitada se existir
            String action = "";
            if (request.getParameter("action") != null) {
                action = request.getParameter("action");
            }

            if (action.equals("avalia")) {
                avaliacaoService.avaliaProduto(new AvaliacaoProdutoInsertDTO(Integer.valueOf(userId), Integer.valueOf(produtoId), Integer.valueOf(session.getAttribute(RATING).toString()), request.getParameter("description"), request.getParameter("title")));
                response.sendRedirect("produto");
                return;
            }

            request.getRequestDispatcher("pages/produto-avalia.jsp").forward(request, response);
        } catch (Exception ex) {
            session.setAttribute("msg", ex.getMessage());
            response.sendRedirect("produto");
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
        return "Servlet pra tratar avaliações dos usuaários feitas sobre os produtos.";
    }// </editor-fold>

}

