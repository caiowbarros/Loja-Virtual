
package br.uff.loja.application.api;

import java.io.*;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.uff.loja.core.dtos.UsuarioDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.services.IUsuarioService;
import br.uff.loja.core.services.UsuarioService;

@WebServlet("/api/usuario/*")
public class UsuarioApiServlet extends HttpServlet {
  private final IUsuarioService usuarioService;

  public UsuarioApiServlet() {
    usuarioService = new UsuarioService();
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String pathInfo = request.getPathInfo();
    String[] parameters = pathInfo.split("/");
    PrintWriter out = response.getWriter();
    
    if (parameters.length > 0) {
    	int id = Integer.parseInt(parameters[1]);
    	
    	try {
    		UsuarioDTO usuarioDTO = usuarioService.encontraUsuarioPorId(id);
    		String json = new Gson().toJson(usuarioDTO);
    		out.println(json);
    	} catch (LojaException e) {
    		e.printStackTrace();
    	}
    } else {
    	try {
    		List<UsuarioDTO> usuarioDTOs = usuarioService.listaUsuarios();
    		String json = new Gson().toJson(usuarioDTOs);
    		out.println(json);
    	} catch (LojaException e) {
    		e.printStackTrace();
    	}    	
    }
  }
}