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
        response.sendRedirect("produto");
    }

    // mostra msg se tiver
    if (session.getAttribute("msg") != null) {
        String msg = session.getAttribute("msg").toString();
        session.setAttribute("msg", null);
        out.println("<script>alert('" + msg + "');</script>");
    }

    ArrayList<String> produto = null;
    ArrayList<ArrayList<String>> avaliacoes = null;
    if (request.getAttribute("produto") != null) {
        produto = (ArrayList<String>) request.getAttribute("produto");
        avaliacoes = (ArrayList<ArrayList<String>>) request.getAttribute("avaliacoes");
    } else {
        session.setAttribute("msg", "Por favor selecione um produto!");
        response.sendRedirect("produtos");
        return;
    }

    String rateGaveByUser = "";
    if (produto.get(8) != null) {
        rateGaveByUser = produto.get(8);
    }

    int somaAvaliacoes = 0;
    if (produto.get(10) != null) {
        somaAvaliacoes = Integer.valueOf(produto.get(10).toString());
    }
    int qtdAvaliacoes = 0;
    if (produto.get(9) != null) {
        qtdAvaliacoes = Integer.valueOf(produto.get(9).toString());
    }

    //calcula resumo das avaliacoes
    double resumoAvaliacoes = 0;
    if (qtdAvaliacoes > 0) {
        resumoAvaliacoes = (double)somaAvaliacoes / (double)qtdAvaliacoes;
    }

//calcula tamanho da barra das avaliacoes
    int barraUmaEstrela = 0;
    int qtdUmaEstrela = 0;
    if (produto.get(11) != null) {
        qtdUmaEstrela = Integer.valueOf(produto.get(11));
        if (qtdAvaliacoes > 0) {
            barraUmaEstrela = (qtdUmaEstrela * 100) / qtdAvaliacoes;
        }
    }

    int barraDuasEstrelas = 0;
    int qtdDuasEstrelas = 0;
    if (produto.get(12) != null) {
        qtdDuasEstrelas = Integer.valueOf(produto.get(12));
        if (qtdAvaliacoes > 0) {
            barraDuasEstrelas = (qtdDuasEstrelas * 100) / qtdAvaliacoes;
        }
    }

    int barraTresEstrelas = 0;
    int qtdTresEstrelas = 0;
    if (produto.get(13) != null) {
        qtdTresEstrelas = Integer.valueOf(produto.get(13));
        if (qtdAvaliacoes > 0) {
            barraTresEstrelas = (qtdTresEstrelas * 100) / qtdAvaliacoes;
        }
    }

    int barraQuatroEstrelas = 0;
    int qtdQuatroEstrelas = 0;
    if (produto.get(14) != null) {
        qtdQuatroEstrelas = Integer.valueOf(produto.get(14));
        if (qtdAvaliacoes > 0) {
            barraQuatroEstrelas = (qtdQuatroEstrelas * 100) / qtdAvaliacoes;
        }
    }

    int barraCincoEstrelas = 0;
    int qtdCincoEstrelas = 0;
    if (produto.get(15) != null) {
        qtdCincoEstrelas = Integer.valueOf(produto.get(15));
        if (qtdAvaliacoes > 0) {
            barraCincoEstrelas = (qtdCincoEstrelas * 100) / qtdAvaliacoes;
        }
    }

%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="<%= produto.get(1)%>"/>
</jsp:include>

