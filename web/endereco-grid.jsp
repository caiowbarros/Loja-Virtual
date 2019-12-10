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
    <jsp:param name="title" value="Meus Endereços"/>
</jsp:include>
<table class="end-grid" cellspacing="10">
    <thead>
        <tr>
            <th colspan="5"><h2>Meus Endereços</h2></th>
        </tr>
        <tr class="end-title-container">
            <td class="end-title">Operações</td>
            <td class="end-title">Descrição do Endereço</td>
            <td class="end-title">Endereço</td>
            <td class="end-title">Cidade</td>
            <td class="end-title">Estado</td>
        </tr>
    </thead>
    <tbody>
        <%
            for (int i = 0; i < grid.size(); i++) {
        %>
        <tr class="end-data-container">
            <td class="end-data">
                <a class="end-link" href="EnderecoController?sel=<%= grid.get(i).get(0)%>">Editar</a>             
            </td>
            <td class="end-data"><%= grid.get(i).get(1)%></td>
            <td class="end-data"><%= grid.get(i).get(2)%></td>
            <td class="end-data"><%= grid.get(i).get(3)%></td>
            <td class="end-data"><%= grid.get(i).get(4)%></td>
        </tr>
        <%
            }
        %>
    </tbody>
    <tfoot>
        <tr>
            <td class="end-add-new" colspan="5">
                <a class="end-link" href="EnderecoController?sel">Inserir Novo Endereço</a>
            </td>
        </tr>
    </tfoot>
</table>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>