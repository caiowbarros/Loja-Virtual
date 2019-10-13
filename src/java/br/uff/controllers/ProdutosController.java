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
            String[] bind = null;
            String limit = " LIMIT " + qtdMaxProdutosPag + " ";
            String offset = "";
            String userId = null;
            // verifica se chama favoritos
            if (request.getParameter("fav") != null) {
                // se tem usuario logado mostra filtra produtos por favoritos
                if (session.getAttribute("userId") != null) {
                    filtro += (filtro.equals("") ? " WHERE " : " AND ") + "p.id in (SELECT f.product_id FROM favorite_products f WHERE f.user_id=1)";
                    userId = session.getAttribute("userId").toString();
                    bind[bind.length] = userId;
                } else {
                    // se n tem usuario logado mostra msg solicitando login
                    throw new Exception("Realize login para ver seus favoritos!");
                }
            } else {
                filtro += (filtro.equals("") ? " WHERE " : " AND ") + "p.quantity>0";
            }

            // filtra categorias
            String categorias[] = request.getParameterValues("category");
            if (categorias != null) {
                for (String value : categorias) {
                    // bind[bind.length] = "%" + value.toUpperCase() + "%";
                    // filtro += (filtro.equals("") ? " WHERE " : " AND ") + " UPPER(c.category_name) like ? ";
                }
            }

            // joga numero max de pags p sessao
            MySql dbMaxPag = null;
            try {
                dbMaxPag = new MySql();
                String maxP = null;
                maxP = dbMaxPag.dbValor("ceil(count(*)/" + qtdMaxProdutosPag + ")", consulta + filtro, "", bind);
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
            } else {
                session.setAttribute("ProdutosPag", ProdutosPag);
            }

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
                        maxPag = Integer.valueOf(session.getAttribute("maxPag").toString());
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
