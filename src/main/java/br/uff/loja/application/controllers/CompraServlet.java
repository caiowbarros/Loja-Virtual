package br.uff.loja.application.controllers;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.uff.loja.core.dtos.VendaDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.services.ICarrinhoService;
import br.uff.loja.core.interfaces.services.IEnderecoService;
import br.uff.loja.core.interfaces.services.IVendaService;
import br.uff.loja.core.services.CarrinhoService;
import br.uff.loja.core.services.EnderecoService;
import br.uff.loja.core.services.VendaService;
import br.uff.loja.infrastructure.shared.Helper;

@WebServlet("/compra")
public class CompraServlet extends HttpServlet {
    private final IVendaService vendaService;
    private final ICarrinhoService carrinhoService;
    private final IEnderecoService enderecoService;
    private final Helper helper;

    public CompraServlet() {
        vendaService = new VendaService();
        carrinhoService = new CarrinhoService();
        enderecoService = new EnderecoService();
        helper = new Helper();
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
            // se n tem usuario logado manda p controller de user
            Integer usuarioId = null;
            if (session.getAttribute("userId") == null) {
                response.sendRedirect("usuario?redirect=carrinho");
                return;
            } else {
                usuarioId = helper.tryParseInteger(session.getAttribute("userId").toString());
            }

            // se solicitar historico manda p pag d compras realizadas
            if (request.getParameter("historico") != null) {
                List<VendaDTO> vendas = vendaService.listaVendasDoUsuario(usuarioId);
                for(VendaDTO venda : vendas) {
                    venda.setProdutosDoCarrinho(carrinhoService.listaProdutosCarrinho(venda.getCarrinhoId()));
                    venda.setEndereco(enderecoService.encontraEnderecoPorId(venda.getEnderecoId()));
                }
                request.setAttribute("vendas", vendas);

                request.getRequestDispatcher("pages/compras.jsp").forward(request, response);
                return;
            }

            Integer enderecoId = null;
            if (session.getAttribute("enderecoId") != null) {
                enderecoId = helper.tryParseInteger(session.getAttribute("enderecoId").toString());
            }

            Integer carrinhoId = null;
            if (session.getAttribute("carrinhoId") != null) {
                carrinhoId = helper.tryParseInteger(session.getAttribute("carrinhoId").toString());
            }

            // recupera acao solicitada se existir
            String action = request.getParameter("action");

            switch (action) {
                case "pagamentoOk": {
                    vendaService.gravaVenda(usuarioId, carrinhoId, enderecoId);
                    response.sendRedirect("compra?historico");
                    return;
                }
                case "pagamentoErr": {
                    throw new LojaException("Houve um erro durante sua compra, realize o procedimento de confirmação de carrinho novamente.");
                }
                default: {
                    break;
                }
            }

            if (enderecoId != null) {
                request.setAttribute("endereco", enderecoService.encontraEnderecoPorId(enderecoId));
            } else {
                throw new LojaException("Por favor selecione um endereço!");
            }

            // manda atributos para a pagina definida, no caso carrinho.jsp
            request.getRequestDispatcher("pages/compra-pagamento.jsp").forward(request, response);
        } catch (Exception ex) {
            session.setAttribute("msg", ex.getMessage());
            response.sendRedirect("carrinho");
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
            Logger.getLogger(CompraServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CompraServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet pra tratar compras.";
    }// </editor-fold>

}
