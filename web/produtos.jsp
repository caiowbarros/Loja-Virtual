<%-- 
    Document   : produtos
    Created on : 02/10/2019, 00:11:02
    Author     : HP
--%>
<%@page import="java.util.ArrayList"%>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Produtos"/>
</jsp:include>
<%
    // mostra se tiver msg
    if (session.getAttribute("msg") != null) {
        String msg = session.getAttribute("msg").toString();
        session.setAttribute("msg", null);
        out.println("<script>alert('" + msg + "');</script>");
    }

    ArrayList<ArrayList<String>> produtos = null;
    if (request.getAttribute("produtos") != null) {
        produtos = (ArrayList<ArrayList<String>>) request.getAttribute("produtos");
    }
%>

<!-- Container principal -->
<div class="products-main">

    <!-- Container dos filtros -->
    <div class="products-filter">
        <!-- Filtro das categorias -->
        <div class="products-item-filter">
            <p>Categorias</p>
            <input name="category" type="checkbox" value="playstation">Playstation<br>
            <input name="category" type="checkbox" value="xbox">Xbox<br>
            <input name="category" type="checkbox" value="wii">Wii<br>
        </div>
        <!-- Fitro das subcategorias -->
        <div class="products-item-filter">
            <p>Subcategorias</p>
            <input name="category" type="checkbox" value="consoles">Consoles<br>
            <input name="category" type="checkbox" value="jogos">Jogos<br>
            <input name="category" type="checkbox" value="acessórios">Acessórios<br>
        </div>
        <!-- Filtro dos preços -->
        <div class="products-item-filter">
            <p>Preços</p>
            <input type="checkbox">Até R$ 250<br>
            <input type="checkbox">De R$ 250 à R$ 500<br>
            <input type="checkbox">De R$ 500 à R$ 1000<br>
            <input type="checkbox">A partir de R$ 1000<br>
            <!--<input type="range" min="1" max="1000" step="100">Min<br>
            <input type="range" min="1" max="1000" step="100">Max<br>-->
        </div>
    </div>

    <!-- Container dos produtos -->
    <div class="products-page">
        <div class="products-container">


            <%
                for (int i = 0; i < produtos.size(); i++) {
            %>
            <div class="products-item">
                <a href="ProdutoController?produtoId=<%= produtos.get(i).get(0)%>">
                    <div class="products-cart">Ver Detalhes</div>
                    <img src="<%= produtos.get(i).get(3)%>">
                    <div class="products-title"><%= produtos.get(i).get(1)%></div>
                    <div class="products-details"><%= produtos.get(i).get(4)%></div>
                    <div class="products-price">R$<%= produtos.get(i).get(2)%></div>
                </a>
            </div>
            <%
                }
            %>

        </div>

        <!-- Páginas -->
        <div class="products-paging">
            <form method="post" action="ProdutosController">
                <% if (!session.getAttribute("ProdutosPag").toString().equals("1")) {%>
                <button class="products-prev" name="action" value="ant">&#8249;</button>
                <% }%>
                <span><%= session.getAttribute("ProdutosPag")%></span>
                <% if (Integer.valueOf(session.getAttribute("ProdutosPag").toString()) < Integer.valueOf(session.getAttribute("maxPag").toString())) { %>
                <button class="products-next" name="action" value="prox">&#8250;</button>
                <% }%>
            </form>
        </div>
    </div>
</div>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>