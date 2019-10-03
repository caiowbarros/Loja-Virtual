<%-- 
    Document   : paypal
    Created on : 02/10/2019, 02:31:48
    Author     : HP
--%>

<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="P�gina de Pagamento"/>
</jsp:include>

<div id="paypal-button"></div>

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
                                total: "1.69",
                                currency: "BRL"
                            },
                            description: "Total: R$1.69 (Dep�sito de R$1 na carteira de igor + taxa da paypal)",
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
                window.location.href = "?paypal_pag";
                return false;
            });
        },
        onCancel: function (data, actions) {
            window.location.href = "?paypal_cancelado";
            return false;
        },
        onError: function (err) {
            console.log(err);
            window.location.href = "?paypal_erro";
            return false;
        }
    },
            "#paypal-button");


</script>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>