<%-- 
    Document   : carrinho-confirma
    Created on : 02/10/2019, 11:42:33
    Author     : HP
--%>
<%@page import="java.util.ArrayList"%>
<%
    // se n tiver um usuario logado chama UserController e configura p redirecionar d volta p CarrinhoController
    if (session.getAttribute("userId") == null) {
        session.setAttribute("msg", "Realize seu login para prosseguir.");
        response.sendRedirect("UserController?redirect=CarrinhoController");
    }
    // mostra se tiver msg
    if (session.getAttribute("msg") != null) {
        String msg = session.getAttribute("msg").toString();
        session.setAttribute("msg", null);
        out.println("<script>alert('" + msg + "');</script>");
    }

    ArrayList<ArrayList<String>> enderecos = null;
    if (request.getAttribute("enderecos") != null) {
        enderecos = (ArrayList<ArrayList<String>>) request.getAttribute("enderecos");
    }

    ArrayList<ArrayList<String>> produtos = null;
    if (session.getAttribute("produtos") != null) {
        produtos = (ArrayList<ArrayList<String>>) session.getAttribute("produtos");
    }

    double totalPrice = Double.parseDouble(request.getAttribute("totalPrice").toString());
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Carrinho"/>
</jsp:include>

<main class="confirma-container">

    <div class="left-confirma">
        <form method="post" action="CarrinhoController">
            <h2>Detalhes da entrega</h2>
            <div class="confirma-end">
                <%
                    for (int i = 0; i < enderecos.size(); i++) {
                %>
                <div class="confirma-radio">
                    <div class="radio-container">
                        <input required type="radio" name="end" id="end<%= enderecos.get(i).get(0)%>" value="<%= enderecos.get(i).get(0)%>"><label for="end<%= enderecos.get(i).get(0)%>">&nbsp;<%= enderecos.get(i).get(1)%></label><br>
                    </div>
                    <a href="EnderecoController?sel=<%= enderecos.get(i).get(0)%>">Editar</a>
                </div>
                <%
                    }
                %>
                <div class="confirma-novoend">
                    <a href="EnderecoController?sel">Inserir novo endereço</a>
                </div>
            </div>
                
            <h2>Método de entrega</h2>
            <div class="confirma-frete">
                <div class="confirma-radio">
                    <div class="radio-container">
                        <input type="radio" name="frete" checked>
                        <div class="radio-label">Normal - 5 dias úteis (Frete grátis)</div>
                    </div>
                </div>
            </div>
            <input type="number" style="display:none;" name="totalPrice" value="<%= totalPrice%>"/>
            <button class="confirma-compra" name="action" value="continuaPagamento">Continuar Pagamento</button>
        </form>
    </div>

    <div class="right-confirma">
        <h2>Resumo do pedido</h2>
        <div class="detalhe-carrinho">
            <div class="detalhe-valor">
                <div class="detalhe-item">
                    <label>Subtotal</label>
                    <div class="detalhe-subtotal">R$<%= String.format("%.2f", totalPrice)%></div>
                </div>
                <div class="detalhe-item">
                    <label>Frete</label>
                    <div class="detalhe-frete">Grátis</div>
                </div>
                <div class="detalhe-item">
                    <label>Total</label>
                    <div class="detalhe-total">R$<%= String.format("%.2f", totalPrice)%></div>
                </div>
                <%
                    for (int i = 0; i < produtos.size(); i++) {
                %>
                <div class="detalhe-produto">
                    <div class="detalhe-img">
                        <img src="<%= produtos.get(i).get(1)%>">
                    </div>
                    <div class="detalhe-info">
                        <div class="detalhe-nome">&nbsp;<%= produtos.get(i).get(0)%></div>
                        <div class="detalhe-id">&nbsp;x&nbsp;<%= produtos.get(i).get(3)%>&nbsp;Unidade(s)</div>
                    </div>
                </div>
                <%
                    }
                %>
                <div class="detalhe-voltar">
                    <a href="CarrinhoController">Editar Carrinho</a>
                </div>
            </div>
        </div>
    </div>

</main>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>