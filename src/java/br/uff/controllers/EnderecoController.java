/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.controllers;

import br.uff.models.Address;
import br.uff.models.BaseModel;
import br.uff.sql.ConnectionManager;
import br.uff.sql.SqlManager;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 *
 * @author HP
 */
@WebServlet(name = "EnderecoController", urlPatterns = {"/EnderecoController"})
public class EnderecoController extends HttpServlet {
    @Override
    public void init() {
        try {
            ConnectionManager.connect();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EnderecoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void destroy() {
        try {
            ConnectionManager.close();
        } catch (SQLException ex) {
            Logger.getLogger(EnderecoController.class.getName()).log(Level.SEVERE, null, ex);
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
        SqlManager sql = new SqlManager(Address.class);

        try {
            // recupera usuario logado
            String userId = null;
            if (session.getAttribute("userId") != null) {
                userId = session.getAttribute("userId").toString();
            } else {
                response.sendRedirect("UserController?redirect=EnderecoController");
                return;
            }

            if (request.getParameter("sel") != null) {
                String selParameter = request.getParameter("sel");
                Address endereco;
                endereco = new Address("", "", "", "", "", "");
                if (!selParameter.equals("")) {
                    // define endereco
                    endereco = (Address) sql.find(Integer.parseInt(selParameter));
                }
                // define atributo de produto
                request.setAttribute("endereco", endereco);
                session.setAttribute("sel", selParameter);
                request.getRequestDispatcher("endereco-cadastro.jsp").forward(request, response);
                return;
            }

            // define endereco selecionado
            String sel = null;
            if (session.getAttribute("sel") != null) {
                sel = session.getAttribute("sel").toString();
            }

            // recupera acao solicitada se existir
            String action = "";

            if (request.getParameter("action") != null) {
                action = request.getParameter("action");
            }

            switch (action) {
                case "grava": {
                    // grava alteracoes do session.getAttribute("produtoId")
                    HashMap<String, Object> attrs = new HashMap() {{
                        put("name", request.getParameter("name"));
                        put("address", request.getParameter("address"));
                        put("zipcode", request.getParameter("zipcode"));
                        put("city", request.getParameter("city"));
                        put("state", request.getParameter("state"));
                        put("country", request.getParameter("country"));
                    }};

                    try {
                        if (sel.equals("")) {
                            attrs.put("user_id", userId);
                            sql.insert().values(attrs).run();
                        } else {
                            sql.update().where("id="+sel).set(attrs).run();
                        }
                        session.setAttribute("msg", "Endereço gravado com sucesso!");
                    } catch (SQLException e) {
                        throw new Exception("Falha ao gravar endereço: " + e.getMessage());
                    }
                    break;
                }
                case "del": {
                    try {
                        sql.delete().where("id="+sel).run();
                        session.setAttribute("msg", "Endereço deletado com sucesso!");
                    } catch (SQLException ed) {
                        throw new Exception(ed.getMessage());
                    }
                    break;
                }
                case "unsel": {
                    // apaga sel da sessao
                    session.setAttribute("sel", null);
                    break;
                }
            }

            // define grid
            try {
                ArrayList<Address> addresses = new ArrayList();
                sql.select().where("user_id="+userId).run().forEach((r) -> {
                    addresses.add((Address) r);
                });
                request.setAttribute("grid", addresses);
            } catch (SQLException ex) {
                throw new Exception("Erro ao recuperar registros do banco: " + ex.getMessage());
            }

            // manda p pag de grid de produtos
            request.getRequestDispatcher("endereco-grid.jsp").forward(request, response);
        } catch (Exception e) {
            session.setAttribute("msg", e.getMessage());
            response.sendRedirect("UserController");
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
