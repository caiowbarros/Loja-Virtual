/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.controllers;

import br.uff.dao.MySql;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
@WebServlet(name = "CompraController", urlPatterns = {"/CompraController"})
public class CompraController extends HttpServlet {

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
            if (session.getAttribute("userId") == null) {
                response.sendRedirect("UserController?redirect=CarrinhoController");
                return;
            }

            String end = null;
            if (request.getAttribute("end") != null) {
                end = request.getAttribute("end").toString();
            }

            if (end != null) {
                String consultaEnd = "SELECT\n"
                        + "	u.`name`,\n"
                        + "	a.address,\n"
                        + "	a.zipcode,\n"
                        + "	a.city,\n"
                        + "	a.state,\n"
                        + "	a.country\n"
                        + "FROM\n"
                        + "	address a\n"
                        + "LEFT JOIN users u ON (a.user_id = u.id)\n"
                        + "WHERE\n"
                        + "	a.id = ?";
                String[] bindEnd = {end};
                MySql db = null;
                try {
                    db = new MySql();
                    ArrayList<String> endereco = new ArrayList<>();
                    ResultSet retorno = db.dbCarrega(consultaEnd, bindEnd);
                    if (retorno.next()) {
                        endereco.add(retorno.getString("name"));
                        endereco.add(retorno.getString("address"));
                        endereco.add(retorno.getString("zipcode"));
                        endereco.add(retorno.getString("city"));
                        endereco.add(retorno.getString("state"));
                        endereco.add(retorno.getString("country"));
                    }
                    request.setAttribute("endereco", endereco);
                } catch (SQLException ed) {
                    throw new Exception(ed.getMessage());
                } finally {
                    db.destroyDb();
                }
            }

            // recupera acao solicitada se existir
            String action = request.getParameter("action");

            // manda atributos para a pagina definida, no caso carrinho.jsp
            request.getRequestDispatcher("compra-pagamento.jsp").forward(request, response);
        } catch (Exception ex) {
            session.setAttribute("msg", ex.getMessage());
            response.sendRedirect("CarrinhoController");
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
