<%-- 
    Document   : carrinho
    Created on : 13/09/2019, 02:33:53
    Author     : Caio
--%>

<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Carrinho"/>
</jsp:include>
<%
    // mostra se tiver msg
    if (session.getAttribute("msg") != null) {
        String msg = session.getAttribute("msg").toString();
        session.setAttribute("msg", null);
        out.println("<script>alert('" + msg + "');</script>");
    }
%>
<!-- Título da página -->
<h1 class="cart-title">Carrinho</h1>

<!-- Coluna de labels -->
<div class="cart-column">
    <label class="cart-img transparent">Imagem</label>
    <label class="cart-details transparent">Produto</label>
    <label class="cart-price">Preço</label>
    <label class="cart-qnt">Quantidade</label>
    <label class="cart-remove transparent">Remover</label>
    <label class="cart-total">Total</label>
</div>

<!-- Produto no carrinho -->
<div class="cart-product">
    <div class="cart-img">
        <img src="https://images-americanas.b2w.io/produtos/01/00/img/471961/8/471961879_1GG.jpg">
    </div>
    <div class="cart-details">
        <div class="cart-name">FIFA 20 - PS4</div>
        <p class="cart-desc">Ea Sports Fifa 20 para PlayStation 4, Xbox One e Pc traz os dois lados do Maior Jogo do Mundo - o prestígio do nível profissional e uma nova experiência de autêntico futebol de rua com Ea Sports Volta. Fifa 20 inova no jogo inteiro, o sistema de Inteligência de Futebol libera uma plataforma sem precedentes para o realismo da jogabilidade, Fifa Ultimate Team? oferece mais maneiras de montar o seu time dos sonhos, e Ea Sports Volta leva o jogo de volta para as ruas, com uma forma autêntica de futebol com poucos jogadores.</p>
    </div>
    <div class="cart-price">R$250,00</div>
    <div class="cart-qnt">
        <form action="CarrinhoController?produtoId=1&action=mudaQtd" method="post" id="mudaQtdForm1">
            <input style="width:50px;" type="number" min="1" max="10" name="qtdProduto" onchange="mudaQtd('mudaQtdForm1')"/>
        </form>
    </div>
    <div class="cart-remove">
        <form action="CarrinhoController?produtoId=1" method="post">
            <button class="cart-remove-btn" name="action" value="removeProduto">Remover</button>
        </form>
    </div>
    <div class="cart-total">R$250,00</div>
</div>

<!-- Checkout -->
<div class="total-finalize">
    <div class="total-item">
        <p>Frete grátis para todo Brasil!</p>
        <label>Subtotal</label>
        <div class="total-subtotal">R$250,00</div>
    </div>
    <div class="total-item">
        <label>Frete</label>
        <div class="total-ship">Grátis</div>
    </div>
    <div class="total-item">
        <label>Total</label>
        <div class="total-total">R$250,00</div>
    </div>
    <div class="total-item">
        <form method="post" action="CarrinhoController">
            <button class="total-keep" name="action" value="continuaCompra" type="submit">Continuar Comprando</button>
            <button class="total-checkout" name="action" value="finalizaCompra" type="submit">Finalizar Compra</button>
        </form>
    </div>
</div>
<script>
    function mudaQtd(formId) {
        var form = document.getElementById(formId);
        var isValidForm = form.checkValidity();
        if (isValidForm) {
            form.submit();
        } else {
            alert("Por favor insira uma quantidade válida! A quantidade atual está acima da quantidade do produto em estoque ou abaixo da quantidade mínima.");
            return false;
        }
    }
</script>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>