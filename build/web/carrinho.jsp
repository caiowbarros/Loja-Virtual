<%-- 
    Document   : carrinho
    Created on : 13/09/2019, 02:33:53
    Author     : Caio
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.ArrayList"%>
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

    double totalPrice = 0.00;

    ArrayList<ArrayList<String>> itens = null;
    if (request.getAttribute("itens") != null) {
        itens = (ArrayList<ArrayList<String>>) request.getAttribute("itens");
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
<%
    if (itens.size() < 1) {
%>
<div class="cart-product">
    <h2>Seu carrinho está vazio por enquanto!</h2>
</div>
<%
} else {
    for (int i = 0; i < itens.size(); i++) {
        double totalProduto = 0.00;
        if (itens.get(i).get(4) != null) {
            if (itens.get(i).get(5) != null) {
                totalProduto = Double.parseDouble(itens.get(i).get(4)) * Integer.valueOf(itens.get(i).get(5));
            }
            totalPrice += totalProduto;
        }
%>
<!-- Produto no carrinho -->
<div class="cart-product">
    <div class="cart-img">
        <img src="<%= itens.get(i).get(3)%>">
    </div>
    <div class="cart-details">
        <div class="cart-name"><%= itens.get(i).get(1)%></div>
        <p class="cart-desc"><%= itens.get(i).get(2)%></p>
    </div>
    <div class="cart-price">R$<%= itens.get(i).get(4)%></div>
    <div class="cart-qnt">
        <form action="CarrinhoController?produtoId=<%= itens.get(i).get(0)%>&action=mudaQtd" method="post" id="mudaQtdForm<%= itens.get(i).get(0)%>">
            <input style="width:50px;" type="number" min="1" max="<%= itens.get(i).get(6)%>" value="<%= itens.get(i).get(5)%>" name="qtdProduto" onchange="mudaQtd('mudaQtdForm<%= itens.get(i).get(0)%>')"/>
        </form>
    </div>
    <div class="cart-remove">
        <form action="CarrinhoController?produtoId=<%= itens.get(i).get(0)%>" method="post">
            <button class="cart-remove-btn" name="action" value="removeProduto">Remover</button>
        </form>
    </div>
    <div class="cart-total">R$<%= String.format("%.2f", totalProduto)%></div>
</div>
<%
        }
    }
%>

<!-- Checkout -->
<div class="total-finalize">
    <%
        if (itens.size() > 0) {
    %>
    <div class="total-item">
        <p>Frete grátis para todo Brasil!</p>
        <label>Subtotal</label>
        <div class="total-subtotal">R$<%= String.format("%.2f", totalPrice)%></div>
    </div>
    <div class="total-item">
        <label>Frete</label>
        <div class="total-ship">Grátis</div>
    </div>
    <div class="total-item">
        <label>Total</label>
        <div class="total-total">R$<%= String.format("%.2f", totalPrice)%></div>
    </div>
    <%
        }
    %>
    <div class="total-item">
        <form method="post" action="CarrinhoController">
            <input type="number" style="display:none;" name="totalPrice" value="<%= totalPrice%>"/>
            <button class="total-keep" name="action" value="continuaCompra" type="submit">Continuar Comprando</button>
            <%
                if (itens.size() > 0) {
            %>
            <button class="total-checkout" name="action" value="finalizaCompra" type="submit">Finalizar Compra</button>
            <%
                }
            %>
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