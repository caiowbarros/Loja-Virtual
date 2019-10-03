<%-- 
    Document   : produtos
    Created on : 02/10/2019, 00:11:02
    Author     : HP
--%>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Produtos"/>
</jsp:include>
<div>
    <fieldset>
        <legend>filter</legend>
        <fieldset>
            <legend>category</legend>
            <input type="checkbox" id="playstation" name="category" value="playstation"><label for="playstation">playstation</label>
            <input type="checkbox" id="xbox" name="category" value="xbox"><label for="xbox">xbox</label>
            <input type="checkbox" id="wii" name="category" value="wii"><label for="wii">wii</label>
        </fieldset>
        <fieldset>
            <legend>sub-category</legend>
            <input type="checkbox" id="console" name="category" value="console"><label for="calca">Console</label>
            <input type="checkbox" id="jogo" name="category" value="jogo"><label for="jogo">Jogo</label>
            <input type="checkbox" id="acessorio" name="category" value="acessorio"><label for="acessorio">Acessorio</label>
        </fieldset>
        <label for="price">min value</label>
        <input type="range" name="price_min" min="1" max="10" step="1" oninput="display_min.value=value" onchange="display_min.value = value">
        <input type="number" readonly id="display_min" value="0"/>
        <label for="price">max value</label>
        <input type="range" name="price_max" min="1" max="10" step="1" oninput="display_max.value=value" onchange="display_max.value = value">
        <input type="number" readonly id="display_max" value="0"/>
        <input name="name" type="text" placeholder="name" maxlength="255" />
        <input name="description" type="text" placeholder="description" maxlength="255" />
        <button type="submit">filter</button>
    </fieldset>
</div>
<div class="fundo-paisagem1 paralax">
    <section class="lista-produtos">

        <div class="produto">
            <img src="https://www.w3schools.com/w3images/jeans3.jpg" alt="Denim Jeans" class="imagem-produto">
            <p class="titulo-produto">Tailored Jeans</p>
            <p class="preco-produto">$19.99</p>
            <p class="descricao-produto">Some text about the jeans. Super slim and comfy lorem ipsum lorem
                jeansum. Lorem jeamsun denim
                lorem jeansum.</p>
        </div>

    </section>
</div>
<div>
    <button>Primeira Página</button>
    <button>Página Anterior</button>
    <span>1</span>
    <button>Próxima Página</button>
    <button>Última Página</button>
</div>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>