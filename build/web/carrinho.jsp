<%-- 
    Document   : carrinho
    Created on : 13/09/2019, 02:33:53
    Author     : Caio
--%>

<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Carrinho"/>
</jsp:include>
<p><%= request.getAttribute("sessionId")%> | ${sessionId}</p>
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
</form>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>