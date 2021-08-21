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
                <a class="end-link" href="endereco?sel">Inserir Novo Endereço</a>
            </td>
        </tr>
    </tfoot>
</table>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>