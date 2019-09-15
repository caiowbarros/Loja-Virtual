<%-- 
    Document   : template-produto
    Created on : 13/09/2019, 18:51:42
    Author     : Caio
--%>

<jsp:include page="header.jsp">
    <jsp:param name="title" value="Camisa CR Flamengo 1"/>
</jsp:include>

<main class="container">
 
  <!-- Left Column / Product Image -->
  <div class="left-column">
    <img src="https://wqsurf.vteximg.com.br/arquivos/ids/176435-1000-1000/camisa-flamengo-jogo-1-adidas-2019-bs2-58454-1.jpg?v=636987970579800000">
  </div>
 
 
  <!-- Right Column -->
  <div class="right-column">
 
    <!-- Product Description -->
    <div class="product-description">
      <span>Camisetas</span>
      <h1>Camisa CR Flamengo 1</h1>
      <p>As ruas do Rio de Janeiro são pavimentadas com mosaicos coloridos. O design fragmentado desta camisa de futebol foi montado para refletir o estilo único da casa do CR Flamengo. Feita para os fãs, ela coloca o conforto em primeiro lugar com um tecido sedoso que absorve o suor e uma modelagem um pouco mais larga do que a usada pelos jogadores em campo. Um escudo do time se destaca no peito.</p>
    </div>
 
    <!-- Product Configuration -->
    <div class="product-configuration">
 
      <!-- Size Configuration -->
      <div class="tamanho-config">
        <span>Tamanho</span>
 
        <div class="escolha-tamanho">
          <button>P</button>
          <button>M</button>
          <button>G</button>
          <button>GG</button>
        </div>
 
      </div>
    </div>
 
    <!-- Product Pricing -->
    <div class="product-price">
      <span>R$250,00</span>
      <a href="#" class="cart-btn">+ Carrinho</a>
      <!-- Fav button -->
      <input id="toggle-heart" type="checkbox" />
      <label for="toggle-heart">&#x2764;</label>
    </div>
    <!-- Product Rating -->
    <div class="rate">
      <input type="radio" id="star5" name="rate" value="5" />
      <label for="star5" title="text"></label>
      <input type="radio" id="star4" name="rate" value="4" />
      <label for="star4" title="text"></label>
      <input type="radio" id="star3" name="rate" value="3" />
      <label for="star3" title="text"></label>
      <input type="radio" id="star2" name="rate" value="2" />
      <label for="star2" title="text"></label>
      <input type="radio" id="star1" name="rate" value="1" />
      <label for="star1" title="text"></label>
    </div>
  </div>
</main>

<jsp:include page="footer.jsp"></jsp:include>