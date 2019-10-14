<%-- 
    Document   : conta
    Created on : 28/09/2019, 16:26:16
    Author     : HP
--%>
<%@page import="br.uff.models.User"%>
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

    User usuario = null;
    if (request.getAttribute("usuario") != null) {
        usuario = (User) request.getAttribute("usuario");
    } else {
        response.sendRedirect("UserController");
        return;
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Conta Pessoal"/>
</jsp:include>
<a href="UserController">Voltar</a>
<form method="post" action="UserController">
    <fieldset>
        <legend>Usuário</legend>
        <input name="name" required type="text" maxlength="255" value="${usuario.getName()}">
        <input name="email" required type="email" maxlength="255" value="${usuario.getEmail()}">
        <input name="password" required type="password" maxlength="255" value="${usuario.getPassword()}">
        <% if (session.getAttribute("userRole").equals("1")) { %>
        <jsp:include page="partials/components/select.jsp">
            <jsp:param name="nameSelect" value="roleId"/>
            <jsp:param name="required" value="1"/>
            <jsp:param name="consulta" value="SELECT id value,name text FROM roles ORDER BY 2"/>
            <jsp:param name="selectedValue" value="${usuario.getRoleId()}"/>
        </jsp:include>
        <% } else { %>
        <input name="roleId" required type="text" style="display:none;" value="${usuario.getRoleId()}">
        <% }%>
        <button type="submit" name="action" value="grava">Gravar</button>
    </fieldset>
</form>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>
