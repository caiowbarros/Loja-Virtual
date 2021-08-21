<%-- 
    Document   : compras
    Created on : 02/10/2019, 00:50:48
    Author     : HP
--%>
<%@page import="br.uff.loja.infrastructure.shared.Helper"%>
<%@page import="br.uff.loja.core.dtos.CarrinhoProdutoDTO"%>
<%@page import="br.uff.loja.core.dtos.PaginateDTO"%>
<%@page import="java.util.List"%>
<%@page import="br.uff.loja.core.dtos.VendaDTO"%>
<%@page import="java.util.ArrayList"%>
<%
    // se n tiver um usuario logado chama UserController e configura p redirecionar d volta p CompraController
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("usuario?redirect=compra");
    }

    PaginateDTO<List<VendaDTO>> vendas = new PaginateDTO<List<VendaDTO>>();
    if (request.getAttribute("vendas") != null) {
        vendas = (PaginateDTO<List<VendaDTO>>)request.getAttribute("vendas");
    }
%>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Meus Pedidos"/>
</jsp:include>

<h2 class="meus-pedidos">Meus Pedidos</h2>
<%
    for (VendaDTO venda : vendas.getDados()) {
%>

<div class="compras-container">
    <table class="end-grid">
        <thead>
            <tr class="end-title-container">
                <td class="end-title">N� do Pedido</td>
                <td class="end-title">Momento da Compra</td>
                <td class="end-title">Endere�o de Entrega</td>
                <td class="end-title">Total</td>
            </tr>
        </thead>
        <tbody>
            <tr class="end-data-container">
                <td class="end-data">#<%= venda.getId()%></td>
                <td class="end-data"><%= venda.getCriadoEm()%></td>
                <td class="end-data">
                    <%= venda.getEndereco().getLogradouro()%> - <%= venda.getEndereco().getCidade()%> - <%= venda.getEndereco().getEstado()%> - CEP: <%= venda.getEndereco().getCep()%>
                </td>
                <td class="end-data"><%= new Helper().tryParseMoneyFormat(venda.getPrecoTotal())%></td>
            </tr>
            <tr>
        <table class="end-grid" style="width: 70%; margin: 0px 15%;">
            <thead>
                <tr class="end-title-container">
                    <td class="end-title"></td>
                    <td class="end-title">Produto</td>
                    <td class="end-title">Quantidade</td>
                    <td class="end-title">Pre�o Unit�rio</td>
                    <td class="end-title">Subtotal</td>
                    <td class="end-title">Avalia��o</td>
                </tr>
            </thead>
            <tbody>
                <%
                    for (CarrinhoProdutoDTO produto : venda.getProdutosDoCarrinho()) {
                %>
                <tr class="end-data-container">
                    <td class="end-data"><img src="<%= produto.getImagem()%>"/></td>
                    <td class="end-data"><a class="end-link" href="produto?produtoId=<%= produto.getProdutoId()%>"><%= produto.getNome()%></a></td>
                    <td class="end-data"><%= produto.getQuantidade()%></td>
                    <td class="end-data"><%= new Helper().tryParseMoneyFormat(produto.getPreco())%></td>
                    <td class="end-data"><%= new Helper().tryParseMoneyFormat(produto.getPrecoTotal())%></td>
                    <td class="end-data"><a class="end-link" href="avaliacao?produtoId=<%= produto.getProdutoId()%>">Avaliar Produto</a></td>
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
    if (vendas.getDados().size() == 0) {
%>
<h2 class="meus-pedidos">Nenhuma compra foi realizada ainda, corra e fa�a uma compra para ela aparecer aqui!</h2>
<%
    }
%>
<!-- P�ginas -->
<div style="font-size: 50px; text-align: center;">
    <% if (vendas.getPaginaAtual() > 1) {%>
    <a href="compra?historico&paginaAtual=<%= vendas.getPaginaAtual()-1%>">&#8249;</a>
    <% }%>
    <span><%= vendas.getPaginaAtual()%></span>
    <% if (vendas.getPaginaAtual() < vendas.getUltimaPagina()) { %>
    <a href="compra?historico&paginaAtual=<%= vendas.getPaginaAtual()+1%>">&#8250;</a>
    <% }%>
</div>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>