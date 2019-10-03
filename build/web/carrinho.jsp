<%-- 
    Document   : carrinho
    Created on : 13/09/2019, 02:33:53
    Author     : Caio
--%>

<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Carrinho"/>
</jsp:include>
<!-- <p><%= request.getAttribute("sessionId")%> | ${sessionId}</p>
<hr>
<div style="display: flex;">
    <div style="align-items: flex-start">
        <img src="https://images-submarino.b2w.io/produtos/01/00/item/120739/9/120739985_1GG.jpg" width="150" height="150">
    </div>
    <div style="align-items:center;width: 100%">
        <p>Far Cry 3</p>
        <p>R$ 399,00</p>
        <form method="post" action="CarrinhoController?produtoId=1">
            <p>Qtd:&nbsp;<input type="number" name="qtd" value="1"><button type="submit" value="mudaQtd" name="action">Mudar Qtd</button></p>
        </form>
    </div>
    <div style="align-items:flex-end">
        <form>
            <button type="submit" style="height: 100%">Tirar do Carrinho</button>
        </form>
    </div>
</div>
<hr>
<form method="post" action="CarrinhoController">
    <button name="action" type="submit" value="finaliza">Finaliza Compra</button>
    <button name="action" type="submit" value="continua">Continuar Comprando</button>
</form> -->

<!-- T�tulo da p�gina -->
<h2 class="cart-title">Carrinho</h2>

<!-- Coluna de labels -->
<div class="cart-column">
    <label class="cart-img"></div>
    <label class="cart-details">Produto</label>
    <label class="cart-price">Pre�o</label>
    <label class="cart-qnt">Quantidade</label>
    <label class="cart-remove">Remover</label>
    <label class="cart-total">Total</label>
</div>

<!-- Produto no carrinho -->
<div class="cart-product">
    <div class="cart-img"><img src="https://images-americanas.b2w.io/produtos/01/00/img/471961/8/471961879_1GG.jpg"></div>
    <div class="cart-details">
        <div class="cart-name">FIFA 20 - PS4</div>
        <p class="cart-desc">Ea Sports Fifa 20 para PlayStation 4, Xbox One e Pc traz os dois lados do Maior Jogo do Mundo - o prest�gio do n�vel profissional e uma nova experi�ncia de aut�ntico futebol de rua com Ea Sports Volta. Fifa 20 inova no jogo inteiro, o sistema de Intelig�ncia de Futebol libera uma plataforma sem precedentes para o realismo da jogabilidade, Fifa Ultimate Team? oferece mais maneiras de montar o seu time dos sonhos, e Ea Sports Volta leva o jogo de volta para as ruas, com uma forma aut�ntica de futebol com poucos jogadores.</p>
    </div>
    <div class="cart-price">R$250,00</div>
    <div class="cart-qnt">
        <input type="number" value="1" min="1">
    </div>
    <div class="cart-remove">
        <button class="cart-remove-btn">Remover</button>
    </div>
    <div class="cart-total">R$250,00</div>
</div>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>