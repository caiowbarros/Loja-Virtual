<%-- 
    Document   : produto-grid
    Created on : 02/10/2019, 02:09:29
    Author     : HP
--%>
<%@page import="java.util.ArrayList"%>
<%
    // se n usuario n for adm retorna p ProdutosController
    if (!session.getAttribute("userRole").equals("1")) {
        response.sendRedirect("UserController");
    }
    // mostra se tiver msg
    if (session.getAttribute("msg") != null) {
        String msg = session.getAttribute("msg").toString();
        session.setAttribute("msg", null);
        out.println("<script>alert('" + msg + "');</script>");
    }

    ArrayList<ArrayList<String>> grid = null;
    if (request.getAttribute("grid") != null) {
        grid = (ArrayList<ArrayList<String>>) request.getAttribute("grid");
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
        <%
            for (int i = 0; i < grid.size(); i++) {
        %>
        <tr>
            <th>
                <a href="ProdutoAdmController?sel=<%= grid.get(i).get(0)%>">Selecionar</a>             
            </th>
            <th><%= grid.get(i).get(1)%></th>
            <th>R$<%= grid.get(i).get(2)%></th>
            <th><%= grid.get(i).get(3)%></th>
        </tr>
        <%
            }
        %>
    </tbody>
    <tfoot>
        <tr>
            <th colspan="4">
                <a href="ProdutoAdmController?sel">Incluir</a>
            </th>
        </tr>
    </tfoot>
</table>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>