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
        <caption>Vendas</caption>
        <thead>
            <tr class="end-title-container">
                <th scope="col" class="end-title">Nº do Pedido</th>
                <th scope="col" class="end-title">Momento da Compra</th>
                <th scope="col" class="end-title">Endereço de Entrega</th>
                <th scope="col" class="end-title">Total</th>
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
                <td colspan="4">
                    <table class="end-grid" style="width: 70%; margin: 0px 15%;">
                        <caption>Produtos da Venda #<%= venda.getId()%></caption>
                        <thead>
                            <tr class="end-title-container">
                                <th scope="col" class="end-title"></th>
                                <th scope="col" class="end-title">Produto</th>
                                <th scope="col" class="end-title">Quantidade</th>
                                <th scope="col" class="end-title">Preço Unitário</th>
                                <th scope="col" class="end-title">Subtotal</th>
                                <th scope="col" class="end-title">Avaliação</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (CarrinhoProdutoDTO produto : venda.getProdutosDoCarrinho()) {
                            %>
                            <tr class="end-data-container">
                                <td class="end-data"><img alt="<%= produto.getNome()%>" src="<%= produto.getImagem()%>"/></td>
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
                </td>
            </tr>
        </tbody>
    </table>
</div>                    

<%
    }
    if (vendas.getDados().size() == 0) {
%>
<h2 class="meus-pedidos">Nenhuma compra foi realizada ainda, corra e faça uma compra para ela aparecer aqui!</h2>
<%
    }
%>
<!-- Páginas -->
<div style="font-size: 30px; text-align: center; margin-top: 20px;">
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