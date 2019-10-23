<%-- 
    Document   : produto-grid
    Created on : 02/10/2019, 02:09:29
    Author     : HP
--%>
<%@page import="java.util.ArrayList"%>
<%
    // se n tiver um usuario logado retorna p userController
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("UserController");
    }
    // mostra se tiver msg
    if (session.getAttribute("msg") != null) {
        String msg = session.getAttribute("msg").toString();
        session.setAttribute("msg", null);
        out.println("<script>alert('" + msg + "');</script>");
    }

    ArrayList<ArrayList<String>> grid = null;
    if (request.getAttribute("usuarios") != null) {
        grid = (ArrayList<ArrayList<String>>) request.getAttribute("usuarios");
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Minha Conta"/>
</jsp:include>
<table width="100%" border="1" cellspacing="10">
    <thead>
        <tr>
            <th colspan="5"><h2>Usuário<%= (session.getAttribute("userRole").equals("1") ? "s" : "")%></h2></th>
        </tr>
        <tr>
            <th>Operações</th>
            <th>Nome</th>
            <th>Email</th>
        </tr>
    </thead>
    <tbody>
        <%
            for (int i = 0; i < grid.size(); i++) {
        %>
        <tr>
            <th>
                <a href="UserController?sel=<%= grid.get(i).get(0)%>">Editar</a>             
            </th>
            <th><%= grid.get(i).get(1)%></th>
            <th><%= grid.get(i).get(2)%></th>
        </tr>
        <%
            }
        %>
    </tbody>
    <tfoot>
        <tr>
            <th colspan="5">
                <a href="EnderecoController">Lista de Endereços</a>
            </th>
        </tr>
        <tr>
            <th colspan="5">
                <a href="ProdutosController?esp=favoritos">Favoritos</a>
            </th>
        </tr>
        <tr>
            <th colspan="5">
                <a href="CompraController?historico">Histórico de Pedidos</a>
            </th>
        </tr>
        <!-- SE ROLE_ID DO USUARIO FOR ADM ENTAO MOSTRA CADASTRO DE PRODUTOS -->
        <% if (session.getAttribute("userRole").equals("1")) { %>
        <tr>
            <th colspan="5">
                <a href="ProdutoAdmController">Cadastro de Produtos</a>
            </th>
        </tr>
        <% }%>
        <tr>
            <th colspan="5">
                <form method="post" action="UserController">
                    <button onclick="return confirm('Tem certeza que deseja fazer o LOGOUT?');false;" type="submit" name="action" value="logout" formnovalidate>LOGOUT</button>
                </form>
            </th>
        </tr>    
    </tfoot>
</table>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>