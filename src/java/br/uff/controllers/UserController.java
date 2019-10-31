/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.controllers;

import br.uff.models.BaseModel;
import br.uff.models.User;
import br.uff.sql.ConnectionManager;
import br.uff.sql.SqlManager;
import java.io.IOException;
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
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {
    @Override
    public void init() {
        try {
            ConnectionManager.connect();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void destroy() {
        try {
            ConnectionManager.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {
        // pega sessao
        HttpSession session = request.getSession();
        try {
            User user = null;
            SqlManager sql = new SqlManager(User.class);

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
                    try {
                        // pega variaveis
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        String roleId = request.getParameter("roleId");
                        String name = request.getParameter("name");
                        HashMap<String, Object> attrs = new HashMap() {{
                            put("email", email);
                            put("password", password);
                            put("role_id", roleId);
                            put("name", name);
                        }};
                        if (selUser.isEmpty()) {
                            user = (User) sql.insert().values(attrs).run();
                        } else {
                            sql.update().set(attrs).where("id = " + selUser).run();
                        }
                        session.setAttribute("msg", "Usuário gravado com sucesso!");
                    } catch (Exception ec) {
                        throw new Exception(ec.getMessage());
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
                    try {
                        // pega variaveis
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        user = (User) sql.findBy("email = '" + email + "' and password = '" + password + "'");
                        if (user != null) {
                            session.setAttribute("userId", user.getId());
                            session.setAttribute("userRole", user.getRoleId());
                            session.setAttribute("msg", "Seja bem vindo " + user.getName() + "!");
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
                    }
                    break;
                }
                case "insere": {
                    try {
                        // pega variaveis
                        String name = request.getParameter("nome");
                        String email = request.getParameter("email");
                        String password = request.getParameter("senha");
                        HashMap<String, Object> attrs = new HashMap() {{
                            put("name", name);
                            put("email", email);
                            put("password", password);
                            put("role_id", User.CUSTOMER_ROLE_ID);
                        }};
                        user = (User) sql.insert().values(attrs).run();
                        session.setAttribute("userId", user.getId());
                        session.setAttribute("userRole", user.getRoleId());
                        session.setAttribute("msg", "Seja bem vindo " + name + "!");
                    } catch (Exception ex) {
                        throw new Exception(ex.getMessage());
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
                        User usuario = new User("", "", "", 2);
                        // define endereco
                        try {
                            usuario = (User) sql.find(Integer.parseInt(selUser));
                        } catch (SQLException ex) {
                            throw new Exception("Erro ao recuperar registros do banco: " + ex.getMessage());
                        }
                        // define atributo de produto
                        request.setAttribute("usuario", usuario);
                        redirect = "usuario-cadastro.jsp";
                    } else {
                        // define grid
                        ArrayList<User> users = new ArrayList();
                        try {
                            ArrayList<BaseModel> result;
                            // se for adm n insere filtro e define bind como null
                            if (session.getAttribute("userRole").equals("1")) {
                                result = sql.all();
                            } else {
                                result = sql.select().where("id=" + userId).run();
                            }
                            result.forEach((u) -> {
                                users.add((User) u);
                            });
                            request.setAttribute("usuarios", users);
                        } catch (SQLException ed) {
                            throw new Exception("Erro ao recuperar registros do banco: " + ed.getMessage());
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
        } catch (Exception ex) {
            session.setAttribute("msg", ex.getMessage());
//            response.sendRedirect("");
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
