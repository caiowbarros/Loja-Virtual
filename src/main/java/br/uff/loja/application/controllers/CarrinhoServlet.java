package br.uff.loja.application.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.uff.loja.core.interfaces.services.ICarrinhoService;
import br.uff.loja.core.interfaces.services.IEnderecoService;
import br.uff.loja.core.services.CarrinhoService;
import br.uff.loja.core.services.EnderecoService;
import br.uff.loja.infrastructure.shared.Helper;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {
    private final ICarrinhoService carrinhoService;
    private final IEnderecoService enderecoService;
    private static final String CARRINHOID = "carrinhoId";
    private static final String PRECOTOTAL = "totalPrice";
    private static final String PRODUTOS = "produtos";
    private final Helper helper;

    public CarrinhoServlet() {
        helper = new Helper();
        carrinhoService = new CarrinhoService();
        enderecoService = new EnderecoService();
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
            String carrinhoIdString = null;
            Cookie[] cookies = request.getCookies();
            // procura carrinhoId nos cookies setados
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie cookie = cookies[i];
                    if (cookie.getName().equals(CARRINHOID)) {
                        carrinhoIdString = cookie.getValue();
                        break;
                    }
                }
            }

            Integer userId = null;
            // recupera userId
            if (session.getAttribute("userId") != null) {
                userId = helper.tryParseInteger(session.getAttribute("userId").toString());
            }

            Integer carrinhoId = null;

            // verifica se carrinhoIdString eh um numero
            if (carrinhoIdString != null) {
                carrinhoId = helper.tryParseInteger(carrinhoIdString);
            }

            carrinhoId = carrinhoService.recuperaCarrinhoAtivo(carrinhoId, userId, request.getRemoteAddr()).getId();

            // define cookie com carrinhoId
            int durMes = 2592000;
            Cookie ckCarrinhoId = new Cookie(CARRINHOID, carrinhoId.toString());
            ckCarrinhoId.setMaxAge(durMes);
            response.addCookie(ckCarrinhoId);
            // define variavel de sessao p ser recuperado
            session.setAttribute(CARRINHOID, carrinhoId);
            
            Integer produtoId = null;
            // recupera produtoId pra add no carrinho
            if (request.getParameter("addProdutoId") != null) {
                produtoId = helper.tryParseInteger(request.getParameter("addProdutoId"));
            }

            // se produtoId nao for null add
            if (produtoId != null) {
                carrinhoService.insereProdutoCarrinho(carrinhoId, produtoId);
                session.setAttribute("msg", "Produto inserido no carrinho com sucesso!");
            }

            // recupera produtoId do request parameter
            if (request.getParameter("produtoId") != null) {
                produtoId = helper.tryParseInteger(request.getParameter("produtoId"));
            }

            // recupera acao solicitada se existir
            String action = "";
            if (request.getParameter("action") != null) {
                action = request.getParameter("action");
            }

            switch (action) {
                case "finalizaCompra": {
                    request.setAttribute("enderecos", enderecoService.listaEnderecosPorUsuarioId(userId));
                    session.setAttribute(PRODUTOS, carrinhoService.listaProdutosCarrinho(carrinhoId));
                    session.setAttribute(PRECOTOTAL, carrinhoService.recuperaPrecoTotalDeUmCarrinho(carrinhoId));
                    request.getRequestDispatcher("pages/carrinho-confirma.jsp").forward(request, response);
                    return;
                }
                case "continuaCompra": {
                    // redireciona p controller de ProdutosController
                    response.sendRedirect(PRODUTOS);
                    return;
                }
                case "removeProduto": {
                    carrinhoService.removeProdutoCarrinho(carrinhoId, produtoId);
                    break;
                }
                case "mudaQtd": {
                    Integer qtdProduto = null;
                    // recupera qtdProduto
                    if (request.getParameter("qtdProduto") != null) {
                        qtdProduto = helper.tryParseInteger(request.getParameter("qtdProduto"));
                    }

                    carrinhoService.alteraQuantidadeProdutoCarrinho(carrinhoId, produtoId, qtdProduto);

                    break;
                }
                case "continuaPagamento": {
                    Integer enderecoId = null;
                    // recupera enderecoId
                    if (request.getParameter("end") != null) {
                        enderecoId = helper.tryParseInteger(request.getParameter("end"));
                    }
                    
                    if (enderecoId == null) {
                        session.setAttribute("msg", "Por favor selecione um endereço!");
                        session.setAttribute("enderecoId", null);
                        request.setAttribute(PRECOTOTAL, null);
                        response.sendRedirect("carrinho");
                        return;
                    } else {
                        session.setAttribute("enderecoId", enderecoId);
                    }
                    
                    session.setAttribute(PRODUTOS, carrinhoService.listaProdutosCarrinho(carrinhoId));
                    
                    request.getRequestDispatcher("compra").forward(request, response);
                    return;
                }
                default: {
                    break;
                }
            }

            request.setAttribute(PRODUTOS, carrinhoService.listaProdutosCarrinho(carrinhoId));
            request.setAttribute(PRECOTOTAL, carrinhoService.recuperaPrecoTotalDeUmCarrinho(carrinhoId));

            // manda atributos para a pag do carrinho
            request.getRequestDispatcher("pages/carrinho.jsp").forward(request, response);
        } catch (Exception ex) {
            session.setAttribute("msg", ex.getMessage());
            response.sendRedirect(PRODUTOS);
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
            Logger.getLogger(CarrinhoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CarrinhoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet pra tratar ações no carrinho.";
    }// </editor-fold>

}
