<%-- 
    Document   : produto-grid
    Created on : 02/10/2019, 02:09:29
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
    <jsp:param name="title" value="Grid de Produtos"/>
</jsp:include>
<table width="100%" border="1" cellspacing="10">
    <thead>
        <tr>
            <th colspan="4"><h2>Produtos</h2></th>
        </tr>
        <tr>
            <th>Operações</th>
            <th>Nome</th>
            <th>Preço</th>
            <th>Categoria</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <th>
                <a href="ProdutoController?sel=1">Selecionar</a>
                &nbsp;|&nbsp;
                <a onclick="return confirm('Tem certeza que deseja excluir esse produto?');false;" href="ProdutoControler?del=1">Excluir</a>                
            </th>
            <th>Xbox</th>
            <th>R$3000,00</th>
            <th>Console</th>
        </tr>
    </tbody>
</table>
<!-- SE N FOR PRODUTO NOVO MOSTRA INCLUIR -->
<a href="ProdutoController?sel">Incluir</a>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>