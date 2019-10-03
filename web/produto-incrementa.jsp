<%-- 
    Document   : produto-incrementa
    Created on : 02/10/2019, 02:09:47
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
    <jsp:param name="title" value="Adição de Produtos ao Estoque"/>
</jsp:include>
<a href="ProdutoController?sel=1">Voltar</a>
<form action="ProdutoController" method="post">
    <fieldset>
        <legend>Adicionar Unidades ao Estoque</legend>
        <input name="amount" min="1" required type="number" placeholder="amount" maxlength="11">
        <button type="submit" name="action" value="estoqueInsere">Aumentar</button>
    </fieldset>
</form>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>