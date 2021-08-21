<%-- 
    Document   : endereco-grid
    Created on : 29/09/2019, 00:45:25
    Author     : HP
--%>
<%@page import="br.uff.loja.core.dtos.EnderecoDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%
    // se n tiver um usuario logado retorna p controller com redirect p EnderecoController
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("usuario?redirect=endereco");
    }

    List<EnderecoDTO> grid = new ArrayList<EnderecoDTO>();
    if (request.getAttribute("grid") != null) {
        grid = (List<EnderecoDTO>) request.getAttribute("grid");
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Meus Endere?os"/>
</jsp:include>
<table class="end-grid">
    <caption>Endere?os</caption>
    <thead>
        <tr>
            <th scope="row" colspan="5"><h2>Meus Endere?os</h2></th>
        </tr>
        <tr class="end-title-container">
            <td class="end-title">Opera??es</td>
            <td class="end-title">Descri??o do Endere?o</td>
            <td class="end-title">Endere?o</td>
            <td class="end-title">Cidade</td>
            <td class="end-title">Estado</td>
        </tr>
    </thead>
    <tbody>
        <%
            for (EnderecoDTO endereco : grid) {
        %>
        <tr class="end-data-container">
            <td class="end-data">
                <a class="end-link" href="endereco?sel=<%= endereco.getId()%>">Editar</a>             
            </td>
            <td class="end-data"><%= endereco.getNome()%></td>
            <td class="end-data"><%= endereco.getLogradouro()%></td>
            <td class="end-data"><%= endereco.getCidade()%></td>
            <td class="end-data"><%= endereco.getEstado()%></td>
        </tr>
        <%
            }
        %>
    </tbody>
    <tfoot>
        <tr>
            <td class="end-add-new" colspan="5">
                <a class="end-link" href="endereco?sel">Inserir Novo Endere?o</a>
            </td>
        </tr>
    </tfoot>
</table>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>