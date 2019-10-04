<%-- 
    Document   : produto-cadastro
    Created on : 02/10/2019, 02:09:04
    Author     : HP
--%>
<%
    // se n usuario n for adm retorna p ProdutosController
    if (!session.getAttribute("userRole").equals("1")) {
        response.sendRedirect("UserController?redirect=ProdutosController");
    }
    if (request.getAttribute("msg") != null) {
        out.println("<script>alert('" + request.getAttribute("msg") + "');</script>");
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Cadastro de Produtos"/>
</jsp:include>
<a href="ProdutoController?unsel">Voltar</a>
<form method="post" action="ProdutoController">
    <fieldset>
        <legend>product</legend>
        <input name="name" required type="text" placeholder="name" maxlength="255" />
        <input name="price" required type="number" min="0.01" step="0.01" placeholder="price" maxlength="255" />
        <input name="description" required type="text" placeholder="description" maxlength="255" />
        <input name="category_id" required type="text" maxlength="255" />
        <input name="created_at" type="datetime" required/>
        <input name="img" accept="image/x-png,image/gif,image/jpeg" required type="file"/>
        <button type="submit" name="action" value="grava">Gravar</button>
    </fieldset>
</form>
<a href="ProdutoController?qtdEstoque">Add unidades no estoque</a>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>