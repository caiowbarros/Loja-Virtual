package br.uff.loja.application.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.uff.loja.core.dtos.FiltraProdutoDTO;
import br.uff.loja.core.dtos.PaginateDTO;
import br.uff.loja.core.dtos.ProdutoListaDTO;
import br.uff.loja.core.interfaces.services.IProdutoService;
import br.uff.loja.core.services.ProdutoService;
import br.uff.loja.infrastructure.shared.Helper;

@WebServlet("/produtos")
public class ProdutosServlet extends HttpServlet {
    private final IProdutoService produtoService;
    private static final String PESQUISASTR = "pesquisa";
    private static final String PLAYSTATIONSTR = "playstation";
    private static final String CHECKEDSTR = "checked";
    private static final String CONSOLESSTR = "consoles";
    private static final String LANCAMENTOSSTR = "lancamentos";
    private static final String FAVORITOSSTR = "favoritos";
    private static final String ACESSORIOSSTR = "acessorios";
    private static final String JOGOSSTR = "jogos";
    private static final String XBOXSTR = "xbox";
    private static final String WIISTR = "wii";
    private static final String PRODUTOSPAGSTR = "ProdutosPag";
    private static final String MAXPAGSTR = "maxPag";

    public ProdutosServlet() {
        produtoService = new ProdutoService();
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // pega sessao
        HttpSession session = request.getSession();

        try {
            FiltraProdutoDTO filtro = new FiltraProdutoDTO();
            filtro.setItensPorPagina(5);
            filtro.setPaginaAtual(1);

            if (request.getParameter(PESQUISASTR) != null) {
                session.setAttribute(PESQUISASTR, request.getParameter(PESQUISASTR));
            }

            if (session.getAttribute(PESQUISASTR) != null) {
                filtro.setPesquisa(session.getAttribute(PESQUISASTR).toString());
            }

            // pega esp
            String[] especiais = request.getParameterValues("esp");

            // pega categorias
            String[] categorias = request.getParameterValues("category");

            // pega sub categorias
            String[] subCategorias = request.getParameterValues("subCategory");

            // Converte String Array de categorias p Lista
            List<String> listaCategorias = new ArrayList<>();
            if (categorias != null) {
                listaCategorias = Arrays.asList(categorias);
                filtro.setCategorias(listaCategorias);
            }

            // Converte String Array de sub categorias p Lista
            List<String> listaSubCategorias = new ArrayList<>();
            if (subCategorias != null) {
                listaSubCategorias = Arrays.asList(subCategorias);
                filtro.setSubCategorias(listaSubCategorias);
            }

            // Converte String Array de especiais p Lista
            List<String> listaEsp = new ArrayList<>();
            if (especiais != null) {
                listaEsp = Arrays.asList(especiais);
            }

            // carrega categorias com base nos selects checados
            session.setAttribute(PLAYSTATIONSTR, (listaCategorias.contains(PLAYSTATIONSTR) ? CHECKEDSTR : ""));
            session.setAttribute(XBOXSTR, (listaCategorias.contains(XBOXSTR) ? CHECKEDSTR : ""));
            session.setAttribute(WIISTR, (listaCategorias.contains(WIISTR) ? CHECKEDSTR : ""));
            session.setAttribute(CONSOLESSTR, (listaSubCategorias.contains(CONSOLESSTR) ? CHECKEDSTR : ""));
            session.setAttribute(JOGOSSTR, (listaSubCategorias.contains(JOGOSSTR) ? CHECKEDSTR : ""));
            session.setAttribute(ACESSORIOSSTR, (listaSubCategorias.contains(ACESSORIOSSTR) ? CHECKEDSTR : ""));
            session.setAttribute(FAVORITOSSTR, (listaEsp.contains(FAVORITOSSTR) ? CHECKEDSTR : ""));
            session.setAttribute(LANCAMENTOSSTR, (listaEsp.contains(LANCAMENTOSSTR) ? CHECKEDSTR : ""));

            // verifica se chamou o carregamento de categorias
            String categoryIdParameter = "";
            if (request.getParameter("categoryId") != null) {
                categoryIdParameter = request.getParameter("categoryId");
            }

            // define na sessao as variaveis para os checkboxes com base no category id passado
            if (categoryIdParameter.equals("5")) {
                session.setAttribute(PLAYSTATIONSTR, CHECKEDSTR);
                session.setAttribute(CONSOLESSTR, CHECKEDSTR);
            } else if (categoryIdParameter.equals("6")) {
                session.setAttribute(PLAYSTATIONSTR, CHECKEDSTR);
                session.setAttribute(JOGOSSTR, CHECKEDSTR);
            } else if (categoryIdParameter.equals("4")) {
                session.setAttribute(PLAYSTATIONSTR, CHECKEDSTR);
                session.setAttribute(ACESSORIOSSTR, CHECKEDSTR);
            } else if (categoryIdParameter.equals("8")) {
                session.setAttribute(XBOXSTR, CHECKEDSTR);
                session.setAttribute(CONSOLESSTR, CHECKEDSTR);
            } else if (categoryIdParameter.equals("9")) {
                session.setAttribute(XBOXSTR, CHECKEDSTR);
                session.setAttribute(JOGOSSTR, CHECKEDSTR);
            } else if (categoryIdParameter.equals("7")) {
                session.setAttribute(XBOXSTR, CHECKEDSTR);
                session.setAttribute(ACESSORIOSSTR, CHECKEDSTR);
            } else if (categoryIdParameter.equals("11")) {
                session.setAttribute(WIISTR, CHECKEDSTR);
                session.setAttribute(CONSOLESSTR, CHECKEDSTR);
            } else if (categoryIdParameter.equals("12")) {
                session.setAttribute(WIISTR, CHECKEDSTR);
                session.setAttribute(JOGOSSTR, CHECKEDSTR);
            } else if (categoryIdParameter.equals("10")) {
                session.setAttribute(WIISTR, CHECKEDSTR);
                session.setAttribute(ACESSORIOSSTR, CHECKEDSTR);
            } else if (categoryIdParameter.equals("1")) {
                session.setAttribute(PLAYSTATIONSTR, CHECKEDSTR);
            } else if (categoryIdParameter.equals("2")) {
                session.setAttribute(XBOXSTR, CHECKEDSTR);
            } else if (categoryIdParameter.equals("3")) {
                session.setAttribute(WIISTR, CHECKEDSTR);
            }

            // verifica se chamou um filtro especial
            String espParameter = "";
            if (request.getParameter("esp") != null) {
                espParameter = request.getParameter("esp");
            }

            if (espParameter.equals(LANCAMENTOSSTR)) {
                session.setAttribute(LANCAMENTOSSTR, CHECKEDSTR);
            } else if (espParameter.equals(FAVORITOSSTR)) {
                session.setAttribute(FAVORITOSSTR, CHECKEDSTR);
            }

            // pega esp lancamentos q n esta na lista de esp e insere
            if (session.getAttribute(LANCAMENTOSSTR) != null) {
                if (session.getAttribute(LANCAMENTOSSTR).toString().equals(CHECKEDSTR) && !listaEsp.contains(LANCAMENTOSSTR)) {
                    listaEsp.add(LANCAMENTOSSTR);
                }
            }

            // pega esp favoritos q n esta na lista de esp e insere
            if (session.getAttribute(FAVORITOSSTR) != null) {
                if (session.getAttribute(FAVORITOSSTR).toString().equals(CHECKEDSTR) && !listaEsp.contains(FAVORITOSSTR)) {
                    listaEsp.add(FAVORITOSSTR);
                }
            }

            // pega sub categoria console q n esta na lista de sub categorias e insere
            if (session.getAttribute(CONSOLESSTR) != null) {
                if (session.getAttribute(CONSOLESSTR).toString().equals(CHECKEDSTR) && !listaSubCategorias.contains(CONSOLESSTR)) {
                    listaSubCategorias.add(CONSOLESSTR);
                }
            }
            // pega  sub categoria jogo q n esta na lista de sub categorias e insere
            if (session.getAttribute(JOGOSSTR) != null) {
                if (session.getAttribute(JOGOSSTR).toString().equals(CHECKEDSTR) && !listaSubCategorias.contains(JOGOSSTR)) {
                    listaSubCategorias.add(JOGOSSTR);
                }
            }
            // pega sub categoria acessorio q n esta na lista de sub categorias e insere
            if (session.getAttribute(ACESSORIOSSTR) != null) {
                if (session.getAttribute(ACESSORIOSSTR).toString().equals(CHECKEDSTR) && !listaSubCategorias.contains(ACESSORIOSSTR)) {
                    listaSubCategorias.add(ACESSORIOSSTR);
                }
            }
            // pega categoria playstation q n esta na lista de categorias e insere
            if (session.getAttribute(PLAYSTATIONSTR) != null) {
                if (session.getAttribute(PLAYSTATIONSTR).toString().equals(CHECKEDSTR) && !listaCategorias.contains(PLAYSTATIONSTR)) {
                    listaCategorias.add(PLAYSTATIONSTR);
                }
            }
            // pega categoria xbox q n esta na lista de categorias e insere
            if (session.getAttribute(XBOXSTR) != null) {
                if (session.getAttribute(XBOXSTR).toString().equals(CHECKEDSTR) && !listaCategorias.contains(XBOXSTR)) {
                    listaCategorias.add(XBOXSTR);
                }
            }
            // pega categoria wii q n esta na lista de categorias e insere
            if (session.getAttribute(WIISTR) != null) {
                if (session.getAttribute(WIISTR).toString().equals(CHECKEDSTR) && !listaCategorias.contains(WIISTR)) {
                    listaCategorias.add(WIISTR);
                }
            }

            // filtra favoritos
            if (listaEsp.contains(FAVORITOSSTR)) {
                // se tem usuario logado mostra filtra produtos por favoritos
                if (session.getAttribute("userId") != null) {
                    filtro.setUsuarioId(new Helper().tryParseInteger(session.getAttribute("userId").toString()));
                    filtro.setApenasFavoritados(true);
                } else {
                    session.setAttribute("msg", "Realize login para ver seus favoritos!");
                    response.sendRedirect("usuario?redirect=produtos");
                    return;
                }
            }

            if (listaEsp.contains(LANCAMENTOSSTR)) {
                filtro.setApenasLancamentos(true);
            }

            // filtra menor preco
            if (request.getParameter("price_min") != null) {
                Double priceMin = Double.valueOf(request.getParameter("price_min"));
                if (priceMin != null) {
                    session.setAttribute("priceMin", priceMin);
                    filtro.setPrecoMinimo(priceMin);
                }
            }

            // filtra maior preco
            if (request.getParameter("price_max") != null) {
                Double priceMax = Double.valueOf(request.getParameter("price_max"));
                if (priceMax != null) {
                    session.setAttribute("priceMax", priceMax);
                    filtro.setPrecoMaximo(priceMax);
                }
            }

            // define pag atual
            Integer produtosPag = 1;
            if (session.getAttribute(PRODUTOSPAGSTR) != null) {
                produtosPag = Integer.valueOf(session.getAttribute(PRODUTOSPAGSTR).toString());
            }

            // recupera acao solicitada se existir
            String action = request.getParameter("action");

            switch (action) {
                case "ant": {
                    produtosPag = produtosPag - 1;
                    break;
                }
                case "prox": {
                    produtosPag = produtosPag + 1;
                    break;
                }
                default: {
                    break;
                }
            }

            filtro.setPaginaAtual(produtosPag);

            PaginateDTO<List<ProdutoListaDTO>> produtosPaginados = produtoService.listaProdutosVitrine(filtro);
            
            session.setAttribute(MAXPAGSTR, produtosPaginados.getUltimaPagina());
            session.setAttribute(PRODUTOSPAGSTR, produtosPaginados.getPaginaAtual());

            request.setAttribute("produtos", produtosPaginados);
            request.getRequestDispatcher("pages/produtos.jsp").forward(request, response);
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
            Logger.getLogger(ProdutosServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ProdutosServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet que lista produtos.";
    }// </editor-fold>

}
