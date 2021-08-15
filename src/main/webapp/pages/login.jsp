<%-- 
    Document   : login
    Created on : 19/09/2019, 00:23:53
    Author     : Caio
--%>
<%
    // se tiver um usuario logado retorna p controller
    if (session.getAttribute("userId") != null) {
        response.sendRedirect("usuario");
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Login"/>
</jsp:include>

<!-- Imagem de fundo -->
<div class="login-background"></div>

<main class="login-container">
    <!-- Coluna da esquerda (login) -->
    <div class="login-column">
        <h2>Login</h2>
        <div class="sub-container">
            <form method="POST" action="usuario">

                <input name="redirect" style="display:none;" value="<%= request.getAttribute("redirect")%>">

                <div class="group">      
                    <input name="email" type="email" required value="${cookie['loginEmail'].getValue()}">
                    <span class="highlight"></span>
                    <span class="bar"></span>
                    <label>E-mail</label>
                </div>

                <div class="group">
                    <input name="password" type="password" required value="${cookie['loginPassword'].getValue()}">
                    <span class="highlight"></span>
                    <span class="bar"></span>
                    <label>Senha</label>
                </div>

                <div class="forgot-pswrd">
                    <input value="1" name="remember" id="remember" type="checkbox">
                    <label for="remember">Lembre-me</label>
                </div>

                <div class="login-btn">
                    <button type="submit" name="action" value="login">Entrar</button>
                </div>

            </form>
        </div>
    </div>

    <!-- Coluna da direita (cadastro) -->
    <div class="register-column">
        <h2>Criar Conta</h2>
        <div class="sub-container">
            <form method="POST" action="usuario">

                <input name="redirect" style="display:none;" value="<%= request.getAttribute("redirect")%>">

                <div class="group">      
                    <input name="nome" type="text" required>
                    <span class="highlight"></span>
                    <span class="bar"></span>
                    <label>Nome Completo</label>
                </div>

                <div class="group">      
                    <input name="email" type="email" required>
                    <span class="highlight"></span>
                    <span class="bar"></span>
                    <label>E-mail</label>
                </div>

                <div class="group">      
                    <input name="senha" id="criaSENHA" type="password" required>
                    <span class="highlight"></span>
                    <span class="bar"></span>
                    <label>Senha</label>
                </div>

                <div class="group">
                    <input id="criaCONFSENHA" type="password" required>
                    <span class="highlight"></span>
                    <span class="bar"></span>
                    <label>Confirma Senha</label>
                </div>
                <script>
                    function verifica_senha() {
                        var senha = document.getElementById("criaSENHA");
                        var conf = document.getElementById("criaCONFSENHA");
                        if (senha.value != conf.value) {
                            alert("senha nao bate com conferencia");
                            return false;
                        } else {
                            return true;
                        }
                    }
                </script>
                <div class="login-btn">
                    <button onclick="return verifica_senha();" name="action" value="insere" type="submit">Criar Conta</button>
                </div>

            </form>
        </div>
    </div>

</main>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>
