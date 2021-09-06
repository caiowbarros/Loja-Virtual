
package br.uff.loja.application.api;

import java.io.*;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.uff.loja.core.dtos.ProdutoDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.services.IProdutoService;
import br.uff.loja.core.services.ProdutoService;

@WebServlet("/api/produto/*")
public class ProdutoApiServlet extends HttpServlet {
  private final IProdutoService produtoService;

  public ProdutoApiServlet() {
    produtoService = new ProdutoService();
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String pathInfo = request.getPathInfo();
    String[] parameters = pathInfo.split("/");
    PrintWriter out = response.getWriter();
    
    if (parameters.length > 0) {
    	int id = Integer.parseInt(parameters[1]);
    	
    	try {
    		ProdutoDTO data = produtoService.encontraProdutoPorId(id);
    		String json = new Gson().toJson(data);
    		out.println(json);
    	} catch (LojaException e) {
    		e.printStackTrace();
    	}
    } else {
    	try {
    		List<ProdutoDTO> data = produtoService.listaProdutosAdm();
    		String json = new Gson().toJson(data);
    		out.println(json);
    	} catch (LojaException e) {
    		e.printStackTrace();
    	}    	
    }
  }
}