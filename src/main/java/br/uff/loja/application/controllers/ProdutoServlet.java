package br.uff.loja.application.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.uff.loja.core.dtos.AvaliacaoProdutoListDTO;
import br.uff.loja.core.dtos.ProdutoVitrineUsuarioDTO;
import br.uff.loja.core.interfaces.services.IAvaliacaoService;
import br.uff.loja.core.interfaces.services.IProdutoService;
import br.uff.loja.core.services.AvaliacaoService;
import br.uff.loja.core.services.ProdutoService;

@WebServlet("/produto")
public class ProdutoServlet extends HttpServlet {
    private static final String PRODUTOID = "produtoId";
    private final IProdutoService produtoService;
    private final IAvaliacaoService avaliacaoService;

    public ProdutoServlet() {
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
            // se tem produto selecionado por parametro passa p sessao
            String produtoId = null;
            if (request.getParameter(PRODUTOID) != null) {
                produtoId = request.getParameter(PRODUTOID);
                session.setAttribute(PRODUTOID, produtoId);
            }

            // se produtoId da sessao for null manda p pag d produtos
            if (session.getAttribute(PRODUTOID) != null) {
                produtoId = session.getAttribute(PRODUTOID).toString();
            } else {
                session.setAttribute("msg", "Por favor, selecione um produto.");
                response.sendRedirect("produtos");
                return;
            }

            // se fav esta definido por parametro passa p sessao
            String fav = null;
            if (request.getParameter("fav") != null) {
                fav = request.getParameter("fav");
                session.setAttribute("fav", fav);
            }
            // recupera fav da sessao
            if (session.getAttribute("fav") != null) {
                fav = session.getAttribute("fav").toString();
            }

            // recupera user id
            String userId = null;
            if (session.getAttribute("userId") != null) {
                userId = session.getAttribute("userId").toString();
            }

            // checa se eh para favoritar ou desfavoritar produto
            if (fav != null) {
                // VERIFICA SE ESTA LOGADO
                if (userId == null) {
                    response.sendRedirect("usuario?redirect=produto");
                    return;
                } else {
                    produtoService.usuarioToogleFavoritaProdutoPorId(Integer.valueOf(produtoId), Integer.valueOf(userId));
                }
            }
            ProdutoVitrineUsuarioDTO produto = produtoService.mostraProdutoVitrineParaUsuario(Integer.valueOf(produtoId), Integer.valueOf(userId));
            request.setAttribute("produto", produto);

            List<AvaliacaoProdutoListDTO> avaliacoes = avaliacaoService.recuperaAvaliacoesDeUmProduto(Integer.valueOf(produtoId));
            request.setAttribute("avaliacoes", avaliacoes);

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
        return "Esse servlet trata a visualização do produto para o usuário.";
    }// </editor-fold>

}