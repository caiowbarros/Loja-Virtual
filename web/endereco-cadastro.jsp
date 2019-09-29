<%-- 
    Document   : endereco
    Created on : 28/09/2019, 23:42:18
    Author     : HP
--%>
<%
    // se n tiver um usuario logado retorna p controller
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("UserController?redirect=EnderecoController");
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Cadastro de Endereços"/>
</jsp:include>
<form action="EnderecoController">
    <fieldset>
        <legend>Endereço</legend>
        <input name="zipcode" required type="number" placeholder="zipcode" maxlength="11" />
        <input name="name" required type="text" placeholder="name" maxlength="255" />
        <input name="address" required type="text" placeholder="address" maxlength="255" />
        <input name="city" required type="text" placeholder="city" maxlength="255" />
        <input name="state" required type="text" placeholder="state" maxlength="255" />
        <input name="country" required type="text" placeholder="country" readonly value="Brasil" maxlength="255" />
        <button name="action" value="grava" type="submit">Salvar</button>
    </fieldset>
</form>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>