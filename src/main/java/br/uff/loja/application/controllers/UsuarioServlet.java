package br.uff.loja.application.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.uff.loja.core.dtos.UsuarioDTO;
import br.uff.loja.core.enums.EPermissaoUsuario;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.services.IRoleService;
import br.uff.loja.core.interfaces.services.IUsuarioService;
import br.uff.loja.core.services.RoleService;
import br.uff.loja.core.services.UsuarioService;
import br.uff.loja.infrastructure.shared.Helper;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {
    private static final String SELUSER = "selUser";
    private static final String EMAIL = "email";
    private static final String SENHA = "password";
    private static final String USERID = "userId";
    private static final String USERROLE = "userRole";
    private static final String REDIRECT = "redirect";
    private final IUsuarioService usuarioService;
    private final IRoleService roleService;
    private final Helper helper;

    public UsuarioServlet() {
        usuarioService = new UsuarioService();
        roleService = new RoleService();
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, LojaException {
        // pega sessao
        HttpSession session = request.getSession();
        try {
            if (request.getParameter("sel") != null) {
                session.setAttribute(SELUSER, request.getParameter("sel"));
            }

            // recupera acao solicitada se existir
            String action = "";
            if(request.getParameter("action")!=null){
                action = request.getParameter("action");
            }

            Integer usuarioIdSelectionado = null;
            if (session.getAttribute(SELUSER) != null) {
                usuarioIdSelectionado = helper.tryParseInteger(session.getAttribute(SELUSER).toString());
            }

            String email = request.getParameter(EMAIL);
            String senha = request.getParameter(SENHA);
            
            switch (action) {
                case "grava": {
                    // grava alteracoes no cadastro d usuario feito na pag de usuario-cadastro
                    usuarioService.gravaUsuario(new UsuarioDTO(
                        usuarioIdSelectionado,
                        request.getParameter("name"),
                        email,
                        senha,
                        helper.tryParseInteger(request.getParameter("roleId"))
                    ));
                    break;
                }
                case "logout": {
                    // define variaveis de sessao d usuario como null
                    session.setAttribute(USERID, null);
                    session.setAttribute(USERROLE, null);
                    session.setAttribute("msg", "Usuário deslogado com sucesso!");
                    // invalida sessao
                    session.invalidate();
                    break;
                }
                case "login": {
                    try {
                        UsuarioDTO usuarioDto = usuarioService.login(email, senha);
                        session.setAttribute(USERID, usuarioDto.getId());
                            session.setAttribute(USERROLE, usuarioDto.getPermissaoId());
                            session.setAttribute("msg", "Seja bem vindo " + usuarioDto.getNome() + "!");
                            // seta cookie se solicitar para lembrar login
                            if (request.getParameter("remember") != null) {
                                int durMes = 2592000;
                                Cookie ckEmail = new Cookie("loginEmail", email);
                                ckEmail.setMaxAge(durMes);
                                response.addCookie(ckEmail);
                                Cookie ckPassword = new Cookie("loginPassword", senha);
                                ckPassword.setMaxAge(durMes);
                                response.addCookie(ckPassword);
                            }
                    } catch (Exception el) {
                        session.setAttribute("msg", el.getMessage());
                    }

                    break;
                }
                case "insere": {
                    UsuarioDTO usuarioDto = usuarioService.gravaUsuario(new UsuarioDTO(
                        usuarioIdSelectionado,
                        request.getParameter("nome"),
                        email,
                        request.getParameter("senha"),
                        EPermissaoUsuario.CLIENTE.getId()
                    ));
                    session.setAttribute(USERID, usuarioDto.getId());
                    session.setAttribute(USERROLE, usuarioDto.getPermissaoId());
                    session.setAttribute("msg", "Seja bem vindo " + usuarioDto.getNome() + "!");
                    break;
                }
                case "unsel": {
                    session.setAttribute(SELUSER, null);
                    response.sendRedirect("usuario");
                    return;
                }
                default: {
                    break;
                }
            }

            String redirect = null;
            // recupera redirect
            if (request.getAttribute(REDIRECT) != null) {
                redirect = (String) request.getAttribute(REDIRECT);
            } else if (request.getParameter(REDIRECT) != null) {
                redirect = request.getParameter(REDIRECT);
            }

            // se tem usuario logado manda p conta caso contrario p login
            if (session.getAttribute(USERID) != null) {
                //recupera userId da sessao
                Integer userId = helper.tryParseInteger(session.getAttribute(USERID).toString());
                // define redirect se n foi passado
                if (redirect == null || "null".equals(redirect)) {
                    if (usuarioIdSelectionado != null) {
                        if (!usuarioIdSelectionado.equals(userId) && !helper.tryParseInteger(session.getAttribute(USERROLE).toString()).equals(EPermissaoUsuario.ADM.getId())) {
                            session.setAttribute(SELUSER, null);
                            throw new LojaException("Acesso não permitido!");
                        }

                        request.setAttribute("permissoes", roleService.listaRoles());
                        request.setAttribute("usuario", usuarioService.encontraUsuarioPorId(usuarioIdSelectionado));
                        redirect = "pages/usuario-cadastro.jsp";
                    } else {
                        // define grid
                        List<UsuarioDTO> usuarios = new ArrayList<>();

                        // se for adm traz td mundo
                        if (session.getAttribute(USERROLE).equals(EPermissaoUsuario.ADM.getId())) {
                            usuarios = usuarioService.listaUsuarios();
                        } else {
                            usuarios.add(usuarioService.encontraUsuarioPorId(userId));
                        }

                        request.setAttribute("usuarios", usuarios);
                        redirect = "pages/usuario-grid.jsp";
                    }
                } else {
                    response.sendRedirect(redirect);
                    return;
                }
            } else {
                // define redirect q sera passado p pag d login
                request.setAttribute(REDIRECT, redirect);
                redirect = "pages/login.jsp";
            }

            request.getRequestDispatcher(redirect).forward(request, response);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet pra tratar edição de usuário.";
    }// </editor-fold>

}
