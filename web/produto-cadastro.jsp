<%-- 
    Document   : produto-cadastro
    Created on : 02/10/2019, 02:09:04
    Author     : HP
--%>
<%@page import="br.uff.models.Product"%>
<%
    // se n usuario n for adm retorna p ProdutosController
    if (!session.getAttribute("userRole").equals("1")) {
        response.sendRedirect("UserController?redirect=ProdutosController");
    }

    if (session.getAttribute("msg") != null) {
        String msg = session.getAttribute("msg").toString();
        session.setAttribute("msg", null);
        out.println("<script>alert('" + msg + "');</script>");
    }

    Product produto = null;
    if (request.getAttribute("produto") != null) {
        produto = (Product) request.getAttribute("produto");
    } else {
        response.sendRedirect("ProdutoAdmController");
        return;
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
        <form method="post" action="ProdutoAdmController">
            
            <ul class="form-style-1">
                <li>
                    <label>Nome </label>
                    <input value="${produto.getName()}" class="field-long" name="name" required type="text" maxlength="255" />
                </li>
                <li>
                    <label>Preço </label>
                    <input value="${produto.getPrice()}" class="field-long" name="price" required type="number" max="5000" min="0.01" step="0.01" maxlength="255" />
                </li>
                <li>
                    <label>Descrição </label>
                    <textarea class="field-long field-textarea" name="description" required maxlength="255">${produto.getDescription()}</textarea>
                </li>
                <li>
                    <label>Categoria </label><jsp:include page="partials/components/select.jsp">
                        <jsp:param name="nameSelect" value="categoryId"/>
                        <jsp:param name="required" value="1"/>
                        <jsp:param name="consulta" value="SELECT id value,category_name text FROM vw_category ORDER BY 2"/>
                        <jsp:param name="selectedValue" value="${produto.getCategoryId()}"/>
                    </jsp:include>
                </li>
                <li>
                    <label>Imagem </label><input name="img" id="img" accept="image/*" <% if (sel.equals("")) { %>required<% }%> type="file"/>
                    <input value="${produto.getImg()}" style="display:none;" name="src" readonly type="text"/>
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
            <img id="prev"/>
        </div>
        
        <% if (!sel.equals("")) { %>
        <form action="ProdutoAdmController" method="post">
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