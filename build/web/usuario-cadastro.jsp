<%-- 
    Document   : conta
    Created on : 28/09/2019, 16:26:16
    Author     : HP
--%>
<%
    // se n tiver um usuario logado retorna p controller
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("UserController");
    }
    if (request.getAttribute("msg") != null) {
        out.println("<script>alert('" + request.getAttribute("msg") + "');</script>");
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Conta Pessoal"/>
</jsp:include>
<form method="post" action="UserController">
    <fieldset>
        <legend>user</legend>
        <div class="group">      
            <input name="name" required type="text" maxlength="255">
            <span class="highlight"></span>
            <span class="bar"></span>
            <label>Nome Completo</label>
        </div>
        <div class="group">      
            <input name="email" required type="email" maxlength="255">
            <span class="highlight"></span>
            <span class="bar"></span>
            <label>E-mail</label>
        </div>
        <div class="group">
            <input name="password" required type="password" maxlength="255">
            <span class="highlight"></span>
            <span class="bar"></span>
            <label>Senha</label>
        </div>
        <!--SE FOR ADM MOSTRA O CAMPO ABAIXO-->
        <div style="display:none;">
            <input id="role_id_adm" name="role_id" required type="radio" value="1"><label for="role_id_adm">ADM</label><br>
            <input id="role_id_cliente" name="role_id" required type="radio" value="2"><label for="role_id_cliente">Cliente</label>
        </div>
        <button type="submit" name="action" value="grava">Gravar</button>
        <button type="submit" name="action" value="logout" formnovalidate>LOGOUT</button>
    </fieldset>
</form>
<a href="EnderecoController">Seus Endereços</a>
<a href="ProdutosController?fav">Seus Produtos Favoritos</a>
<a href="compras.jsp">Suas Compras</a>
<!-- SE ROLE_ID DO USUARIO FOR ADM ENTAO MOSTRA ITEM ABAIXO -->
<fieldset>
    <legend>Opções Administrativas</legend>
    <a href="ProdutoController">Cadastro de Produtos</a>
</fieldset>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>
