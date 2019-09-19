<%-- 
    Document   : login
    Created on : 19/09/2019, 00:23:53
    Author     : Caio
--%>

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
      <form method="POST" action="">
        
        <div class="group">      
          <input type="text" required>
          <span class="highlight"></span>
          <span class="bar"></span>
          <label>E-mail</label>
        </div>
          
        <div class="group">      
          <input type="password" required>
          <span class="highlight"></span>
          <span class="bar"></span>
          <label>Senha</label>
        </div>

        <div class="forgot-pswrd">
          <input type="checkbox">
          <label>Lembre-me</label>
        </div>
        
        <div class="login-btn">
          <button type="submit">Entrar</button>
        </div>

      </form>
    </div>
  </div>

  <!-- Coluna da direita (cadastro) -->
  <div class="register-column">
    <h2>Criar Conta</h2>
    <div class="sub-container">
      <form method="POST" action="">
        
        <div class="group">      
          <input type="text" required>
          <span class="highlight"></span>
          <span class="bar"></span>
          <label>Nome Completo</label>
        </div>
        
        <div class="group">      
          <input type="text" required>
          <span class="highlight"></span>
          <span class="bar"></span>
          <label>E-mail</label>
        </div>

        <div class="group">      
          <input type="password" required>
          <span class="highlight"></span>
          <span class="bar"></span>
          <label>Senha</label>
        </div>

        <div class="group">
          <input type="password" required>
          <span class="highlight"></span>
          <span class="bar"></span>
          <label>Confirma Senha</label>
        </div>

        <div class="login-btn">
          <button type="submit">Criar Conta</button>
        </div>
        
      </form>
    </div>
  </div>

</main>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>
