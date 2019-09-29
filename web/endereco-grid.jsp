<%-- 
    Document   : endereco-grid
    Created on : 29/09/2019, 00:45:25
    Author     : HP
--%>
<%
    // se n tiver um usuario logado retorna p controller
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("UserController?redirect=EnderecoController");
    }
    if (request.getAttribute("msg") != null) {
        out.println("<script>alert('" + request.getAttribute("msg") + "');</script>");
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Cadastro de Endereços"/>
</jsp:include>
<table width="100%" border="1" cellspacing="10">
    <thead>
        <tr>
            <th colspan="6"><h2>Endereços</h2></th>
        </tr>
        <tr>
            <th>Operações</th>
            <th>Descrição do Endereço</th>
            <th>Rua</th>
            <th>Cidade</th>
            <th>Estado</th>
            <th>País</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <th>
                <a href="EnderecoController?sel=1">Selecionar</a>
                &nbsp;|&nbsp;
                <a href="EnderecoController?del=1">Excluir</a>
            </th>
            <th>APT</th>
            <th>Rua Ary Parreiras, 4, 1201</th>
            <th>Niterói</th>
            <th>RJ</th>
            <th>Brasil</th>
        </tr>
        <tr>
            <th>
                <a href="EnderecoController?sel=2">Selecionar</a>
                &nbsp;|&nbsp;
                <a href="EnderecoController?del=2">Excluir</a>
            </th>
            <th>Trabalho</th>
            <th>Rua Miguel Couto, 105</th>
            <th>Rio de Janeiro</th>
            <th>RJ</th>
            <th>Brasil</th>
        </tr>
    </tbody>
</table>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>