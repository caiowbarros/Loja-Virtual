
package br.uff.loja.application.api;

import java.io.*;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.uff.loja.core.dtos.CategoriaDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.services.ICategoriaService;
import br.uff.loja.core.services.CategoriaService;

@WebServlet("/api/categoria/*")
public class CategoriaApiServlet extends HttpServlet {
  private final ICategoriaService categoriaService;

  public CategoriaApiServlet() {
    categoriaService = new CategoriaService();
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    
	try {
		List<CategoriaDTO> data = categoriaService.listaCategorias();
		String json = new Gson().toJson(data);
		out.println(json);
	} catch (LojaException e) {
		e.printStackTrace();
	}    	
  }
}