/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.controllers;

import br.uff.dao.MySql;
import java.io.IOException;
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
@WebServlet(name = "ProdutoController", urlPatterns = {"/ProdutoController"})
public class ProdutoController extends HttpServlet {

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

            // se tem produto selecionado por parametro passa p sessao
            String produtoId = null;
            if (request.getParameter("produtoId") != null) {
                produtoId = request.getParameter("produtoId");
                session.setAttribute("produtoId", produtoId);
            }

            // se produtoId da sessao for null manda p pag d produtos
            if (session.getAttribute("produtoId") != null) {
                produtoId = session.getAttribute("produtoId").toString();
            } else {
                session.setAttribute("msg", "Por favor, selecione um produto.");
                response.sendRedirect("ProdutosController");
                return;
            }

            // se fav esta definido por parametro passa p sessao
            String fav = null;
            if (request.getParameter("fav") != null) {
                fav = request.getParameter("fav");
                session.setAttribute("fav", fav);
            }
            // recupera fav da sessao
            if (session.getAttribute("fav") != null) {
                fav = session.getAttribute("fav").toString();
            }

            // recupera user id
            String userId = null;
            if (session.getAttribute("userId") != null) {
                userId = session.getAttribute("userId").toString();
            }

            // checa se eh para favoritar ou desfavoritar produto
            if (fav != null) {
                // VERIFICA SE ESTA LOGADO
                if (userId == null) {
                    response.sendRedirect("UserController?redirect=ProdutoController");
                    return;
                } else {
                    MySql db = null;
                    try {
                        db = new MySql();
                        String[] bindFav = {produtoId, userId};
                        String count = "0";
                        count = db.dbValor("count(*)", "favorite_products", "product_id=? AND user_id=?", bindFav);
                        // se ja tiver registro, remove, se nao tiver insere
                        if (!count.equals("0")) {
                            db.dbGrava("DELETE FROM favorite_products WHERE product_id=? AND user_id=?", bindFav);
                            session.setAttribute("msg", "Produto desfavoritado com sucesso!");
                        } else {
                            db.dbGrava("INSERT INTO favorite_products (product_id,user_id) VALUES (?,?)", bindFav);
                            session.setAttribute("msg", "Produto favoritado com sucesso!");
                        }
                    } catch (Exception ex) {
                        throw new Exception(ex.getMessage());
                    } finally {
                        db.destroyDb();
                        // define fav como null
                        session.setAttribute("fav", null);
                    }
                }
            }

            String filtroUser = (userId == null ? "null" : userId);
            String[] bind = {filtroUser, filtroUser, produtoId};
            String consulta = "SELECT\n"
                    + "	p.id,\n"
                    + "	p.`name`,\n"
                    + "	p.quantity,\n"
                    + "	p.description,\n"
                    + "	p.price,\n"
                    + "	p.img,\n"
                    + "	c.category_name,\n"
                    + "	(SELECT count(*) FROM favorite_products f WHERE f.product_id = p.id AND f.user_id=?) user_fav_product,\n"
                    + "	(SELECT f.rating FROM user_produts_rating f WHERE f.product_id = p.id AND f.user_id=?) rate_gave_by_user,\n"
                    + "	(SELECT count(*) FROM user_produts_rating f WHERE f.product_id = p.id) qtd_ratings,\n"
                    + "	(SELECT COALESCE(sum(f.rating),0) FROM user_produts_rating f WHERE f.product_id = p.id) sum_rating,\n"
                    + "	(SELECT count(*) FROM user_produts_rating f WHERE f.product_id = p.id AND f.rating='1') qtd_ratings_1,\n"
                    + "	(SELECT count(*) FROM user_produts_rating f WHERE f.product_id = p.id AND f.rating='2') qtd_ratings_2,\n"
                    + "	(SELECT count(*) FROM user_produts_rating f WHERE f.product_id = p.id AND f.rating='3') qtd_ratings_3,\n"
                    + "	(SELECT count(*) FROM user_produts_rating f WHERE f.product_id = p.id AND f.rating='4') qtd_ratings_4,\n"
                    + "	(SELECT count(*) FROM user_produts_rating f WHERE f.product_id = p.id AND f.rating='5') qtd_ratings_5\n"
                    + "FROM\n"
                    + "	products p\n"
                    + "LEFT JOIN\n"
                    + "	vw_category c\n"
                    + "ON\n"
                    + "	(p.category_id = c.id)\n"
                    + "WHERE\n"
                    + "	p.id=?";

            // define grid
            MySql dbProduto = null;
            try {
                dbProduto = new MySql();
                ArrayList<String> produto = new ArrayList<>();
                ResultSet ret = dbProduto.dbCarrega(consulta, bind);
                if (ret.next()) {
                    // preenche row
                    produto.add(ret.getString("id"));
                    produto.add(ret.getString("name"));
                    produto.add(ret.getString("quantity"));
                    produto.add(ret.getString("description"));
                    produto.add(ret.getString("price"));
                    produto.add(ret.getString("img"));
                    produto.add(ret.getString("category_name"));
                    produto.add(ret.getString("user_fav_product"));
                    produto.add(ret.getString("rate_gave_by_user"));
                    produto.add(ret.getString("qtd_ratings"));
                    produto.add(ret.getString("sum_rating"));
                    produto.add(ret.getString("qtd_ratings_1"));
                    produto.add(ret.getString("qtd_ratings_2"));
                    produto.add(ret.getString("qtd_ratings_3"));
                    produto.add(ret.getString("qtd_ratings_4"));
                    produto.add(ret.getString("qtd_ratings_5"));
                }
                request.setAttribute("produto", produto);
            } catch (SQLException ex) {
                throw new Exception("Erro ao recuperar registros do banco: " + ex.getMessage());
            } finally {
                dbProduto.destroyDb();
            }

            request.getRequestDispatcher("produto.jsp").forward(request, response);
            return;
        } catch (Exception ex) {
            session.setAttribute("msg", ex.getMessage());
            request.getRequestDispatcher("ProdutosController").forward(request, response);
            return;
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
