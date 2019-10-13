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
            <input name="category" id="playstation" type="checkbox" value="playstation"><label for="playstation">Playstation</label><br>
            <input name="category" id="xbox" type="checkbox" value="xbox"><label for="xbox">Xbox</label><br>
            <input name="category" id="wii" type="checkbox" value="wii"><label for="wii">Wii</label><br>
        </div>
        <!-- Fitro das subcategorias -->
        <div class="products-item-filter">
            <p>Subcategorias</p>
            <input name="category" type="checkbox" value="consoles" id="consoles"><label for="consoles">Consoles</label><br>
            <input name="category" type="checkbox" value="jogos" id="jogos"><label for="jogos">Jogos</label><br>
            <input name="category" type="checkbox" id="acessorios" value="acessórios"><label for="acessorios">Acessórios</label><br>
        </div>
        <!-- Filtro dos preços -->
        <div class="products-item-filter">
            <p>Preços</p>
            <label>Valor Mínimo (R$)</label>
            <div style="width: 100%;display:flex;">
                <input type="range" min="0" max="1000" step="100" oninput="display_min.value=value" onchange="display_min.value = value">
                <input style="border:none;"  name="price_min" type="number" readonly id="display_min"/>
            </div>
            <label>Valor Máximo (R$)</label>
            <div style="width: 100%;display:flex;">
                <input type="range" min="0" max="1000" step="100" oninput="display_max.value=value" onchange="display_max.value = value">
                <input style="border:none;" name="price_max" type="number" readonly id="display_max"/>
            </div>
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