<%-- 
    Document   : carrinho-confirma
    Created on : 02/10/2019, 11:42:33
    Author     : HP
--%>
<%
    // se n tiver um usuario logado chama UserController e configura p redirecionar d volta p CarrinhoController
    if (session.getAttribute("userId") == null) {
        //response.sendRedirect("UserController?redirect=CarrinhoController");
    }
    // mostra msg se existir
    if (request.getAttribute("msg") != null) {
        out.println("<script>alert('" + request.getAttribute("msg") + "');</script>");
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Carrinho"/>
</jsp:include>

<!--
Aqui tem que ter:
-um radio contendo os endereços
-produtos escolhidos, qtd e preco
-preco total
-opcao p ir p pag p editar enderecos
-botao p ir p pag de produtos p continuar comprando
-botao p voltar p carrinho p remover produtos ou mudar qtd dos produtos
-->

<main class="confirma-container">
    
    <div class="left-confirma">
        <h2>Detalhes da entrega</h2>
        <div class="confirma-end">
            <div class="confirma-radio">
                <div class="radio-container">
                    <input type="radio" name="end"> Casa<br>
                </div>
                <a href="">Editar</a>
            </div>
            <div class="confirma-radio">
                <div class="radio-container">
                    <input type="radio" name="end"> Trabalho<br>
                </div>
                <a href="">Editar</a>
            </div>
            <div class="confirma-novoend">
                <a href="">Inserir novo endereço</a>
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
        <button class="confirma-compra">Continuar Pagamento</button>
    </div>
    
    <div class="right-confirma">
        <h2>Resumo do pedido</h2>
        <div class="detalhe-carrinho">
            <div class="detalhe-valor">
                <div class="detalhe-item">
                    <label>Subtotal</label>
                    <div class="detalhe-subtotal">R$250,00</div>
                </div>
                <div class="detalhe-item">
                    <label>Frete</label>
                    <div class="detalhe-frete">Grátis</div>
                </div>
                <div class="detalhe-item">
                    <label>Total</label>
                    <div class="detalhe-total">R$250,00</div>
                </div>
                <div class="detalhe-produto">
                    <div class="detalhe-img"><img src="https://images-americanas.b2w.io/produtos/01/00/img/471961/8/471961879_1GG.jpg"></div>
                    <div class="detalhe-info">
                        <div class="detalhe-nome">Fifa 20 - ps4</div>
                        <div class="detalhe-id">69420</div>
                    </div>
                </div>
                <div class="detalhe-voltar">
                    <a href="carrinho.jsp">Editar Carrinho</a>
                </div>
            </div>
        </div>
    </div>
    
</main>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>