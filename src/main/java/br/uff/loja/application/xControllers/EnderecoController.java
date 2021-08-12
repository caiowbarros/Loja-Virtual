/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.application.controllers;

import br.uff.application.models.Address;
import br.uff.application.models.Product;
import br.uff.dao.MySql;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 *
 * @author HP
 */
@WebServlet(name = "EnderecoController", urlPatterns = {"/EnderecoController"})
public class EnderecoController extends HttpServlet {

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
                    MySql dbEnd = null;
                    try {
                        dbEnd = new MySql();
                        String[] bindSel = {selParameter};
                        ResultSet ret = dbEnd.dbCarrega("SELECT * FROM address WHERE id=?", bindSel);
                        if (ret.next()) {
                            // preenche endereco
                            endereco.setId(ret.getInt("id"));
                            endereco.setName(ret.getString("name"));
                            endereco.setAddress(ret.getString("address"));
                            endereco.setCity(ret.getString("city"));
                            endereco.setCountry(ret.getString("country"));
                            endereco.setState(ret.getString("state"));
                            endereco.setZipcode(ret.getString("zipcode"));
                        }
                    } catch (SQLException ex) {
                        throw new Exception("Erro ao recuperar registros do banco: " + ex.getMessage());
                    } finally {
                        dbEnd.destroyDb();
                    }
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
                    String name = request.getParameter("name");
                    String address = request.getParameter("address");
                    String zipcode = request.getParameter("zipcode");
                    String city = request.getParameter("city");
                    String state = request.getParameter("state");
                    String country = request.getParameter("country");

                    MySql db = null;
                    try {
                        db = new MySql();
                        if (sel.equals("")) {
                            String[] bind = {name, address, zipcode, city, state, country, userId};
                            db.dbGrava("INSERT INTO address (name, address, zipcode, city, state, country, user_id) VALUES (?,?,?,?,?,?,?)", bind);
                        } else {
                            String[] bind = {name, address, zipcode, city, state, country, sel};
                            db.dbGrava("UPDATE address set name=?, address=?, zipcode=?, city=?, state=?, country=? WHERE id=?", bind);
                        }
                        session.setAttribute("msg", "Endereço gravado com sucesso!");
                    } catch (ClassNotFoundException | SQLException e) {
                        throw new Exception("Falha ao gravar endereço: " + e.getMessage());
                    } finally {
                        db.destroyDb();
                    }
                    break;
                }
                case "del": {
                    MySql db = null;
                    try {
                        db = new MySql();
                        String[] bindDel = {sel};
                        db.dbGrava("DELETE FROM address WHERE id=?", bindDel);
                        session.setAttribute("msg", "Endereço deletado com sucesso!");
                    } catch (Exception ed) {
                        throw new Exception(ed.getMessage());
                    } finally {
                        db.destroyDb();
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
            MySql dbGrid = null;
            try {
                dbGrid = new MySql();
                ArrayList<ArrayList> grid = new ArrayList<>();
                String[] bindAddress = {userId};
                ResultSet ret = dbGrid.dbCarrega("SELECT id,name,address,city,state FROM address WHERE user_id=?", bindAddress);
                while (ret.next()) {
                    ArrayList<String> row = new ArrayList<>();
                    // preenche row
                    row.add(ret.getString("id"));
                    row.add(ret.getString("name"));
                    row.add(ret.getString("address"));
                    row.add(ret.getString("city"));
                    row.add(ret.getString("state"));
                    // add row no grid
                    grid.add(row);
                }
                request.setAttribute("grid", grid);
            } catch (SQLException ex) {
                throw new Exception("Erro ao recuperar registros do banco: " + ex.getMessage());
            } finally {
                dbGrid.destroyDb();
            }

            // manda p pag de grid de produtos
            request.getRequestDispatcher("endereco-grid.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            session.setAttribute("msg", e.getMessage());
            response.sendRedirect("UserController");
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
