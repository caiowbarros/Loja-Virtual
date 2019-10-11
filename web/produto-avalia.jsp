<%-- 
    Document   : produto-avalia
    Created on : 02/10/2019, 23:33:28
    Author     : HP
--%>
<%
    // se n tiver um usuario logado retorna p controller
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("UserController?redirect=AvaliacaoController");
    }
    // se n tiver produto selecionado retorna p pag d produtos
    if (session.getAttribute("produtoId") == null) {
        response.sendRedirect("ProdutosController");
    }
    // mostra msg se tiver
    if (session.getAttribute("msg") != null) {
        String msg = session.getAttribute("msg").toString();
        session.setAttribute("msg", null);
        out.println("<script>alert('" + msg + "');</script>");
    }

    // define rating
    String rating = "";
    if (session.getAttribute("rating") != null) {
        rating = session.getAttribute("rating").toString();
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Avalia Produto"/>
</jsp:include>
<a href="ProdutoController">Voltar</a>
<form action="AvaliacaoController" method="post">
    <fieldset>
        <legend>Avaliação</legend>
        <!-- Avaliação do produto (em estrelas) -->
        <div class="rate">
            <input <%= rating.equals("5") ? "selected" : ""%> type="radio" id="star5" name="rating" value="5" />
            <label for="star5" title="text"></label>
            <input <%= rating.equals("4") ? "selected" : ""%> type="radio" id="star4" name="rating" value="4" />
            <label for="star4" title="text"></label>
            <input <%= rating.equals("3") ? "selected" : ""%> type="radio" id="star3" name="rating" value="3" />
            <label for="star3" title="text"></label>
            <input <%= rating.equals("2") ? "selected" : ""%> type="radio" id="star2" name="rating" value="2" />
            <label for="star2" title="text"></label>
            <input <%= rating.equals("1") ? "selected" : ""%> type="radio" id="star1" name="rating" value="1" />
            <label for="star1" title="text"></label>
        </div>
        <input name="title" required type="text" placeholder="Título da Avaliação" maxlength="255" />
        <input name="description" required type="text" placeholder="Descricao da Avaliação" maxlength="255" />
        <button name="action" value="avalia" type="submit">Salvar</button>
    </fieldset>
</form>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>