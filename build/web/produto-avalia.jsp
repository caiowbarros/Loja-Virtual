<%-- 
    Document   : produto-avalia
    Created on : 02/10/2019, 23:33:28
    Author     : HP
--%>
<%
    // se n tiver um usuario logado retorna p controller
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("UserController?redirect=ProdutoController");
    }
    if (request.getAttribute("msg") != null) {
        out.println("<script>alert('" + request.getAttribute("msg") + "');</script>");
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Avalia Produto"/>
</jsp:include>
<form action="ProdutoController?produtoId=2" method="post">
    <fieldset>
        <legend>Avaliação</legend>
        <!-- Avaliação do produto (em estrelas) -->
        <div class="rate">
            <input  type="radio" id="star5" name="rating" value="5" />
            <label for="star5" title="text"></label>
            <input  type="radio" id="star4" name="rating" value="4" />
            <label for="star4" title="text"></label>
            <input  type="radio" id="star3" name="rating" value="3" />
            <label for="star3" title="text"></label>
            <input  type="radio" id="star2" name="rating" value="2" />
            <label for="star2" title="text"></label>
            <input  type="radio" id="star1" name="rating" value="1" />
            <label for="star1" title="text"></label>
        </div>
        <input name="Title" required type="text" placeholder="Título da Avaliação" maxlength="255" />
        <input name="description" required type="text" placeholder="Descricao da Avaliação" maxlength="255" />
        <button name="action" value="avalia" type="submit">Salvar</button>
    </fieldset>
</form>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>