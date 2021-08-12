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

<table class="product-grid" cellspacing="10">
    <thead>
        <tr>
            <th colspan="5"><h2>Produtos</h2></th>
        </tr>
        <tr class="product-grid-title">
            <td>Operações</td>
            <td>Nome</td>
            <td>Preço</td>
            <td>Categoria</td>
            <td>Quantidade no Estoque</td>
        </tr>
    </thead>
    <tbody>
        <%
            for (int i = 0; i < grid.size(); i++) {
        %>
        <tr class="product-data">
            <td>
                <a href="ProdutoAdmController?sel=<%= grid.get(i).get(0)%>">Editar</a>             
            </td>
            <td><%= grid.get(i).get(1)%></td>
            <td>R$<%= grid.get(i).get(2)%></td>
            <td><%= grid.get(i).get(3)%></td>
            <td><%= grid.get(i).get(4)%></td>
        </tr>
        <%
            }
        %>
    </tbody>
    <tfoot>
        <tr class="product-data-add">
            <td colspan="5">
                <a class="end-link" href="ProdutoAdmController?sel">Inserir Novo Produto</a>
            </td>
        </tr>
    </tfoot>
</table>
    
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>