
package br.uff.loja.application.api;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.uff.loja.core.dtos.UsuarioDTO;
import br.uff.loja.core.interfaces.services.IUsuarioService;
import br.uff.loja.core.services.UsuarioService;

@WebServlet("/api/usuario/*")
public class UsuarioApiServlet extends HttpServlet {
	private final IUsuarioService usuarioService;

	public UsuarioApiServlet() {
		usuarioService = new UsuarioService();
	}

	/**
	 * Processes requests methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			String pathInfo = request.getPathInfo();
			String[] parameters = pathInfo.split("/");

			if (parameters.length > 0) {
				int id = Integer.parseInt(parameters[1]);

				UsuarioDTO usuarioDTO = usuarioService.encontraUsuarioPorId(id);
				String json = new Gson().toJson(usuarioDTO);
				out.println(json);
			} else {
				List<UsuarioDTO> usuarioDTOs = usuarioService.listaUsuarios();
				String json = new Gson().toJson(usuarioDTOs);
				out.println(json);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.sendError(500);
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
	// + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception ex) {
			Logger.getLogger(UsuarioApiServlet.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Servlet da API pra mostrar usuários.";
	}// </editor-fold>
}