<main class="container">

    <!-- Coluna da esquerda / Imagem do produto -->
    <div class="left-column">
        <img src="<%= produto.get(5)%>">
    </div>

    <!-- Coluna da direita -->
    <div class="right-column">

        <!-- Descrição do produto -->
        <div class="product-description">
            <span><%= produto.get(6)%></span>
            <h1><%= produto.get(1)%></h1>
            <p><%= produto.get(3)%></p>
        </div>

        <!-- Preço do produto -->
        <div class="product-price">
            <span>R$<%= produto.get(4)%></span>
            <a href="CarrinhoController?addProdutoId=<%= produtoId%>" class="cart-btn">+ Carrinho</a>
            <!-- Botão de favorito -->
            <input <%= (Integer.valueOf(produto.get(7).toString()) > 0 ? "checked" : "")%> onchange="window.location.href = 'ProdutoController?fav=<%= produtoId%>'" id="toggle-heart" type="checkbox" />
            <label for="toggle-heart">&#x2764;</label>
        </div>
        <!-- Avaliação do produto (em estrelas) -->
        <div class="rate" <%= (!rateGaveByUser.equals("") ? "style='pointer-events:none'" : "")%>>
            <input <%= (rateGaveByUser.equals("5") ? "checked" : "")%> onClick="window.location.href = 'AvaliacaoController?produtoId=<%= produtoId%>&rating=5'" type="radio" id="star5" name="rate" value="5" />
            <label for="star5" title="text"></label>
            <input <%= (rateGaveByUser.equals("4") ? "checked" : "")%> onClick="window.location.href = 'AvaliacaoController?produtoId=<%= produtoId%>&rating=4'" type="radio" id="star4" name="rate" value="4" />
            <label for="star4" title="text"></label>
            <input <%= (rateGaveByUser.equals("3") ? "checked" : "")%> onClick="window.location.href = 'AvaliacaoController?produtoId=<%= produtoId%>&rating=3'" type="radio" id="star3" name="rate" value="3" />
            <label for="star3" title="text"></label>
            <input <%= (rateGaveByUser.equals("2") ? "checked" : "")%> onClick="window.location.href = 'AvaliacaoController?produtoId=<%= produtoId%>&rating=2'" type="radio" id="star2" name="rate" value="2" />
            <label for="star2" title="text"></label>
            <input <%= (rateGaveByUser.equals("1") ? "checked" : "")%> onClick="window.location.href = 'AvaliacaoController?produtoId=<%= produtoId%>&rating=1'" type="radio" id="star1" name="rate" value="1" />
            <label for="star1" title="text"></label>
        </div>
    </div>

</main>

<div class="product-rating">

    <!-- Coluna da esquerda (resumo da avaliação) -->
    <div class="left-rating">
        <div class="review-resume">Resumo das avaliações</div>
        <div class="review-rate"><%= resumoAvaliacoes%> / 5</div>
        <div class="review-list">
            <ul>
                <li>
                    <div>5 ESTRELAS</div>
                    <div class="percentage">
                        <div class="percentage-full" style="width: <%= barraCincoEstrelas%>%;"></div>
                    </div>
                    <div class="count"><%= qtdCincoEstrelas%></div>
                </li>
                <li>
                    <div>4 ESTRELAS</div>
                    <div class="percentage">
                        <div class="percentage-full" style="width: <%= barraQuatroEstrelas%>%;"></div>
                    </div>
                    <div class="count"><%= qtdQuatroEstrelas%></div>
                </li>
                <li>
                    <div>3 ESTRELAS</div>
                    <div class="percentage">
                        <div class="percentage-full" style="width: <%= barraTresEstrelas%>%;"></div>
                    </div>
                    <div class="count"><%= qtdTresEstrelas%></div>
                </li>
                <li>
                    <div>2 ESTRELAS</div>
                    <div class="percentage">
                        <div class="percentage-full" style="width: <%= barraDuasEstrelas%>%;"></div>
                    </div>
                    <div class="count"><%= qtdDuasEstrelas%></div>
                </li>
                <li>
                    <div>1 ESTRELAS</div>
                    <div class="percentage">
                        <div class="percentage-full" style="width: <%= barraUmaEstrela%>%;"></div>
                    </div>
                    <div class="count"><%= qtdUmaEstrela%></div>
                </li>
            </ul>
        </div>
    </div>

    <!-- Coluna da direita (avaliações em si) -->
    <div class="right-rating">

        <%
            for (int i = 0; i < avaliacoes.size(); i++) {
        %>
        <!-- Avaliação do cliente -->
        <div class="review-star">
            <%
                for (int r = 0; r < Integer.valueOf(avaliacoes.get(i).get(2)); r++) {
            %>
            &#x2605;
            <%
                }
            %>
            <div class="review-date"><%= avaliacoes.get(i).get(5)%></div>
        </div>
        <div class="review-title"><%= avaliacoes.get(i).get(1)%><div class="review-text"><%= avaliacoes.get(i).get(3)%></div>
        </div>
        <div class="review-name"><%= avaliacoes.get(i).get(0)%></div>
        <%
            }
        %>

        <% if (rateGaveByUser.equals("")) { %>
        <!-- Botão para avaliar o produto -->
        <div class="review-btn">
            <a href="AvaliacaoController?produtoId=<%= produtoId%>">Adicionar uma avaliação</a>
        </div>
        <% }%>
    </div>
</div>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>