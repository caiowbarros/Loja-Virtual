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
                <a href="produto.jsp">
                    <img src="https://images-americanas.b2w.io/produtos/01/00/img/471961/8/471961879_1GG.jpg">
                    <div class="products-title">FIFA 20</div>
                    <div class="products-details">PS4 - Jogos</div>
                    <div class="products-price">R$250,00</div>
                </a>
            </div>
            
            <!-- Produto -->
            <div class="products-item">
                <a href="produto.jsp">
                    <img src="https://images-americanas.b2w.io/produtos/01/00/img/471961/8/471961879_1GG.jpg">
                    <div class="products-title">FIFA 20</div>
                    <div class="products-details">PS4 - Jogos</div>
                    <div class="products-price">R$250,00</div>
                </a>
            </div>
            
            <!-- Produto -->
            <div class="products-item">
                <a href="produto.jsp">
                    <img src="https://images-americanas.b2w.io/produtos/01/00/img/471961/8/471961879_1GG.jpg">
                    <div class="products-title">FIFA 20</div>
                    <div class="products-details">PS4 - Jogos</div>
                    <div class="products-price">R$250,00</div>
                </a>
            </div>
            
            <!-- Produto -->
            <div class="products-item">
                <a href="produto.jsp">
                    <img src="https://images-americanas.b2w.io/produtos/01/00/img/471961/8/471961879_1GG.jpg">
                    <div class="products-title">FIFA 20</div>
                    <div class="products-details">PS4 - Jogos</div>
                    <div class="products-price">R$250,00</div>
                </a>
            </div>
            
        </div>
        
        <!-- Páginas -->
        <div class="products-paging">
            <form action="ProdutosController?paginacao">
                <button class="products-prev" name="action" value="ant">&#8249;</button>
                <span><%= session.getAttribute("ProdutosPag") %></span>
                <button class="products-next" name="action" value="prox">&#8250;</button>
            </form>
        </div>
    </div>


</div>
<!-- <div>
    <fieldset>
        <legend>filter</legend>
        <fieldset>
            <legend>category</legend>
            <input type="checkbox" id="playstation" name="category" value="playstation"><label for="playstation">playstation</label>
            <input type="checkbox" id="xbox" name="category" value="xbox"><label for="xbox">xbox</label>
            <input type="checkbox" id="wii" name="category" value="wii"><label for="wii">wii</label>
        </fieldset>
        <fieldset>
            <legend>sub-category</legend>
            <input type="checkbox" id="console" name="category" value="console"><label for="calca">Console</label>
            <input type="checkbox" id="jogo" name="category" value="jogo"><label for="jogo">Jogo</label>
            <input type="checkbox" id="acessorio" name="category" value="acessorio"><label for="acessorio">Acessorio</label>
        </fieldset>
        <label for="price">min value</label>
        <input type="range" name="price_min" min="1" max="10" step="1" oninput="display_min.value=value" onchange="display_min.value = value">
        <input type="number" readonly id="display_min" value="0"/>
        <label for="price">max value</label>
        <input type="range" name="price_max" min="1" max="10" step="1" oninput="display_max.value=value" onchange="display_max.value = value">
        <input type="number" readonly id="display_max" value="0"/>
        <input name="name" type="text" placeholder="name" maxlength="255" />
        <input name="description" type="text" placeholder="description" maxlength="255" />
        <button type="submit" name="action" value="filtra">filter</button>
    </fieldset>
</div>
<div class="fundo-paisagem1 paralax">
    <section class="lista-produtos">

        <a href="ProdutoController?produtoId=1">
            <div class="produto">
                <img src="https://www.w3schools.com/w3images/jeans3.jpg" alt="Denim Jeans" class="imagem-produto">
                <p class="titulo-produto">Tailored Jeans</p>
                <p class="preco-produto">$19.99</p>
                <p class="descricao-produto">Some text about the jeans. Super slim and comfy lorem ipsum lorem
                    jeansum. Lorem jeamsun denim
                    lorem jeansum.</p>
            </div>
        </a>
<a href="ProdutoController?produtoId=1">
            <div class="produto">
                <img src="https://www.w3schools.com/w3images/jeans3.jpg" alt="Denim Jeans" class="imagem-produto">
                <p class="titulo-produto">Tailored Jeans</p>
                <p class="preco-produto">$19.99</p>
                <p class="descricao-produto">Some text about the jeans. Super slim and comfy lorem ipsum lorem
                    jeansum. Lorem jeamsun denim
                    lorem jeansum.</p>
            </div>
        </a><a href="ProdutoController?produtoId=1">
            <div class="produto">
                <img src="https://www.w3schools.com/w3images/jeans3.jpg" alt="Denim Jeans" class="imagem-produto">
                <p class="titulo-produto">Tailored Jeans</p>
                <p class="preco-produto">$19.99</p>
                <p class="descricao-produto">Some text about the jeans. Super slim and comfy lorem ipsum lorem
                    jeansum. Lorem jeamsun denim
                    lorem jeansum.</p>
            </div>
        </a><a href="ProdutoController?produtoId=1">
            <div class="produto">
                <img src="https://www.w3schools.com/w3images/jeans3.jpg" alt="Denim Jeans" class="imagem-produto">
                <p class="titulo-produto">Tailored Jeans</p>
                <p class="preco-produto">$19.99</p>
                <p class="descricao-produto">Some text about the jeans. Super slim and comfy lorem ipsum lorem
                    jeansum. Lorem jeamsun denim
                    lorem jeansum.</p>
            </div>
        </a><a href="ProdutoController?produtoId=1">
            <div class="produto">
                <img src="https://www.w3schools.com/w3images/jeans3.jpg" alt="Denim Jeans" class="imagem-produto">
                <p class="titulo-produto">Tailored Jeans</p>
                <p class="preco-produto">$19.99</p>
                <p class="descricao-produto">Some text about the jeans. Super slim and comfy lorem ipsum lorem
                    jeansum. Lorem jeamsun denim
                    lorem jeansum.</p>
            </div>
        </a>

    </section>
</div>
<div>
    <form action="ProdutosController?paginacao">
        <button name="action" value="ant">Página Anterior</button>
        <span><%= session.getAttribute("ProdutosPag") %></span>
        <button name="action" value="prox">Próxima Página</button>
    </form>
</div> -->
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>