<%-- 
    Document   : compras
    Created on : 02/10/2019, 00:50:48
    Author     : HP
--%>

<jsp:include page="header.jsp">
    <jsp:param name="title" value="Produtos"/>
</jsp:include>

<table witdh="100%" border="1">
    <thead>
        <tr>Compra - 1</tr>
        <tr>
            <td>Total</td>
            <td>Momento da Compra</td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>R$300,00</td>
            <td>25/03/2019 15:54</td>
        </tr>
        <tr>
            <td colspan="2">
                <table>
                    <thead>
                        <tr>Endereço de Entrega</tr>
                        <tr>
                            <td>CEP</td>
                            <td>Endereço</td>
                            <td>Cidade</td>
                            <td>Estado</td>
                            <td>País</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>24230-320</td>
                            <td>Av. Alm. Ary Parreiras, 6</td>
                            <td>Niterói</td>
                            <td>RJ</td>
                            <td>BRASIL</td>
                        </tr>
                    </tbody>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <table>
                    <thead>
                        <tr>Produtos da Compra</tr>
                        <tr>
                            <td>Nome</td>
                            <td>Número de Unidades</td>
                            <td>Preço por Unidade</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>GTA V</td>
                            <td>1</td>
                            <td>R$199,99</td>
                        </tr>
                        <tr>
                            <td>Mario Bros</td>
                            <td>1</td>
                            <td>R$10,01</td>
                        </tr>
                        <tr>
                            <td>Wii Sports</td>
                            <td>2</td>
                            <td>R$40,00</td>
                        </tr>
                    </tbody>
                </table>
            </td>
        </tr>
    </tbody>
</table>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>