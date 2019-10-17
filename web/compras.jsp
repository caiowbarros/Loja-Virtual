<%-- 
    Document   : compras
    Created on : 02/10/2019, 00:50:48
    Author     : HP
--%>
<%@page import="java.util.ArrayList"%>
<%
    // se n tiver um usuario logado chama UserController e configura p redirecionar d volta p CompraController
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("UserController?redirect=CompraController");
    }
    // mostra se tiver msg
    if (session.getAttribute("msg") != null) {
        String msg = session.getAttribute("msg").toString();
        session.setAttribute("msg", null);
        out.println("<script>alert('" + msg + "');</script>");
    }

    ArrayList<ArrayList> vendas = null;
    if (request.getAttribute("vendas") != null) {
        vendas = (ArrayList<ArrayList>) request.getAttribute("vendas");
    }
%>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Produtos"/>
</jsp:include>
<%
    for (int i = 0; i < vendas.size(); i++) {
%>
<table witdh="100%" border="1">
    <thead>
        <tr>Compra - <%= vendas.get(i).get(0)%></tr>
        <tr>
            <td>Total</td>
            <td>Momento da Compra</td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>R$<%= vendas.get(i).get(3)%></td>
            <td><%= vendas.get(i).get(4)%></td>
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
                            <%
                                ArrayList endereco = (ArrayList) vendas.get(i).get(2);
                            %>
                            <td><%= endereco.get(0)%></td>
                            <td><%= endereco.get(1)%></td>
                            <td><%= endereco.get(2)%></td>
                            <td><%= endereco.get(3)%></td>
                            <td><%= endereco.get(4)%></td>
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
                            <td>Operações</td>
                            <td>Nome</td>
                            <td>Número de Unidades</td>
                            <td>Preço por Unidade</td>
                            <td>Preço total do Produto</td>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            ArrayList<ArrayList> produtos = (ArrayList<ArrayList>) vendas.get(i).get(1);
                            for (int z = 0; z < produtos.size(); z++) {
                        %>
                        <tr>
                            <td><a href="AvaliacaoController?produtoId=<%= produtos.get(z).get(0)%>">Avaliar Produto</a></td>
                            <td><a href="ProdutoController?produtoId=<%= produtos.get(z).get(0)%>"><%= produtos.get(z).get(1)%></a></td>
                            <td><%= produtos.get(z).get(3)%></td>
                            <td>R$<%= produtos.get(z).get(2)%></td>
                            <td>R$<%= produtos.get(z).get(4)%></td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </td>
        </tr>
    </tbody>
</table>
<hr>
<%
    }
    if (vendas.size() == 0) {
%>
<h2>Nenhuma compra foi realizada ainda, corra faça uma compra para ela aparecer aqui!</h2>
<%
    }
%>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>