<%-- 
    Document   : produtos
    Created on : 02/10/2019, 00:11:02
    Author     : HP
--%>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Produtos"/>
</jsp:include>
<%
    // mostra se tiver msg
    if (request.getAttribute("msg") != null) {
        out.println("<script>alert('" + request.getAttribute("msg") + "');</script>");
    }
%>

<!-- Container principal -->
<div class="products-main">

    <!-- Container dos filtros -->
    <div class="products-filter">
        <!-- Filtro das categorias -->
        <div class="products-item-filter">
            <p>Categorias</p>
            <input type="checkbox">Playstation<br>
            <input type="checkbox">Xbox<br>
            <input type="checkbox">Wii<br>
        </div>
        <!-- Fitro das subcategorias -->
        <div class="products-item-filter">
            <p>Subcategorias</p>
            <input type="checkbox">Consoles<br>
            <input type="checkbox">Jogos<br>
            <input type="checkbox">Acessórios<br>
        </div>
        <!-- Filtro dos preços -->
        <div class="products-item-filter">
            <p>Preços</p>
            <input type="range" min="1" max="1000" step="100">Min<br>
            <input type="range" min="1" max="1000" step="100">Max<br>
        </div>
    </div>

    <!-- Container dos produtos -->
    <div class="products-page">
        <div class="products-container">
            
            <!-- Produto -->
            <div class="products-item">
                <a href="ProdutoController?produtoId=4">
                    <div class="products-cart">Ver Detalhes</div>
                    <img src="https://images-americanas.b2w.io/produtos/01/00/img/471961/8/471961879_1GG.jpg">
                    <div class="products-title">FIFA 20</div>
                    <div class="products-details">PS4 - Jogos</div>
                    <div class="products-price">R$250,00</div>
                </a>
            </div>
            
            <!-- Produto -->
            <div class="products-item">
                <a href="ProdutoController?produtoId=4">
                    <div class="products-cart">Ver Detalhes</div>
                    <img src="https://images-americanas.b2w.io/produtos/01/00/img/471961/8/471961879_1GG.jpg">
                    <div class="products-title">FIFA 20</div>
                    <div class="products-details">PS4 - Jogos</div>
                    <div class="products-price">R$250,00</div>
                </a>
            </div>
            
            <!-- Produto -->
            <div class="products-item">
                <a href="ProdutoController?produtoId=4">
                    <div class="products-cart">Ver Detalhes</div>
                    <img src="https://images-americanas.b2w.io/produtos/01/00/img/471961/8/471961879_1GG.jpg">
                    <div class="products-title">FIFA 20</div>
                    <div class="products-details">PS4 - Jogos</div>
                    <div class="products-price">R$250,00</div>
                </a>
            </div>
            
            <!-- Produto -->
            <div class="products-item">
                <a href="ProdutoController?produtoId=4">
                    <div class="products-cart">Ver Detalhes</div>
                    <img src="https://images-americanas.b2w.io/produtos/01/00/img/471961/8/471961879_1GG.jpg">
                    <div class="products-title">FIFA 20</div>
                    <div class="products-details">PS4 - Jogos</div>
                    <div class="products-price">R$250,00</div>
                </a>
            </div>
            
            <!-- Produto -->
            <div class="products-item">
                <a href="ProdutoController?produtoId=4">
                    <div class="products-cart">Ver Detalhes</div>
                    <img src="https://images-americanas.b2w.io/produtos/01/00/img/471961/8/471961879_1GG.jpg">
                    <div class="products-title">FIFA 20</div>
                    <div class="products-details">PS4 - Jogos</div>
                    <div class="products-price">R$250,00</div>
                </a>
            </div>
            
        </div>
        
        <!-- Páginas -->
        <div class="products-paging">
            <form method="post" action="ProdutosController">
                <button class="products-prev" name="action" value="ant">&#8249;</button>
                <span><%= session.getAttribute("ProdutosPag") %></span>
                <button class="products-next" name="action" value="prox">&#8250;</button>
            </form>
        </div>
    </div>
</div>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>