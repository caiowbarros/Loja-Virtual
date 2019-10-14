<%-- 
    Document   : paypal
    Created on : 02/10/2019, 02:31:48
    Author     : HP
--%>
<%
    // se n tiver um usuario logado chama UserController e configura p redirecionar d volta p CompraController
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("UserController?redirect=CompraController");
    }
    // mostra msg se existir
    if (request.getAttribute("msg") != null) {
        out.println("<script>alert('" + request.getAttribute("msg") + "');</script>");
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Página de Pagamento"/>
</jsp:include>

<main class="confirma-container">
    
    <div class="left-confirma">
        <h2>Método de pagamento</h2>
        <div class="confirma-end">
            <div id="paypal-button"></div>
        </div>
        
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
        <div class="detalhe-carrinho">
            <div class="detalhe-valor">
                <div class="detalhe-endereco">endereço de entrega</div>
                <div class="detalhe-endereco-info">    
                    <p>Caio Wey Barros</p>
                    <p>Rua Xingu, 236, apt: 206</p>
                    <p>22760-070</p>
                    <p>Rio de Janeiro - RJ</p>
                    <p>Brasil</p>
                </div>
                <div class="detalhe-voltar">
                    <a href="carrinho-confirma.jsp">Editar Endereço</a>
                </div>
            </div>
        </div>
        <div class="detalhe-carrinho">
            <div class="detalhe-valor">
                <div class="detalhe-endereco">Método de entrega</div>
                <div class="detalhe-entrega">ENTREGA CONVENCIONAL - Frete grátis para todo o Brasil (Previsão de entrega em 5 dias úteis)</div>
                <div class="detalhe-voltar">
                    <a href="carrinho-confirma.jsp">Editar Entrega</a>
                </div>
            </div>
        </div>
    </div>
    
</main>

<script src="https://www.paypalobjects.com/api/checkout.js"></script>
<script>
    //https://github.com/paypal/paypal-checkout-components/blob/master/docs/implement-checkout.md
    var btn = paypal.Button.render({
        env: "sandbox",
        locale: "pt_BR",
        client: {
            sandbox: "Af4ArltRj8MriojisGcUAhAgTTkSlnxi382830HLHEdiUO5mhlNbCPSIrQ8rQs9mxSrY1aCC2N2q4-8x",
            production: "AeoSYJCtv2igh68hnvUOg7y6j3TGLEjrD7h03LNHLoRfs5ljGcisdA8ph-j_AcUVaTEP1voA0FJ5iuxG"
        },
        commit: true,
        style: {
            color: "black",
            size: "responsive",
            shape: "rect",
            label: "pay",
            tagline: "false"
        },
        payment: function (data, actions) {
            return actions.payment.create({
                payment: {
                    transactions: [{
                            amount: {
                                total: "12.02",
                                currency: "BRL"
                            },
                            description: "Aquisição de produtos por",
                            payment_options: {
                                allowed_payment_method: "IMMEDIATE_PAY",
                            }
                        }]
                }
            });
        },
        onAuthorize: function (data, actions) {
            return actions.payment.execute().then(function (payment) {
                console.log("Pagamento OK");
                console.log(actions);
                window.location.href = "CompraController?paypal_pag";
                return false;
            });
        },
        onCancel: function (data, actions) {
            window.location.href = "CompraController?paypal_cancelado";
            return false;
        },
        onError: function (err) {
            console.log(err);
            window.location.href = "CompraController?paypal_erro";
            return false;
        }
    },
            "#paypal-button");


</script>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>