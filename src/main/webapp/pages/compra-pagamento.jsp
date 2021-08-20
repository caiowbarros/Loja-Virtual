<%-- 
    Document   : paypal
    Created on : 02/10/2019, 02:31:48
    Author     : HP
--%>
<%@page import="br.uff.loja.infrastructure.shared.Helper"%>
<%
    // se n tiver um usuario logado chama UserController e configura p redirecionar d volta p CompraController
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("usuario?redirect=carrinho");
    }

    ArrayList<ArrayList<String>> produtos = null;
    if (session.getAttribute("produtos") != null) {
        produtos = (ArrayList<ArrayList<String>>) session.getAttribute("produtos");
    }

    ArrayList<String> endereco = null;
    if (request.getAttribute("endereco") != null) {
        endereco = (ArrayList<String>) request.getAttribute("endereco");
    }

    double totalPrice = Double.parseDouble(session.getAttribute("totalPrice").toString());

%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Página de Pagamento"/>
</jsp:include>

<main class="confirma-container">

    <div class="left-confirma">

        <h2>Método de pagamento</h2>

        <div class="confirma-forma-pag">
            <ul class="form-style-1">
                <li>
                    <div id="paypal-button"></div>
                </li>
                <li style="text-align: -webkit-center;">
                    <fieldset>
                        <legend><img src="img/paypal.png"></legend>
                        <img class="credit-card-img" src="img/credit-card.png">
                    </fieldset>
                </li>
                <li>
                    <div class="paypal-sub">Pagamento 100% seguro</div>
                </li>
                <li class="center">
                    <button style="display:none" onclick="return confirm('Deseja realizar essa compra?');false;" name="action" value="pagamentoOk">Comprar</button>
                </li>
            </ul>
        </div>

    </div>

    <div class="right-confirma">
        <h2>Resumo do pedido</h2>
        <div class="detalhe-carrinho">
            <div class="detalhe-valor">
                <div class="detalhe-item">
                    <label>Subtotal</label>
                    <div class="detalhe-subtotal"><%= new Helper().tryParseMoneyFormat(totalPrice)%></div>
                </div>
                <div class="detalhe-item">
                    <label>Frete</label>
                    <div class="detalhe-frete">Grátis</div>
                </div>
                <div class="detalhe-item">
                    <label>Total</label>
                    <div class="detalhe-total"><%= new Helper().tryParseMoneyFormat(totalPrice)%></div>
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
                    <a href="carrinho">Editar Carrinho</a>
                </div>
            </div>
        </div>
        <div class="detalhe-carrinho">
            <div class="detalhe-valor">
                <div class="detalhe-endereco">Endereço de Entrega escolhido</div>
                <%
                    if (endereco.size() > 0) {
                %>
                <div class="detalhe-endereco-info">    
                    <p><%= endereco.get(0)%></p>
                    <p><%= endereco.get(1)%></p>
                    <p><%= endereco.get(2)%></p>
                    <p><%= endereco.get(3)%>&nbsp;-&nbsp;<%= endereco.get(4)%></p>
                    <p><%= endereco.get(5)%></p>
                </div>
                <%
                    }
                %>
            </div>
        </div>
        <div class="detalhe-carrinho">
            <div class="detalhe-valor">
                <div class="detalhe-endereco">Método de entrega</div>
                <div class="detalhe-entrega">ENTREGA CONVENCIONAL - Frete grátis para todo o Brasil (Previsão de entrega em 5 dias úteis)</div>
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
                                sandbox: "Af4ArltRj8MriojisGcUAhAgTTkSlnxi382830HLHEdiUO5mhlNbCPSIrQ8rQs9mxSrY1aCC2N2q4-8x"
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
                                                    total: "<%= totalPrice%>",
                                                    currency: "BRL"
                                                },
                                                description: "Compras",
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
                                    window.location.href = "compra?action=pagamentoOk";
                                    return false;
                                });
                            },
                            onCancel: function (data, actions) {
                                window.location.href = "compra?action=pagamentoErr";
                                return false;
                            },
                            onError: function (err) {
                                console.log(err);
                                window.location.href = "compra?action=pagamentoErr";
                                return false;
                            }
                        },
                                "#paypal-button");


</script>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>