<%-- 
    Document   : endereco-grid
    Created on : 29/09/2019, 00:45:25
    Author     : HP
--%>
<%@page import="br.uff.models.Address"%>
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

    ArrayList<Address> adresses = null;
    if (request.getAttribute("adresses") != null) {
        adresses = (ArrayList<Address>) request.getAttribute("adresses");
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
            for (int i = 0; i < adresses.size(); i++) {
        %>
        <tr class="end-data-container">
            <% Address a = adresses.get(i); %>
            <td class="end-data">
                <a class="end-link" href="EnderecoController?sel=<%= adresses.get(i).getId()%>">Editar</a>             
            </td>
            <td class="end-data"><%= adresses.get(i).getName()%></td>
            <td class="end-data"><%= adresses.get(i).getAddress()%></td>
            <td class="end-data"><%= adresses.get(i).getCity()%></td>
            <td class="end-data"><%= adresses.get(i).getState()%></td>
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