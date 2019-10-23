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
    <jsp:param name="title" value="Meus Pedidos"/>
</jsp:include>

<%
    for (int i = 0; i < vendas.size(); i++) {
%>

<div class="compras-container">
    <table class="end-grid">
        <thead>
            <tr class="end-title-container">
                <td class="end-title">N° do Pedido</td>
                <td class="end-title">Momento da Compra</td>
                <td class="end-title">Endereço de Entrega</td>
                <td class="end-title">Total</td>
            </tr>
        </thead>
        <tbody>
            <tr class="end-data-container">
                <td class="end-data">#<%= vendas.get(i).get(0)%></td>
                <td class="end-data"><%= vendas.get(i).get(4)%></td>
                <td class="end-data">
                    <%
                        ArrayList endereco = (ArrayList) vendas.get(i).get(2);
                    %>
                    <%= endereco.get(1)%> - <%= endereco.get(2)%> - <%= endereco.get(3)%> - CEP: <%= endereco.get(0)%>
                </td>
                <td class="end-data">R$<%= vendas.get(i).get(3)%></td>
            </tr>
            <tr>
                <table class="end-grid" style="width: 70%; margin: 0px 15%;">
                    <thead>
                        <tr class="end-title-container">
                            <td class="end-title"></td>
                            <td class="end-title">Produto</td>
                            <td class="end-title">Quantidade</td>
                            <td class="end-title">Preço Unitário</td>
                            <td class="end-title">Subtotal</td>
                            <td class="end-title">Avaliação</td>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            ArrayList<ArrayList> produtos = (ArrayList<ArrayList>) vendas.get(i).get(1);
                            for (int z = 0; z < produtos.size(); z++) {
                        %>
                        <tr class="end-data-container">
                            <td class="end-data">!IMAGEM DO PRODUTO!</td>
                            <td class="end-data"><a class="end-link" href="ProdutoController?produtoId=<%= produtos.get(z).get(0)%>"><%= produtos.get(z).get(1)%></a></td>
                            <td class="end-data"><%= produtos.get(z).get(3)%></td>
                            <td class="end-data">R$<%= produtos.get(z).get(2)%></td>
                            <td class="end-data">R$<%= produtos.get(z).get(4)%></td>
                            <td class="end-data"><a class="end-link" href="AvaliacaoController?produtoId=<%= produtos.get(z).get(0)%>">Avaliar Produto</a></td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </tr>
        </tbody>
    </table>
</div>                    

<%
    }
    if (vendas.size() == 0) {
%>
<h2>Nenhuma compra foi realizada ainda, corra e faça uma compra para ela aparecer aqui!</h2>
<%
    }
%>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>