<%-- 
    Document   : paypal
    Created on : 02/10/2019, 02:31:48
    Author     : HP
--%>
<%@page import="java.util.ArrayList"%>
<%
    // se n tiver um usuario logado chama UserController e configura p redirecionar d volta p CompraController
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("UserController?redirect=CarrinhoController");
    }

    ArrayList<ArrayList<String>> produtos = null;
    if (session.getAttribute("produtos") != null) {
        produtos = (ArrayList<ArrayList<String>>) session.getAttribute("produtos");
    }

    ArrayList<String> endereco = null;
    if (request.getAttribute("endereco") != null) {
        endereco = (ArrayList<String>) request.getAttribute("endereco");
    }

    // mostra se tiver msg
    if (session.getAttribute("msg") != null) {
        String msg = session.getAttribute("msg").toString();
        session.setAttribute("msg", null);
        out.println("<script>alert('" + msg + "');</script>");
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
            <form action="CompraController">
                
                <ul class="form-style-1">
                    <li>
                        <label>Nome do Titular do Cartão </label>
                        <input class="field-long" name="name" required type="text" maxlength="255">
                    </li>
                    <li>
                        <label>Número do Cartão </label>
                        <input class="field-long" name="number" required type="text" maxlength="16">
                    </li>
                    <li>
                        <label>Data de Expiração </label>
                        <select class="field-half">
                            <option value="01">Janeiro</option>
                            <option value="02">Fevereiro</option>
                            <option value="03">Março</option>
                            <option value="04">Abril</option>
                            <option value="05">Maio</option>
                            <option value="06">Junho</option>
                            <option value="07">Julho</option>
                            <option value="08">Agosto</option>
                            <option value="09">Setembro</option>
                            <option value="10">Outubro</option>
                            <option value="11">Novembro</option>
                            <option value="12">Dezembro</option>
                        </select>
                        <select class="field-half" style="float: right;">
                            <option value="19">2019</option>
                            <option value="20">2020</option>
                            <option value="21">2021</option>
                            <option value="21">2022</option>
                            <option value="21">2023</option>
                            <option value="21">2024</option>
                            <option value="21">2025</option>
                            <option value="21">2026</option>
                            <option value="21">2027</option>
                            <option value="21">2028</option>
                            <option value="21">2029</option>
                        </select>
                        <!--<input class="field-half" name="month" required type="text" placeholder="Mês" maxlength="2">
                        <input class="field-half" name="year" required type="text" placeholder="Ano" maxlength="2">-->
                    </li>
                    <li>
                        <label>CVV </label>
                        <input class="field-half" name="cvv" required type="number" maxlength="3">
                        <img class="credit-card-img" src="https://i.imgur.com/fc0e1ow.png">
                    </li>
                    <li class="center">
                        <div>
                            <button onclick="return confirm('Deseja realizar essa compra?');false;" name="action" value="pagamentoOk" style="font-size: 15px; padding: 10px 24px;">Efetuar Compra</button>
                        </div>
                    </li>
                </ul>
                    
            </form>
        </div>
        
        <div class="confirma-forma-pag">
            <ul class="form-style-1">
                <li class="center">
                    <p>OU</p>
                </li>
                <li>
                    <div id="paypal-button"></div>
                </li>
                <li style="text-align: -webkit-center;">
                    <fieldset>
                        <legend><img src="https://logodownload.org/wp-content/uploads/2014/10/paypal-logo-3.png"></legend>
                        <img class="credit-card-img" src="https://i.imgur.com/fc0e1ow.png">
                    </fieldset>
                </li>
                <li>
                    <div class="paypal-sub">Pagamento 100% seguro</div>
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
                window.location.href = "CompraController?action=pagamentoOk";
                return false;
            });
        },
        onCancel: function (data, actions) {
            window.location.href = "CompraController?action=pagamentoErr";
            return false;
        },
        onError: function (err) {
            console.log(err);
            window.location.href = "CompraController?action=pagamentoErr";
            return false;
        }
    },
            "#paypal-button");


</script>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>