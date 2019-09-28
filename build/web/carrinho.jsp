<%-- 
    Document   : carrinho
    Created on : 13/09/2019, 02:33:53
    Author     : Caio
--%>

<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Carrinho"/>
</jsp:include>
<p>${sessionId}</p>
<table class="tabela-carrinho">
    <thead>
        <tr>
            <th class="imagem-produto"><strong>Imagem</strong></th>
            <th class="nome-produto"><strong>Produto</strong></th>
            <th class="quantidade-produto"><strong>Quantidade</strong></th>
            <th class="valor-produto"><strong>Valor unitário</strong></th>
            <th class="total-produto"><strong>Valor Total</strong></th>
            <th class="remove-produto"><strong></strong></th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td class="imagem-produto"><img src="https://static.netshoes.com.br/produtos/camisa-flamengo-i-1920-sn-torcedor-c-patrocinio-adidas-masculina/68/COL-7378-068/COL-7378-068_zoom1.jpg" width="500px"></td>
            <td class="nome-produto">Camisa CR Flamengo 1</td>
            <td class="quantidade-produto">1</td>
            <td class="valor-produto">R$250,00</td>
            <td class="total-produto">R$250,00</td>
            <td class="remove-produto">X</td>
        </tr>
    </tbody>
</table>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>