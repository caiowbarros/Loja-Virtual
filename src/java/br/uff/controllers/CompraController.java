/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.controllers;

import br.uff.dao.MySql;
import br.uff.models.Address;
import br.uff.models.Product;
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
@WebServlet(name = "CompraController", urlPatterns = {"/CompraController"})
public class CompraController extends HttpServlet {

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
            // se n tem usuario logado manda p controller de user
            String userId = null;
            if (session.getAttribute("userId") == null) {
                response.sendRedirect("UserController?redirect=CarrinhoController");
                return;
            } else {
                userId = session.getAttribute("userId").toString();
            }

            // se solicitar historico manda p pag d compras realizadas
            if (request.getParameter("historico") != null) {
                MySql db = null;
                try {
                    db = new MySql();
                    ArrayList<ArrayList> vendas = new ArrayList<>();
                    String[] bindVendas = {userId};
                    ResultSet vendasRet = db.dbCarrega("SELECT * FROM sales WHERE user_id=?", bindVendas);
                    while (vendasRet.next()) {
                        ArrayList venda = new ArrayList<>();
                        venda.add(vendasRet.getString("id"));
                        //pega produtos
                        String[] bindProdutos = {vendasRet.getString("cart_id")};
                        ArrayList<ArrayList> produtos = new ArrayList<>();
                        ResultSet produtosRet = db.dbCarrega("SELECT p.id, p.`name`, p.price, c.quantity, p.price * c.quantity total_price_product FROM carts_products c LEFT JOIN products p ON (c.product_id = p.id) WHERE c.cart_id = ?", bindProdutos);
                        while (produtosRet.next()) {
                            ArrayList produto = new ArrayList<>();
                            produto.add(produtosRet.getString("id"));
                            produto.add(produtosRet.getString("name"));
                            produto.add(produtosRet.getString("price"));
                            produto.add(produtosRet.getString("quantity"));
                            produto.add(produtosRet.getString("total_price_product"));
                            produtos.add(produto);
                        }
                        venda.add(produtos);
                        // pega endereco
                        String[] bindEndereco = {vendasRet.getString("address_id")};
                        ArrayList endereco = new ArrayList<>();
                        ResultSet enderecoRet = db.dbCarrega("SELECT * FROM address WHERE id=?", bindEndereco);
                        if (enderecoRet.next()) {
                            endereco.add(enderecoRet.getString("zipcode"));
                            endereco.add(enderecoRet.getString("address"));
                            endereco.add(enderecoRet.getString("city"));
                            endereco.add(enderecoRet.getString("state"));
                            endereco.add(enderecoRet.getString("country"));
                        }
                        venda.add(endereco);
                        venda.add(vendasRet.getString("total_price"));
                        venda.add(vendasRet.getString("created_at"));
                        vendas.add(venda);
                    }
                    request.setAttribute("vendas", vendas);
                } catch (SQLException ed) {
                    throw new Exception(ed.getMessage());
                } finally {
                    db.destroyDb();
                }
                request.getRequestDispatcher("compras.jsp").forward(request, response);
                return;
            }

            String end = null;
            if (session.getAttribute("enderecoId") != null) {
                end = session.getAttribute("enderecoId").toString();
            }

            String carrinhoId = null;
            if (session.getAttribute("carrinhoId") != null) {
                carrinhoId = session.getAttribute("carrinhoId").toString();
            }

            // recupera acao solicitada se existir
            String action = "";
            if (request.getParameter("action") != null) {
                action = request.getParameter("action");
            }

            switch (action) {
                case "pagamentoOk": {
                    MySql db = null;
                    try {
                        db = new MySql();
                        String[] comandos = {"CALL buy_cart_itens(?, ?)"};
                        String[][] bind = {{carrinhoId, end}};
                        db.dbTransaction(comandos, bind);
                        session.setAttribute("msg", "Compra realizada com sucesso!");
                    } catch (Exception ed) {
                        throw new Exception("Compra não foi realizada devido ao seguinte erro: " + ed.getMessage());
                    } finally {
                        db.destroyDb();
                    }
                    response.sendRedirect("CompraController?historico");
                    return;
                }
                case "pagamentoErr": {
                    throw new Exception("Houve um erro durante sua compra, realize o procedimento de confirmação de carrinho novamente.");
                }
            }

            if (end != null) {
                String consultaEnd = "SELECT\n"
                        + "	u.`name`,\n"
                        + "	a.address,\n"
                        + "	a.zipcode,\n"
                        + "	a.city,\n"
                        + "	a.state,\n"
                        + "	a.country\n"
                        + "FROM\n"
                        + "	address a\n"
                        + "LEFT JOIN users u ON (a.user_id = u.id)\n"
                        + "WHERE\n"
                        + "	a.id = ?";
                String[] bindEnd = {end};
                MySql db = null;
                try {
                    db = new MySql();
                    ArrayList<String> endereco = new ArrayList<>();
                    ResultSet retorno = db.dbCarrega(consultaEnd, bindEnd);
                    if (retorno.next()) {
                        endereco.add(retorno.getString("name"));
                        endereco.add(retorno.getString("address"));
                        endereco.add(retorno.getString("zipcode"));
                        endereco.add(retorno.getString("city"));
                        endereco.add(retorno.getString("state"));
                        endereco.add(retorno.getString("country"));
                    }
                    request.setAttribute("endereco", endereco);
                } catch (SQLException ed) {
                    throw new Exception(ed.getMessage());
                } finally {
                    db.destroyDb();
                }
            } else {
                throw new Exception("Por favor selecione um endereço!");
            }

            // manda atributos para a pagina definida, no caso carrinho.jsp
            request.getRequestDispatcher("compra-pagamento.jsp").forward(request, response);
        } catch (Exception ex) {
            session.setAttribute("msg", ex.getMessage());
            response.sendRedirect("CarrinhoController");
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
