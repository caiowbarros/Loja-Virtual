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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HP
 */
@WebServlet(name = "ProdutosController", urlPatterns = {"/ProdutosController"})
public class ProdutosController extends HttpServlet {

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
            String qtdMaxProdutosPag = "8";
            String consulta = "SELECT p.id,p.name,p.price,p.img,c.category_name,p.category_id FROM products p LEFT JOIN vw_category c on(p.category_id=c.id)";
            String filtro = "";
            ArrayList<String> bindItens = new ArrayList<>();
            String limit = " LIMIT " + qtdMaxProdutosPag + " ";
            String offset = "";
            String userId = null;

            // verifica se chama favoritos
            if (request.getParameter("fav") != null) {
                // se tem usuario logado mostra filtra produtos por favoritos
                if (session.getAttribute("userId") != null) {
                    filtro += (filtro.equals("") ? " WHERE " : " AND ") + "p.id in (SELECT f.product_id FROM favorite_products f WHERE f.user_id=1)";
                    userId = session.getAttribute("userId").toString();
                    bindItens.add(userId);
                } else {
                    // se n tem usuario logado mostra msg solicitando login
                    throw new Exception("Realize login para ver seus favoritos!");
                }
            } else {
                filtro += (filtro.equals("") ? " WHERE " : " AND ") + "p.quantity>0";
            }

            // pega categorias
            String[] categorias = request.getParameterValues("category");

            // pega sub categorias
            String[] subCategorias = request.getParameterValues("subCategory");

            // Converte String Array de categorias p Lista
            List<String> listaCategorias = new ArrayList<String>();;
            if (categorias != null) {
                listaCategorias = Arrays.asList(categorias);
            }

            // Converte String Array de sub categorias p Lista
            List<String> listaSubCategorias = new ArrayList<String>();;
            if (subCategorias != null) {
                listaSubCategorias = Arrays.asList(subCategorias);
            }

            // carrega categorias com base nos selects checados
            session.setAttribute("playstation", (listaCategorias.contains("playstation") ? "checked" : ""));
            session.setAttribute("xbox", (listaCategorias.contains("xbox") ? "checked" : ""));
            session.setAttribute("wii", (listaCategorias.contains("wii") ? "checked" : ""));
            session.setAttribute("consoles", (listaSubCategorias.contains("consoles") ? "checked" : ""));
            session.setAttribute("jogos", (listaSubCategorias.contains("jogos") ? "checked" : ""));
            session.setAttribute("acessorios", (listaSubCategorias.contains("acessorios") ? "checked" : ""));

            // verifica se chamou o carregamento de categorias
            String categoryIdParameter = "";
            if (request.getParameter("categoryId") != null) {
                categoryIdParameter = request.getParameter("categoryId");
            }

            // define na sessao as variaveis para os checkboxes com base no category id passado
            if (categoryIdParameter.equals("5")) {
                session.setAttribute("playstation", "checked");
                session.setAttribute("consoles", "checked");
            } else if (categoryIdParameter.equals("6")) {
                session.setAttribute("playstation", "checked");
                session.setAttribute("jogos", "checked");
            } else if (categoryIdParameter.equals("4")) {
                session.setAttribute("playstation", "checked");
                session.setAttribute("acessorios", "checked");
            } else if (categoryIdParameter.equals("8")) {
                session.setAttribute("xbox", "checked");
                session.setAttribute("consoles", "checked");
            } else if (categoryIdParameter.equals("9")) {
                session.setAttribute("xbox", "checked");
                session.setAttribute("jogos", "checked");
            } else if (categoryIdParameter.equals("7")) {
                session.setAttribute("xbox", "checked");
                session.setAttribute("acessorios", "checked");
            } else if (categoryIdParameter.equals("11")) {
                session.setAttribute("wii", "checked");
                session.setAttribute("consoles", "checked");
            } else if (categoryIdParameter.equals("12")) {
                session.setAttribute("wii", "checked");
                session.setAttribute("jogos", "checked");
            } else if (categoryIdParameter.equals("10")) {
                session.setAttribute("wii", "checked");
                session.setAttribute("acessorios", "checked");
            }

            // pega sub categoria console q n esta na lista de sub categorias e insere
            if (session.getAttribute("consoles") != null) {
                if (session.getAttribute("consoles").toString().equals("checked")) {
                    if (!listaSubCategorias.contains("consoles")) {
                        listaSubCategorias.add("consoles");
                    }
                }
            }
            // pega  sub categoria jogo q n esta na lista de sub categorias e insere
            if (session.getAttribute("jogos") != null) {
                if (session.getAttribute("jogos").toString().equals("checked")) {
                    if (!listaSubCategorias.contains("jogos")) {
                        listaSubCategorias.add("jogos");
                    }
                }
            }
            // pega sub categoria acessorio q n esta na lista de sub categorias e insere
            if (session.getAttribute("acessorios") != null) {
                if (session.getAttribute("acessorios").toString().equals("checked")) {
                    if (!listaSubCategorias.contains("acessorios")) {
                        listaSubCategorias.add("acessorios");
                    }
                }
            }
            // pega categoria playstation q n esta na lista de categorias e insere
            if (session.getAttribute("playstation") != null) {
                if (session.getAttribute("playstation").toString().equals("checked")) {
                    if (!listaCategorias.contains("playstation")) {
                        listaCategorias.add("playstation");
                    }
                }
            }
            // pega categoria xbox q n esta na lista de categorias e insere
            if (session.getAttribute("xbox") != null) {
                if (session.getAttribute("xbox").toString().equals("checked")) {
                    if (!listaCategorias.contains("xbox")) {
                        listaCategorias.add("xbox");
                    }
                }
            }
            // pega categoria wii q n esta na lista de categorias e insere
            if (session.getAttribute("wii") != null) {
                if (session.getAttribute("wii").toString().equals("checked")) {
                    if (!listaCategorias.contains("wii")) {
                        listaCategorias.add("wii");
                    }
                }
            }

