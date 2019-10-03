<%-- 
    Document   : template-produto
    Created on : 13/09/2019, 18:51:42
    Author     : Caio
--%>
<%
    if (request.getAttribute("produto") == null && request.getParameter("produto") == null) {
        response.sendRedirect("ProdutosController");
    }
    // mostra msg se tiver
    if (request.getAttribute("msg") != null) {
        out.println("<script>alert('" + request.getAttribute("msg") + "');</script>");
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Camisa CR Flamengo 1"/>
</jsp:include>

<main class="container">

    <!-- Coluna da esquerda / Imagem do produto -->
    <div class="left-column">
        <img src="https://wqsurf.vteximg.com.br/arquivos/ids/176435-1000-1000/camisa-flamengo-jogo-1-adidas-2019-bs2-58454-1.jpg?v=636987970579800000">
    </div>

    <!-- Coluna da direita -->
    <div class="right-column">

        <!-- Descrição do produto -->
        <div class="product-description">
            <span>Camisetas</span>
            <h1>Camisa CR Flamengo 1</h1>
            <p>As ruas do Rio de Janeiro são pavimentadas com mosaicos coloridos. O design fragmentado desta camisa de futebol foi montado para refletir o estilo único da casa do CR Flamengo. Feita para os fãs, ela coloca o conforto em primeiro lugar com um tecido sedoso que absorve o suor e uma modelagem um pouco mais larga do que a usada pelos jogadores em campo. Um escudo do time se destaca no peito.</p>
        </div>

        <!-- Preço do produto -->
        <div class="product-price">
            <span>R$250,00</span>
            <a href="carrinho.jsp?addProdutoId=" class="cart-btn">+ Carrinho</a>
            <!-- Botão de favorito -->
            <input id="toggle-heart" type="checkbox" />
            <label for="toggle-heart">&#x2764;</label>
        </div>
        <!-- Avaliação do produto (em estrelas) -->
        <div class="rate">
            <input onClick="window.location.href = 'ProdutoController?action=mostra_avaliacao&produtoId=2&rating=5'" type="radio" id="star5" name="rate" value="5" />
            <label for="star5" title="text"></label>
            <input onClick="window.location.href = 'ProdutoController?action=mostra_avaliacao&produtoId=2&rating=4'" type="radio" id="star4" name="rate" value="4" />
            <label for="star4" title="text"></label>
            <input onClick="window.location.href = 'ProdutoController?action=mostra_avaliacao&produtoId=2&rating=3'" type="radio" id="star3" name="rate" value="3" />
            <label for="star3" title="text"></label>
            <input onClick="window.location.href = 'ProdutoController?action=mostra_avaliacao&produtoId=2&rating=2'" type="radio" id="star2" name="rate" value="2" />
            <label for="star2" title="text"></label>
            <input onClick="window.location.href = 'ProdutoController?action=mostra_avaliacao&produtoId=2&rating=1'" type="radio" id="star1" name="rate" value="1" />
            <label for="star1" title="text"></label>
        </div>
    </div>

</main>

<div class="product-rating">

    <!-- Coluna da esquerda (resumo da avaliação) -->
    <div class="left-rating">
        <div class="review-resume">Resumo das avaliações</div>
        <div class="review-rate">4.5 / 5</div>
        <div class="review-list">
            <ul>
                <li>
                    <div>5 ESTRELAS</div>
                    <div class="percentage">
                        <div class="percentage-full" style="width: 50%;"></div>
                    </div>
                    <div class="count">1</div>
                </li>
                <li>
                    <div>4 ESTRELAS</div>
                    <div class="percentage">
                        <div class="percentage-full" style="width: 50%;"></div>
                    </div>
                    <div class="count">1</div>
                </li>
                <li>
                    <div>3 ESTRELAS</div>
                    <div class="percentage">
                        <div class="percentage-full" style="width: 0%;"></div>
                    </div>
                    <div class="count">0</div>
                </li>
                <li>
                    <div>2 ESTRELAS</div>
                    <div class="percentage">
                        <div class="percentage-full" style="width: 0%;"></div>
                    </div>
                    <div class="count">0</div>
                </li>
                <li>
                    <div>1 ESTRELAS</div>
                    <div class="percentage">
                        <div class="percentage-full" style="width: 0%;"></div>
                    </div>
                    <div class="count">0</div>
                </li>
            </ul>
        </div>
    </div>

    <!-- Coluna da direita (avaliações em si) -->
    <div class="right-rating">

        <!-- Avaliação do cliente -->
        <div class="review-star">&#x2605;&#x2605;&#x2605;&#x2605;&#x2605;
            <div class="review-date">setembro 15, 2019</div>
        </div>

        <div class="review-title">Entrega rápida, numeração top!
            <div class="review-text">Produto de excelente qualidade, entrega rápida e personalização da numeração diferenciada.</div>
        </div>
        <div class="review-name">Caio Wey Barros</div>

        <!-- Avaliação do cliente -->
        <div class="review-star">&#x2605;&#x2605;&#x2605;&#x2605;
            <div class="review-date">setembro 20, 2019</div>
        </div>

        <div class="review-title">Excelente compra! Presentei meu irmão com essa camisa ele amou!
            <div class="review-text">produto excelente, de qualidade e ainda é possível personalizar! Recomendo.</div>
        </div>
        <div class="review-name">Igor Rodrigues Lisboa</div>

        <!-- Botão para avaliar o produto -->
        <div class="review-btn">
            <a href="ProdutoController?action=mostra_avaliacao&produtoId=2">Adicionar uma avaliação</a>
        </div>
    </div>
</div>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>