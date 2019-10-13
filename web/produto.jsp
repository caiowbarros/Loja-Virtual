<%-- 
    Document   : template-produto
    Created on : 13/09/2019, 18:51:42
    Author     : Caio
--%>
<%@page import="java.util.ArrayList"%>
<%
    // recupera produtoId
    String produtoId = "";
    if (session.getAttribute("produtoId") != null) {
        produtoId = session.getAttribute("produtoId").toString();
    } else {
        response.sendRedirect("ProdutoController");
    }

    // mostra msg se tiver
    if (session.getAttribute("msg") != null) {
        String msg = session.getAttribute("msg").toString();
        session.setAttribute("msg", null);
        out.println("<script>alert('" + msg + "');</script>");
    }

    ArrayList<String> produto = null;
    if (request.getAttribute("produto") != null) {
        produto = (ArrayList<String>) request.getAttribute("produto");
    } else {
        session.setAttribute("msg", "Por favor selecione um produto!");
        response.sendRedirect("ProdutosController");
        return;
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="<%= produto.get(1) %>"/>
</jsp:include>

<main class="container">

    <!-- Coluna da esquerda / Imagem do produto -->
    <div class="left-column">
        <img src="<%= produto.get(5) %>">
    </div>

    <!-- Coluna da direita -->
    <div class="right-column">

        <!-- Descrição do produto -->
        <div class="product-description">
            <span><%= produto.get(6) %></span>
            <h1><%= produto.get(1) %></h1>
            <p><%= produto.get(3) %></p>
        </div>

        <!-- Preço do produto -->
        <div class="product-price">
            <span>R$<%= produto.get(4) %></span>
            <a href="CarrinhoController?addProdutoId=<%= produtoId%>" class="cart-btn">+ Carrinho</a>
            <!-- Botão de favorito -->
            <input onchange="window.location.href = 'ProdutoController?fav=<%= produtoId%>'" id="toggle-heart" type="checkbox" />
            <label for="toggle-heart">&#x2764;</label>
        </div>
        <!-- Avaliação do produto (em estrelas) -->
        <div class="rate">
            <input onClick="window.location.href = 'AvaliacaoController?produtoId=<%= produtoId%>&rating=5'" type="radio" id="star5" name="rate" value="5" />
            <label for="star5" title="text"></label>
            <input onClick="window.location.href = 'AvaliacaoController?produtoId=<%= produtoId%>&rating=4'" type="radio" id="star4" name="rate" value="4" />
            <label for="star4" title="text"></label>
            <input checked onClick="window.location.href = 'AvaliacaoController?produtoId=<%= produtoId%>&rating=3'" type="radio" id="star3" name="rate" value="3" />
            <label for="star3" title="text"></label>
            <input onClick="window.location.href = 'AvaliacaoController?produtoId=<%= produtoId%>&rating=2'" type="radio" id="star2" name="rate" value="2" />
            <label for="star2" title="text"></label>
            <input onClick="window.location.href = 'AvaliacaoController?produtoId=<%= produtoId%>&rating=1'" type="radio" id="star1" name="rate" value="1" />
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
            <a href="AvaliacaoController">Adicionar uma avaliação</a>
        </div>
    </div>
</div>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>