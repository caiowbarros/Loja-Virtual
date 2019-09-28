<%-- 
    Document   : conta
    Created on : 28/09/2019, 16:26:16
    Author     : HP
--%>

<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Conta Pessoal"/>
</jsp:include>
<form method="post" action="">
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
        <button type="submit">Gravar</button>
    </fieldset>
</form>
<a href="./enderecosController">Seus Endereços</a>
<a href="./favoritosController">Seus Produtos Favoritos</a>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>