            // filtra categorias
            if (listaCategorias.size() > 0) {
                String categoriaFiltro = "";
                for (String value : listaCategorias) {
                    bindItens.add("%" + value.toUpperCase() + "%");
                    categoriaFiltro += (categoriaFiltro.equals("") ? " ( " : " OR ") + " UPPER(c.category_name) like ? ";
                }
                categoriaFiltro += (categoriaFiltro.equals("") ? "" : " ) ");
                filtro += (filtro.equals("") ? " WHERE " : " AND ") + categoriaFiltro;
            }

            // filtra sub categorias
            if (listaSubCategorias.size() > 0) {
                String subCategoriaFiltro = "";
                for (String value : listaSubCategorias) {
                    bindItens.add("%" + value.toUpperCase() + "%");
                    subCategoriaFiltro += (subCategoriaFiltro.equals("") ? " ( " : " OR ") + " UPPER(c.category_name) like ? ";
                }
                subCategoriaFiltro += (subCategoriaFiltro.equals("") ? "" : " ) ");
                filtro += (filtro.equals("") ? " WHERE " : " AND ") + subCategoriaFiltro;
            }

            // filtra menor preco
            if (request.getParameter("price_min") != null) {
                String priceMin = request.getParameter("price_min");
                if (!priceMin.equals("")) {
                    session.setAttribute("priceMin", priceMin);
                    bindItens.add(priceMin);
                    filtro += (filtro.equals("") ? " WHERE " : " AND ") + " price >= ? ";
                }
            }

            // filtra maior preco
            if (request.getParameter("price_max") != null) {
                String priceMax = request.getParameter("price_max");
                if (!priceMax.equals("")) {
                    session.setAttribute("priceMax", priceMax);
                    bindItens.add(priceMax);
                    filtro += (filtro.equals("") ? " WHERE " : " AND ") + " price <= ? ";
                }
            }

            // monta bind
            String[] bind = null;
            if (bindItens.size() > 0) {
                bind = new String[bindItens.size()];
                for (int i = 0; i < bindItens.size(); i++) {
                    bind[i] = bindItens.get(i);
                }
            }

            // joga numero max de pags p sessao
            MySql dbMaxPag = null;
            String maxP = null;
            try {
                dbMaxPag = new MySql();
                maxP = dbMaxPag.dbValor("ceil(count(*)/" + qtdMaxProdutosPag + ")", consulta + filtro, "", bind);
                if (Integer.valueOf(maxP) < 1) {
                    maxP = "1";
                }
                session.setAttribute("maxPag", maxP);
            } catch (Exception ed) {
                throw new Exception(ed.getMessage());
            } finally {
                dbMaxPag.destroyDb();
            }

            // define pag atual
            Integer ProdutosPag = 1;
            if (session.getAttribute("ProdutosPag") != null) {
                ProdutosPag = (Integer) session.getAttribute("ProdutosPag");
            }
            // se produtos pag for maior q o num max de pag, define max como pag atual 
            if (ProdutosPag > Integer.valueOf(maxP)) {
                ProdutosPag = Integer.valueOf(maxP);
            }
            // joga produtos pag p sessao
            session.setAttribute("ProdutosPag", ProdutosPag);

            // recupera acao solicitada se existir
            String action = "";

            if (request.getParameter("action") != null) {
                action = request.getParameter("action");
            }

            switch (action) {
                case "ant": {
                    if (ProdutosPag > 1) {
                        ProdutosPag = ProdutosPag - 1;
                        session.setAttribute("ProdutosPag", ProdutosPag);
                    }
                    break;
                }
                case "prox": {
                    int maxPag = 1;
                    if (session.getAttribute("maxPag") != null) {
                        maxPag = Integer.valueOf(maxP);
                    }
                    if (maxPag > ProdutosPag) {
                        ProdutosPag = ProdutosPag + 1;
                        session.setAttribute("ProdutosPag", ProdutosPag);
                    }
                    break;
                }
            }

            // define offset
            if (session.getAttribute("ProdutosPag") != null) {
                int calcOffset = 0;
                calcOffset = (int) Integer.valueOf(session.getAttribute("ProdutosPag").toString());
                if (calcOffset > 1) {
                    calcOffset = calcOffset - 1;
                    calcOffset = calcOffset * Integer.valueOf(qtdMaxProdutosPag);
                    offset = " OFFSET " + calcOffset + " ";
                }
            }

            // define grid
            MySql dbProdutos = null;
            try {
                dbProdutos = new MySql();
                ArrayList<ArrayList> produtos = new ArrayList<>();
                ResultSet ret = dbProdutos.dbCarrega(consulta + filtro + limit + offset, bind);
                while (ret.next()) {
                    ArrayList<String> row = new ArrayList<>();
                    // preenche row
                    row.add(ret.getString("id"));
                    row.add(ret.getString("name"));
                    row.add(ret.getString("price"));
                    row.add(ret.getString("img"));
                    row.add(ret.getString("category_name"));
                    // add row no grid
                    produtos.add(row);
                }
                request.setAttribute("produtos", produtos);
            } catch (SQLException ex) {
                throw new Exception("Erro ao recuperar registros do banco: " + ex.getMessage());
            } finally {
                dbProdutos.destroyDb();
            }

            request.getRequestDispatcher("produtos.jsp").forward(request, response);
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
