package br.uff.loja.application.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.uff.loja.core.dtos.ProdutoDTO;
import br.uff.loja.core.enums.EPermissaoUsuario;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.services.IProdutoService;
import br.uff.loja.core.services.ProdutoService;
import br.uff.loja.infrastructure.shared.Helper;

@WebServlet("/produto-adm")
public class ProdutoAdmServlet extends HttpServlet {
    private final IProdutoService produtoService;

    public ProdutoAdmServlet() {
        produtoService = new ProdutoService();
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        // pega sessao
        HttpSession session = request.getSession();
        try {
            // opcoes restritas a usuario ADM
            if (session.getAttribute("userRole").equals(EPermissaoUsuario.ADM.getId())) {

                if (request.getParameter("sel") != null) {
                    Integer selParameter = new Helper().tryParseInteger(request.getParameter("sel"));
                    ProdutoDTO produto = produtoService.encontraProdutoPorId(selParameter);
                    // define atributo de produto
                    request.setAttribute("produto", produto);
                    session.setAttribute("sel", selParameter);
                    request.getRequestDispatcher("pages/produto-cadastro.jsp").forward(request, response);
                    return;
                }

                // define produto selecionado
                Integer sel = null;
                if (session.getAttribute("sel") != null) {
                    sel = new Helper().tryParseInteger(session.getAttribute("sel").toString());
                }

                // recupera acao solicitada se existir
                String action = "";
                if (request.getParameter("action") != null) {
                    action = request.getParameter("action");
                }

                switch (action) {
                    case "grava": {
                        // grava alteracoes do session.getAttribute("produtoId")
                        ProdutoDTO produtoDto = new ProdutoDTO(
                            sel,
                            request.getParameter("name"),
                            Double.valueOf(request.getParameter("price")),
                            request.getParameter("description"),
                            request.getParameter("src"),
                            new Helper().tryParseInteger(request.getParameter("categoryId")),
                            0
                        );
                        
                        if (sel == null) {
                            produtoService.insereProduto(produtoDto);
                        } else {
                            produtoService.atualizaProdutoPorId(sel, produtoDto);
                        }
                        session.setAttribute("msg", "Produto gravado com sucesso!");

                        break;
                    }
                    case "estoqueInsere": {
                        produtoService.insereQuantidadeEmEstoqueDoProdutoPorId(sel, new Helper().tryParseInteger(request.getParameter("quantity")));
                        session.setAttribute("msg", "Quantidade em estoque aumentada com sucesso!");
                        break;
                    }
                    case "del": {
                        produtoService.excluiProdutoPorId(sel);
                        session.setAttribute("msg", "Produto deletado com sucesso!");
                        break;
                    }
                    case "unsel": {
                        // apaga sel da sessao
                        session.setAttribute("sel", null);
                        break;
                    }
                    default: {
                        break;
                    }
                }
            } else {
                throw new LojaException("Usuário não permitido!");
            }

            // define grid
            request.setAttribute("grid", produtoService.listaProdutosAdm());

            // manda p pag de grid de produtos
            request.getRequestDispatcher("pages/produto-grid.jsp").forward(request, response);
        } catch (Exception e) {
            session.setAttribute("msg", e.getMessage());
            response.sendRedirect("usuario");
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
            Logger.getLogger(ProdutoAdmServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ProdutoAdmServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet pra administrar produtos.";
    }// </editor-fold>

}
