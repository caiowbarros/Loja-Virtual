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

import br.uff.loja.core.dtos.EnderecoDTO;
import br.uff.loja.core.interfaces.services.IEnderecoService;
import br.uff.loja.core.services.EnderecoService;
import br.uff.loja.infrastructure.shared.Helper;

@WebServlet("/endereco")
public class EnderecoServlet extends HttpServlet {
    private final IEnderecoService enderecoService;
    private final Helper helper;

    public EnderecoServlet() {
        helper = new Helper();
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
            Integer userId = null;
            // recupera usuario logado
            if (session.getAttribute("userId") != null) {
                userId = helper.tryParseInteger(session.getAttribute("userId").toString());
            } else {
                response.sendRedirect("usuario?redirect=endereco");
                return;
            }

            if (request.getParameter("sel") != null) {
                String enderecoIdSelStr = request.getParameter("sel");
                Integer enderecoIdSel = helper.tryParseInteger(enderecoIdSelStr);
                EnderecoDTO endereco = new EnderecoDTO();
                if(enderecoIdSel != null) {
                    endereco = enderecoService.encontraEnderecoPorId(enderecoIdSel);
                }
                request.setAttribute("endereco", null);
                session.setAttribute("sel", enderecoIdSelStr);
                request.getRequestDispatcher("pages/endereco-cadastro.jsp").forward(request, response);
                return;
            }

            // define endereco selecionado
            Integer enderecoIdSel = null;
            if (session.getAttribute("sel") != null) {
                enderecoIdSel = helper.tryParseInteger(session.getAttribute("sel").toString());
            }

            // recupera acao solicitada se existir
            String action = "";

            if (request.getParameter("action") != null) {
                action = request.getParameter("action");
            }

            switch (action) {
                case "grava": {
                    EnderecoDTO enderecoDto = new EnderecoDTO(
                        request.getParameter("name"),
                        userId,
                        helper.tryParseInteger(request.getParameter("zipcode")),
                        request.getParameter("address"),
                        request.getParameter("city"),
                        request.getParameter("state"),
                        request.getParameter("country")
                    );

                    if (enderecoIdSel != null) {
                        enderecoService.atualizaEnderecoPorId(enderecoIdSel, enderecoDto);
                    } else {
                        enderecoService.insereEndereco(enderecoDto);
                    }

                    session.setAttribute("msg", "Endereço gravado com sucesso!");
                    break;
                }
                case "del": {
                    enderecoService.excluiEnderecoPorId(enderecoIdSel);

                    session.setAttribute("msg", "Endereço deletado com sucesso!");
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

            request.setAttribute("grid", enderecoService.listaEnderecosPorUsuarioId(userId));

            // manda p pag de grid de produtos
            request.getRequestDispatcher("pages/endereco-grid.jsp").forward(request, response);
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
            Logger.getLogger(EnderecoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EnderecoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet pra tratar interações com endereços.";
    }// </editor-fold>

}
