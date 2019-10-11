<%-- 
    Document   : endereco-grid
    Created on : 29/09/2019, 00:45:25
    Author     : HP
--%>
<%@page import="java.util.ArrayList"%>
<%
    // se n tiver um usuario logado retorna p controller com redirect p EnderecoController
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("UserController?redirect=EnderecoController");
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
        <%
            for (int i = 0; i < grid.size(); i++) {
        %>
        <tr>
            <th>
                <a href="EnderecoController?sel=<%= grid.get(i).get(0)%>">Selecionar</a>             
            </th>
            <th><%= grid.get(i).get(1)%></th>
            <th>R$<%= grid.get(i).get(2)%></th>
            <th><%= grid.get(i).get(3)%></th>
            <th><%= grid.get(i).get(4)%></th>
            <th><%= grid.get(i).get(5)%></th>
        </tr>
        <%
            }
        %>
    </tbody>
    <tfoot>
        <tr>
            <td colspan="6">
                <a href="EnderecoController?sel">Incluir</a>
            </td>
        </tr>
    </tfoot>
</table>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>