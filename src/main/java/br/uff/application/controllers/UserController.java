/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.application.controllers;

import br.uff.application.models.User;
import br.uff.dao.MySql;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {
        // pega sessao
        HttpSession session = request.getSession();
        try {

            if (request.getParameter("sel") != null) {
                session.setAttribute("selUser", request.getParameter("sel"));
            }

            String selUser = null;
            if (session.getAttribute("selUser") != null) {
                selUser = session.getAttribute("selUser").toString();
            }

            // recupera acao solicitada se existir
            String action = "";
            if (request.getParameter("action") != null) {
                action = request.getParameter("action");
            }

            switch (action) {
                case "grava": {
                    // grava alteracoes no cadastro d usuario feito na pag de usuario-cadastro
                    MySql db = null;
                    try {
                        db = new MySql();
                        // pega variaveis
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        String roleId = request.getParameter("roleId");
                        String name = request.getParameter("name");
                        String[] bind = null;
                        String comando = "";
                        if (selUser.equals("")) {
                            String[] bindUpdate = {email, password, roleId, name};
                            bind = bindUpdate;
                            comando = "INSERT INTO users (email,password,role_id,name) VALUES (?,?,?,?)";
                        } else {
                            String[] bindInsert = {email, password, roleId, name, selUser};
                            bind = bindInsert;
                            comando = "UPDATE users set email=?,password=?,role_id=?,name=? WHERE id=?";
                        }
                        db.dbGrava(comando, bind);
                        session.setAttribute("msg", "Usuário gravado com sucesso!");
                    } catch (Exception ec) {
                        throw new Exception(ec.getMessage());
                    } finally {
                        db.destroyDb();
                    }
                    break;
                }
                case "logout": {
                    // invalida sessao
                    //session.invalidate();
                    // define variaveis de sessao d usuario como null
                    session.setAttribute("userId", null);
                    session.setAttribute("userRole", null);
                    session.setAttribute("msg", "Usuário deslogado com sucesso!");
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
                    } catch (Exception ec) {
                        throw new Exception(ec.getMessage());
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
                case "unsel": {
                    session.setAttribute("selUser", null);
                    response.sendRedirect("UserController");
                    return;
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

            // se tem usuario logado manda p conta caso contrario p login
            if (session.getAttribute("userId") != null) {
                //recupera userId da sessao
                String userId = session.getAttribute("userId").toString();
                // define redirect se n foi passado
                if (redirect == null || "null".equals(redirect)) {
                    if (selUser != null) {
                        if (!selUser.equals(userId) && !session.getAttribute("userRole").toString().equals("1")) {
                            session.setAttribute("selUser", null);
                            throw new Exception("Acesso não permitido!");
                        }
                        User usuario;
                        usuario = new User("", "", "", 2);
                        // define endereco
                        MySql dbEnd = null;
                        try {
                            dbEnd = new MySql();
                            String[] bindSel = {selUser};
                            ResultSet ret = dbEnd.dbCarrega("SELECT * FROM users WHERE id=?", bindSel);
                            if (ret.next()) {
                                // preenche endereco
                                usuario.setId(ret.getInt("id"));
                                usuario.setName(ret.getString("name"));
                                usuario.setPassword(ret.getString("password"));
                                usuario.setEmail(ret.getString("email"));
                                usuario.setRoleId(ret.getInt("role_id"));
                            }
                        } catch (SQLException ex) {
                            throw new Exception("Erro ao recuperar registros do banco: " + ex.getMessage());
                        } finally {
                            dbEnd.destroyDb();
                        }
                        // define atributo de produto
                        request.setAttribute("usuario", usuario);
                        redirect = "usuario-cadastro.jsp";
                    } else {
                        // define grid
                        MySql dbUsuarios = null;
                        try {
                            dbUsuarios = new MySql();
                            String consulta = "SELECT id,name,email FROM users";
                            String[] bind = {userId};
                            // se for adm n insere filtro e define bind como null
                            if (session.getAttribute("userRole").equals("1")) {
                                bind = null;
                            } else {
                                consulta += " WHERE id=?";
                            }
                            ArrayList<ArrayList> usuarios = new ArrayList<>();
                            ResultSet ret = dbUsuarios.dbCarrega(consulta, bind);
                            while (ret.next()) {
                                ArrayList<String> row = new ArrayList<>();
                                // preenche row
                                row.add(ret.getString("id"));
                                row.add(ret.getString("name"));
                                row.add(ret.getString("email"));
                                // add row no grid
                                usuarios.add(row);
                            }
                            request.setAttribute("usuarios", usuarios);
                        } catch (SQLException ed) {
                            throw new Exception("Erro ao recuperar registros do banco: " + ed.getMessage());
                        } finally {
                            dbUsuarios.destroyDb();
                        }
                        redirect = "usuario-grid.jsp";
                    }
                } else {
                    response.sendRedirect(redirect);
                    return;
                }
            } else {
                // define redirect q sera passado p pag d login
                request.setAttribute("redirect", redirect);
                redirect = "login.jsp";
            }

            request.getRequestDispatcher(redirect).forward(request, response);
            return;
        } catch (Exception ex) {
            session.setAttribute("msg", ex.getMessage());
            response.sendRedirect("");
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
