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
<form method="post" action="ProdutosController" id="frmProdutos">
    <!-- Container principal -->
    <div class="products-main">

        <!-- Container dos filtros -->
        <div class="products-filter" style="position:fixed;">
            <h2>Filtros</h2>
            <!-- Filtro das categorias -->
            <div class="products-item-filter">
                <p>Categorias</p>
                <input ${playstation} onchange="document.getElementById('frmProdutos').submit()" name="category" id="playstation" type="checkbox" value="playstation"><label for="playstation">Playstation</label><br>
                <input ${xbox} onchange="document.getElementById('frmProdutos').submit()" name="category" id="xbox" type="checkbox" value="xbox"><label for="xbox">Xbox</label><br>
                <input ${wii} onchange="document.getElementById('frmProdutos').submit()" name="category" id="wii" type="checkbox" value="wii"><label for="wii">Wii</label><br>
            </div>
            <!-- Fitro das subcategorias -->
            <div class="products-item-filter">
                <p>Subcategorias</p>
                <input ${consoles} onchange="document.getElementById('frmProdutos').submit()" name="subCategory" type="checkbox" value="consoles" id="consoles"><label for="consoles">Consoles</label><br>
                <input ${jogos} onchange="document.getElementById('frmProdutos').submit()" name="subCategory" type="checkbox" value="jogos" id="jogos"><label for="jogos">Jogos</label><br>
                <input ${acessorios} onchange="document.getElementById('frmProdutos').submit()" name="subCategory" type="checkbox" id="acessorios" value="acessorios"><label for="acessorios">Acessórios</label><br>
            </div>
            <!-- Filtro dos preços -->
            <div class="products-item-filter">
                <p>Preços</p>
                <label>Valor Mínimo (R$)</label>
                <div style="width: 100%;display:flex;">
                    <input value="${priceMin}" onchange="verifica_precos()" type="range" min="0" max="5000" step="500" oninput="display_min.value=value" onchange="display_min.value = value">
                    <input value="${priceMin}" style="border:none;background-color: transparent;" name="price_min" type="number" readonly id="display_min"/>
                </div>
                <label>Valor Máximo (R$)</label>
                <div style="width: 100%;display:flex;">
                    <input value="${priceMax}" onchange="verifica_precos()" type="range" min="0" max="5000" step="500" oninput="display_max.value=value" onchange="display_max.value = value">
                    <input value="${priceMax}" style="border:none;background-color: transparent;" name="price_max" type="number" readonly id="display_max"/>
                </div>
            </div>
        </div>

        <!-- Container dos produtos -->
        <div class="products-page" style="margin-left:20%;margin-top:50px;">
            <% if (produtos.size() < 1) { %>
            <h3>O filtro escolhido não retornou nenhum produto, tente com outro!</h3>
            <%
            } else {
            %>
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
                <% if (Integer.valueOf(session.getAttribute("ProdutosPag").toString()) > 1) {%>
                <button class="products-prev" name="action" value="ant">&#8249;</button>
                <% }%>
                <span><%= session.getAttribute("ProdutosPag")%></span>
                <% if (Integer.valueOf(session.getAttribute("ProdutosPag").toString()) < Integer.valueOf(session.getAttribute("maxPag").toString())) { %>
                <button class="products-next" name="action" value="prox">&#8250;</button>
                <% }%>
            </div>
            <%
                }
            %>
        </div>
    </div>
</form>
<script>
    function verifica_precos() {
        var price_min = document.getElementById('display_min').value;
        var price_max = document.getElementById('display_max').value;
        if (parseInt(price_min) >= parseInt(price_max)) {
            alert("Valor mínimo(R$" + price_min + ") não pode ser igual ou maior que o valor máximo(R$" + price_max + ")!");
            return false;
        } else {
            document.getElementById('frmProdutos').submit();
            return true;
        }
    }
</script>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>