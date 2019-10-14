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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 *
 * @author HP
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

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

            // recupera acao solicitada se existir
            String action = "";
            if (request.getParameter("action") != null) {
                action = request.getParameter("action");
            }

            switch (action) {
                case "grava": {
                    // grava alteracoes no cadastro d usuario feito na pag de usuario-cadastro
                    session.setAttribute("msg", "Usuário gravado com sucesso!");
                    break;
                }
                case "logout": {
                    // invalida sessao
                    session.invalidate();
                    //session.setAttribute("msg", "Usuário deslogado com sucesso!");
                    break;
                }
                case "login": {
                    MySql db = null;
                    try {
                        db = new MySql();
                        // pega variaveis
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        String[] bind = {email, password};
                        // inserindo com role = 2 (cliente)
                        ResultSet ret = db.dbCarrega("SELECT id,name,role_id FROM users WHERE email=? AND password=?", bind);
                        if (ret.next()) {
                            session.setAttribute("userId", ret.getString("id"));
                            session.setAttribute("userRole", ret.getString("role_id"));
                            session.setAttribute("msg", "Seja bem vindo " + ret.getString("name") + "!");
                            // seta cookie se solicitar para lembrar login
                            if (request.getParameter("remember") != null) {
                                int durMes = 2592000;
                                Cookie ckEmail = new Cookie("loginEmail", email);
                                ckEmail.setMaxAge(durMes);
                                response.addCookie(ckEmail);
                                Cookie ckPassword = new Cookie("loginPassword", password);
                                ckPassword.setMaxAge(durMes);
                                response.addCookie(ckPassword);
                            }
                        } else {
                            session.setAttribute("msg", "Combinação de email e senha inválidos!");
                        }
                    } catch (Exception ex) {
                        throw new Exception(ex.getMessage());
                    } finally {
                        db.destroyDb();
                    }
                    break;
                }
                case "insere": {
                    MySql db = null;
                    try {
                        db = new MySql();
                        // pega variaveis
                        String name = request.getParameter("nome");
                        String email = request.getParameter("email");
                        String password = request.getParameter("senha");
                        String role = "2";
                        String[] bind = {name, email, password, role};
                        // inserindo com role = 2 (cliente)
                        db.dbGrava("INSERT INTO users (name,email,password,role_id) VALUES (?,?,?,?)", bind);
                        String id = null;
                        id = db.dbValor("id", "users", "name=? AND email=? AND password=? AND role_id=?", bind);
                        session.setAttribute("userId", id);
                        session.setAttribute("userRole", role);
                        session.setAttribute("msg", "Seja bem vindo " + name + "!");
                    } catch (Exception ex) {
                        throw new Exception(ex.getMessage());
                    } finally {
                        db.destroyDb();
                    }
                    break;
                }
            }

            String redirect;
            // recupera redirect
            if (request.getAttribute("redirect") != null) {
                redirect = (String) request.getAttribute("redirect");
            } else if (request.getParameter("redirect") != null) {
                redirect = request.getParameter("redirect");
            } else {
                redirect = null;
            }

            try {
                // se tem usuario logado manda p conta caso contrario p login
                if (session.getAttribute("userId") != null) {
                    // define redirect se n foi passado
                    if (redirect == null || "null".equals(redirect)) {
                        redirect = "usuario-cadastro.jsp";
                    } else {
                        response.sendRedirect(redirect);
                        return;
                    }
                } else {
                    request.setAttribute("redirect", redirect);
                    redirect = "login.jsp";
                }
            } catch (Exception ex) {
                request.setAttribute("redirect", redirect);
                redirect = "login.jsp";
            }

            request.getRequestDispatcher(redirect).forward(request, response);
            return;
        } catch (Exception ex) {
            session.setAttribute("msg", ex.getMessage());
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
