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
    <div class="cart-img"><img src="https://images-americanas.b2w.io/produtos/01/00/img/471961/8/471961879_1GG.jpg"></div>
    <div class="cart-details">
        <div class="cart-name">FIFA 20 - PS4</div>
        <p class="cart-desc">Ea Sports Fifa 20 para PlayStation 4, Xbox One e Pc traz os dois lados do Maior Jogo do Mundo - o prestígio do nível profissional e uma nova experiência de autêntico futebol de rua com Ea Sports Volta. Fifa 20 inova no jogo inteiro, o sistema de Inteligência de Futebol libera uma plataforma sem precedentes para o realismo da jogabilidade, Fifa Ultimate Team? oferece mais maneiras de montar o seu time dos sonhos, e Ea Sports Volta leva o jogo de volta para as ruas, com uma forma autêntica de futebol com poucos jogadores.</p>
    </div>
    <div class="cart-price">R$250,00</div>
    <div class="cart-qnt">
        <select>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
        </select>
    </div>
    <div class="cart-remove">
        <button class="cart-remove-btn">Remover</button>
    </div>
    <div class="cart-total">R$250,00</div>
</div>

<!-- Produto no carrinho -->
<div class="cart-product">
    <div class="cart-img"><img src="https://images-americanas.b2w.io/produtos/01/00/img/471961/8/471961879_1GG.jpg"></div>
    <div class="cart-details">
        <div class="cart-name">FIFA 20 - PS4</div>
        <p class="cart-desc">Ea Sports Fifa 20 para PlayStation 4, Xbox One e Pc traz os dois lados do Maior Jogo do Mundo - o prestígio do nível profissional e uma nova experiência de autêntico futebol de rua com Ea Sports Volta. Fifa 20 inova no jogo inteiro, o sistema de Inteligência de Futebol libera uma plataforma sem precedentes para o realismo da jogabilidade, Fifa Ultimate Team? oferece mais maneiras de montar o seu time dos sonhos, e Ea Sports Volta leva o jogo de volta para as ruas, com uma forma autêntica de futebol com poucos jogadores.</p>
    </div>
    <div class="cart-price">R$250,00</div>
    <div class="cart-qnt">
        <select>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
        </select>
    </div>
    <div class="cart-remove">
        <button class="cart-remove-btn">Remover</button>
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
        <button class="total-keep">Continuar Comprando</button>
        <button class="total-checkout" onclick="location.href='carrinho-confirma.jsp';">Finalizar Compra</button>
    </div>
</div>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>