package br.uff.loja.application.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.uff.loja.core.interfaces.services.IAvaliacaoService;
import br.uff.loja.core.interfaces.services.IProdutoService;
import br.uff.loja.core.services.AvaliacaoService;
import br.uff.loja.core.services.ProdutoService;
import br.uff.loja.infrastructure.shared.Helper;

@WebServlet("/produto")
public class ProdutoServlet extends HttpServlet {
    private static final String PRODUTOID = "produtoId";
    private final IProdutoService produtoService;
    private final IAvaliacaoService avaliacaoService;
    private final Helper helper;

    public ProdutoServlet() {
        helper = new Helper();
        produtoService = new ProdutoService();
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
            // se rating estiver setado na sessao... define null
            session.setAttribute("rating", null);

            // se tem produto selecionado por parametro passa p sessao
            Integer produtoId = null;
            if (request.getParameter(PRODUTOID) != null) {
                produtoId = helper.tryParseInteger(request.getParameter(PRODUTOID));
                session.setAttribute(PRODUTOID, produtoId);
            }

            // se produtoId da sessao for null manda p pag d produtos
            if (session.getAttribute(PRODUTOID) != null) {
                produtoId = helper.tryParseInteger(session.getAttribute(PRODUTOID).toString());
            } else {
                session.setAttribute("msg", "Por favor, selecione um produto.");
                response.sendRedirect("produtos");
                return;
            }

            // recupera user id
            Integer userId = null;
            if (session.getAttribute("userId") != null) {
                userId = helper.tryParseInteger(session.getAttribute("userId").toString());
            }            

            if (request.getParameter("fav") != null) {
                // VERIFICA SE ESTA LOGADO
                if (userId == null) {
                    session.setAttribute("msg", "Realize login para favoritar um produto!");
                    response.sendRedirect("usuario?redirect=produto");
                    return;
                } else {
                    produtoService.usuarioToogleFavoritaProdutoPorId(produtoId, userId);
                    request.removeAttribute("fav");
                    response.sendRedirect("produto");
                    return;
                }
            }
            request.setAttribute("produto", produtoService.mostraProdutoVitrineParaUsuario(produtoId, userId));
            request.setAttribute("avaliacoes", avaliacaoService.recuperaAvaliacoesDeUmProduto(produtoId));

            request.getRequestDispatcher("pages/produto.jsp").forward(request, response);
        } catch (Exception ex) {
            session.setAttribute("msg", ex.getMessage());
            response.sendRedirect("produtos");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ProdutoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ProdutoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Esse servlet trata a visualização do produto para o usuário.";
    }// </editor-fold>

}