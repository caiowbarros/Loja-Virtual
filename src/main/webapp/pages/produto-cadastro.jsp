<%-- 
    Document   : produto-cadastro
    Created on : 02/10/2019, 02:09:04
    Author     : HP
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.uff.loja.core.dtos.CategoriaDTO"%>
<%@page import="java.util.List"%>
<%@page import="br.uff.loja.core.dtos.ProdutoDTO"%>
<%@page import="br.uff.loja.core.enums.EPermissaoUsuario"%>
<%
    // se n usuario n for adm retorna p ProdutosController
    if (!session.getAttribute("userRole").equals(EPermissaoUsuario.ADM.getId())) {
        response.sendRedirect("usuario?redirect=produtos");
    }

    ProdutoDTO produto = new ProdutoDTO();
    if (request.getAttribute("produto") != null) {
        produto = (ProdutoDTO) request.getAttribute("produto");
    } else {
        response.sendRedirect("produto-adm");
        return;
    }

    List<CategoriaDTO> categorias = new ArrayList<CategoriaDTO>();
    if (request.getAttribute("categorias") != null) {
        categorias = (List<CategoriaDTO>) request.getAttribute("categorias");
    }

    String sel = request.getParameter("sel");
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Cadastro de Produtos"/>
</jsp:include>

<div class="produto-main-container">

    <div class="produto-left-container">

        <h2>Produto</h2>
        <form method="post" action="produto-adm">

            <ul class="form-style-1">
                <li>
                    <label>Nome </label>
                    <input value="<%= produto.getNome()%>" class="field-long" name="name" required type="text" maxlength="255" />
                </li>
                <li>
                    <label>Preço </label>
                    <input value="<%= produto.getPreco()%>" class="field-long" name="price" required type="number" max="5000" min="0.01" step="0.01" maxlength="255" />
                </li>
                <li>
                    <label>Descrição </label>
                    <textarea class="field-long field-textarea" name="description" required maxlength="255"><%= produto.getDescricao()%></textarea>
                </li>
                <li>
                    <label>Categoria </label>
                    <select required name="categoryId">
                        <% for (CategoriaDTO categoria : categorias) {%>
                        <option <%= (produto.getCategoriaId() == categoria.getId() ? "selected" : "")%> value="<%= categoria.getId()%>"><%= categoria.getNome()%></option>
                        <% } %>
                    </select>
                </li>
                <li>
                    <label>Imagem </label><input name="img" id="img" accept="image/*" <% if (sel.equals("")) { %>required<% }%> type="file"/>
                    <input value="<%= produto.getImagem()%>" style="display:none;" name="src" readonly type="text"/>
                </li>
                <li class="center">
                    <button name="action" formnovalidate value="unsel">Voltar</button>
                    <button type="submit" name="action" value="grava">Gravar</button>
                    <% if (!sel.equals("")) { %>
                    <button type="submit" name="action" value="del" formnovalidate onclick="return confirm('Tem certeza que deseja excluir esse produto?');false;">Apagar</button>
                    <% }%>
                </li>
            </ul>
        </form>
    </div>

    <div class="produto-right-container">

        <h2>Prévia da Imagem</h2>
        <div class="previa-img">
            <img alt="Imagem do Produto" id="prev"/>
        </div>

        <% if (!sel.equals("")) { %>
        <form action="produto-adm" method="post">
            <ul class="form-style-1" style="margin-left: 31px;">
                <li>
                    <label>Quantidade a entrar no estoque </label>
                    <input name="quantity" min="1" required type="number" maxlength="11" class="field-divided">
                </li>
                <li class="center">
                    <button type="submit" name="action" value="estoqueInsere">Inserir</button>
                </li>
            </ul>
        </form>
        <% }%>
    </div>
</div>
<script>
    window.onload = function () {
        // qnd a pag esta carregada set prev
        setPrevBySrcInput();
    }

    const toBase64 = file => new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => resolve(reader.result);
            reader.onerror = error => reject(error);
        });

    async function fileToBase64(seletor) {
        const file = document.querySelector(seletor).files[0];
        const src = await toBase64(file);
        return src;
    }

    document.querySelector('#img').addEventListener('change', function () {
        var maxAllowedSize = 1 * 1024 * 1024;
        if (this.files[0].size > maxAllowedSize) {
            alert("Arquivo grande demais, selecione outro menor!");
            this.value = "";
        } else {
            atualizaImg();
        }

    }, false);

    async function atualizaImg() {
        const src = await fileToBase64('#img');
        setValue('[name=src]', src);
        setPrevBySrcInput();
    }

    function setValue(seletor, value) {
        document.querySelector(seletor).value = String(value);
    }

    function setPrevBySrcInput() {
        document.querySelector('#prev').src = document.querySelector('[name=src]').value;
    }
</script>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>