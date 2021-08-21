<%-- 
    Document   : produto-grid
    Created on : 02/10/2019, 02:09:29
    Author     : HP
--%>
<%@page import="br.uff.loja.infrastructure.shared.Helper"%>
<%@page import="java.util.List"%>
<%@page import="br.uff.loja.core.dtos.ProdutoDTO"%>
<%@page import="br.uff.loja.core.enums.EPermissaoUsuario"%>
<%@page import="java.util.ArrayList"%>
<%
    // se n usuario n for adm retorna p ProdutosController
    if (!session.getAttribute("userRole").equals(EPermissaoUsuario.ADM.getId())) {
        response.sendRedirect("usuario");
    }
    List<ProdutoDTO> grid = new ArrayList<ProdutoDTO>();
    if (request.getAttribute("grid") != null) {
        grid = (List<ProdutoDTO>) request.getAttribute("grid");
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Grid de Produtos"/>
</jsp:include>

<table class="product-grid">
    <caption>Produtos</caption>
    <thead>
        <tr>
            <th scope="row" colspan="5"><h2>Produtos</h2></th>
        </tr>
        <tr class="product-grid-title">
            <td>Operações</td>
            <td>Nome</td>
            <td>Preço</td>
            <td>Categoria</td>
            <td>Quantidade no Estoque</td>
        </tr>
    </thead>
    <tbody>
        <%
            for (ProdutoDTO produto : grid) {
        %>
        <tr class="product-data">
            <td>
                <a href="produto-adm?sel=<%= produto.getId()%>">Editar</a>             
            </td>
            <td><%= produto.getNome()%></td>
            <td><%= new Helper().tryParseMoneyFormat(produto.getPreco())%></td>
            <td><%= produto.getCategoria()%></td>
            <td><%= produto.getQuantidade()%></td>
        </tr>
        <%
            }
        %>
    </tbody>
    <tfoot>
        <tr class="product-data-add">
            <td colspan="5">
                <a class="end-link" href="produto-adm?sel">Inserir Novo Produto</a>
            </td>
        </tr>
    </tfoot>
</table>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>