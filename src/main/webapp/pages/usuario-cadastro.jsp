<%-- 
    Document   : conta
    Created on : 28/09/2019, 16:26:16
    Author     : HP
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.uff.loja.core.dtos.RoleDTO"%>
<%@page import="java.util.List"%>
<%@page import="br.uff.loja.core.enums.EPermissaoUsuario"%>
<%@page import="br.uff.loja.core.dtos.UsuarioDTO"%>
<%
    // se n tiver um usuario logado retorna p userController
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("usuario");
        return;
    }

    if (request.getAttribute("usuario") == null) {
        response.sendRedirect("usuario");
        return;
    }

    UsuarioDTO usuario = (UsuarioDTO) request.getAttribute("usuario");

    List<RoleDTO> permissoes = new ArrayList<RoleDTO>();
    if (request.getAttribute("permissoes") != null) {
        permissoes = (List<RoleDTO>) request.getAttribute("permissoes");
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Conta Pessoal"/>
</jsp:include>

<div class="user-cadastro-container">

    <h2>Editar Dados</h2>
    <form method="post" action="usuario">

        <ul class="form-style-1">
            <li>
                <label>Nome Completo </label>
                <input class="field-long" name="name" required type="text" maxlength="255" value="<%= usuario.getNome()%>">
            </li>
            <li>
                <label>E-mail </label>
                <input class="field-long" name="email" required type="email" maxlength="255" value="<%= usuario.getEmail()%>">
            </li>
            <li>
                <label>Senha </label>
                <input class="field-long" name="password" required type="password" maxlength="255" value="<%= usuario.getSenha()%>">
            </li>
            <% if (session.getAttribute("userRole").equals(EPermissaoUsuario.ADM.getId())) { %>
            <li>
                <label>Papel </label>
                <select name="roleId" required>
                    <% for (RoleDTO permissao : permissoes) {%>
                    <option value="<%= permissao.getId()%>" <%= (permissao.getId() == usuario.getPermissaoId() ? "selected" : "")%>><%= permissao.getNome()%></option>
                    <% } %>
                </select>
                <% } else {%>
                <input name="roleId" required type="text" style="display:none;" value="<%= usuario.getPermissaoId()%>">
                <% }%>
            </li>
            <li class="center">
                <button type="submit" name="action" value="unsel">Voltar</button>
                <button type="submit" name="action" value="grava">Gravar</button>
            </li>
        </ul>

    </form>

</div>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>
