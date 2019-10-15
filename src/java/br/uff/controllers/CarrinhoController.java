/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.controllers;

import br.uff.dao.MySql;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 *
 * @author HP
 */
@WebServlet(name = "CarrinhoController", urlPatterns = {"/CarrinhoController"})
public class CarrinhoController extends HttpServlet {

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

            String carrinhoId = null;
            Cookie[] cookies = request.getCookies();
            // procura carrinhoId nos cookies setados
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie cookie = cookies[i];
                    if (cookie.getName().equals("carrinhoId")) {
                        carrinhoId = cookie.getValue().toString();
                        continue;
                    }
                }
            }

            String userId = null;
            // recupera userId e se tiver carrinhoId como cookie mas n tiver userId cadastrado p esse carrinho id cadastra user
            if (session.getAttribute("userId") != null) {
                userId = session.getAttribute("userId").toString();
                if (carrinhoId != null) {
                    MySql db = null;
                    try {
                        db = new MySql();
                        String[] bind = {userId, carrinhoId};
                        db.dbGrava("UPDATE carts set user_id=? WHERE id=? AND user_id IS NULL", bind);
                    } catch (Exception ed) {
                        throw new Exception(ed.getMessage());
                    } finally {
                        db.destroyDb();
                    }
                }
            }

            //verifica se carrinhoId eh um numero
            if (carrinhoId != null) {
                try {
                    int validador = Integer.valueOf(carrinhoId);
                } catch (Exception ec) {
                    carrinhoId = null;
                }
            }

            // se n achou o cookie cria e seta
            if (carrinhoId == null) {
                // formato da data p mysql
                String pattern = "yyyy-MM-dd HH:mm:ss";
                // define novo simpleDateFormat
                DateFormat df = new SimpleDateFormat(pattern);
                // pega o time pelo calendar
                Date data = Calendar.getInstance().getTime();
                // monta string createdAt
                String createdAt = df.format(data);
                String ip = request.getRemoteAddr().toString();
                String[] bind = {ip, createdAt, userId};
                MySql db = null;
                try {
                    db = new MySql();
                    db.dbGrava("INSERT INTO carts (ip,created_at,user_id) VALUES (?,?,?)", bind);
                    carrinhoId = db.dbValor("max(id)", "carts", "ip=? AND created_at=? AND user_id " + (userId == null ? " IS " : " = ") + " ?", bind);
                } catch (SQLException ed) {
                    throw new Exception(ed.getMessage());
                } finally {
                    db.destroyDb();
                }
                int durMes = 2592000;
                Cookie ckCarrinhoId = new Cookie("carrinhoId", carrinhoId);
                ckCarrinhoId.setMaxAge(durMes);
                response.addCookie(ckCarrinhoId);
            }

            if (request.getParameter("addProdutoId") != null) {
                // ADD PRODUTO NO SESSIONID
            } else if (request.getParameter("confirmaCompra") != null) {
                // manda atributos para a pagina definida, no caso carrinho-confirma.jsp
                request.getRequestDispatcher("carrinho-confirma.jsp").forward(request, response);
            }

            // recupera acao solicitada se existir
            String action = request.getParameter("action");

            if ("mudaQtd".equals(action)) {
                // muda qtd d produtos no carrinho
                // define msg a ser mostrada
                request.setAttribute("msg", "Quantidade de Produtos alterada com sucesso!");
            } else if ("removeProdutoId".equals(action)) {
                // remove produtoId do carrinho de sessao id
                // define msg a ser mostrada
                request.setAttribute("msg", "Produto removido do carrinho com sucesso!");
            } else if ("finalizaCompra".equals(action)) {
                // manda atributos para a pagina definida, no caso compra-pagamento.jsp
                request.getRequestDispatcher("compra-pagamento.jsp").forward(request, response);
            } else if ("continuaCompra".equals(action)) {
                // redireciona p controller de ProdutosController
                response.sendRedirect("ProdutosController");
            }

            // manda atributos para a pagina definida, no caso carrinho.jsp
            request.getRequestDispatcher("carrinho.jsp").forward(request, response);
        } catch (Exception ex) {
            session.setAttribute("msg", ex.getMessage());
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
