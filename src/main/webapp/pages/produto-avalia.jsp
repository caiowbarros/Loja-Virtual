<%-- 
    Document   : produto-avalia
    Created on : 02/10/2019, 23:33:28
    Author     : HP
--%>
<%@page import="br.uff.loja.infrastructure.shared.Helper"%>
<%
    // se n tiver um usuario logado retorna p controller
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("usuario?redirect=avaliacao");
    }
    // se n tiver produto selecionado retorna p pag d produtos
    if (session.getAttribute("produtoId") == null) {
        response.sendRedirect("produtos");
    }

    // define rating
    Integer rating = 0;
    if (session.getAttribute("rating") != null) {
        rating = (new Helper()).tryParseInteger(session.getAttribute("rating").toString());
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Avalia??o do Produto"/>
</jsp:include>

<form action="avaliacao" method="post">

    <ul class="form-style-1">
        <!-- Avalia??o do produto (em estrelas) -->
        <li>
            <label>Avalia??o </label>
            <div class="rate">
                <input <%= rating.equals(5) ? "checked" : ""%> type="radio" id="star5" name="rating" value="5" />
                <label for="star5" title="text"></label>
                <input <%= rating.equals(4) ? "checked" : ""%> type="radio" id="star4" name="rating" value="4" />
                <label for="star4" title="text"></label>
                <input <%= rating.equals(3) ? "checked" : ""%> type="radio" id="star3" name="rating" value="3" />
                <label for="star3" title="text"></label>
                <input <%= rating.equals(2) ? "checked" : ""%> type="radio" id="star2" name="rating" value="2" />
                <label for="star2" title="text"></label>
                <input <%= rating.equals(1) ? "checked" : ""%> type="radio" id="star1" name="rating" value="1" />
                <label for="star1" title="text"></label>
            </div>
            <br>
        </li>
        <li>
            <br>
            <label>T?tulo </label>
            <input class="field-long" name="title" required type="text" maxlength="255" />
        </li>
        <li>
            <label>Descri??o </label>
            <textarea class="field-long field-textarea" name="description" required type="text" maxlength="255"></textarea>
        </li>
        <li class="center">
            <a href="ProdutoController">Voltar</a>
            <button name="action" value="avalia" type="submit">Salvar</button>
        </li>
    </ul>
</form>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>