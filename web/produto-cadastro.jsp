<%-- 
    Document   : produto-cadastro
    Created on : 02/10/2019, 02:09:04
    Author     : HP
--%>
<%
    // se n usuario n for adm retorna p ProdutosController
    if (!session.getAttribute("userRole").equals("1")) {
        response.sendRedirect("UserController?redirect=ProdutosController");
    }
    if (request.getAttribute("msg") != null) {
        out.println("<script>alert('" + request.getAttribute("msg") + "');</script>");
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Cadastro de Produtos"/>
</jsp:include>
<a href="ProdutoController?unsel">Voltar</a>
<form method="post" action="ProdutoController">
    <fieldset>
        <legend>product</legend>
        <input name="name" required type="text" placeholder="name" maxlength="255" />
        <input name="price" required type="number" min="0.01" step="0.01" placeholder="price" maxlength="255" />
        <input name="description" required type="text" placeholder="description" maxlength="255" />
        <input name="category_id" required type="text" maxlength="255" />
        <input name="created_at" type="datetime" required/>
        <input name="img" id="img" accept="image/*" required type="file"/>
        <input style="display:none;" name="src" required readonly type="text"/>
        <button type="submit" name="action" value="grava">Gravar</button>
    </fieldset>
</form>
<fieldset>
    <legend>Previa da Imagem</legend>
    <img style="width: 100px;height:auto" id="prev"/>
</fieldset>
<a href="ProdutoController?qtdEstoque">Add unidades no estoque</a>
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

        atualizaImg();

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