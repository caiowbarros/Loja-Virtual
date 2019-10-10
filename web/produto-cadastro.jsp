<%-- 
    Document   : produto-cadastro
    Created on : 02/10/2019, 02:09:04
    Author     : HP
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="br.uff.dao.MySql"%>
<%@page import="br.uff.dao.Components"%>
<%
    // se n usuario n for adm retorna p ProdutosController
    if (!session.getAttribute("userRole").equals("1")) {
        response.sendRedirect("UserController?redirect=ProdutosController");
    }
    // campos
    String name = "";
    String price = "";
    String description = "";
    String categoryId = "";
    String img = "";
    String quantity = "";

    if (session.getAttribute("produtoId") != null) {
        String produtoId = session.getAttribute("produtoId").toString();
        MySql db = null;
        try {
            db = new MySql("test", "root", "");
            String[] bind = {produtoId};
            ResultSet ret = db.dbCarrega("SELECT * FROM products WHERE id=?", bind);
            if (ret.next()) {
                name = ret.getString("name");
                price = ret.getString("price");
                description = ret.getString("description");
                categoryId = ret.getString("category_id");
                img = ret.getString("img");
                quantity = ret.getString("quantity");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            request.setAttribute("msg", ex.getMessage());
        } finally {
            db.destroyDb();
        }
    }

    if (request.getAttribute("msg") != null) {
        out.println("<script>alert('" + request.getAttribute("msg") + "');</script>");
    }

    Components comp = new Components();
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Cadastro de Produtos"/>
</jsp:include>
<a href="ProdutoController?unsel">Voltar</a>
<form method="post" action="ProdutoController">
    <fieldset>
        <legend>product</legend>
        <input value="<%= name%>" name="name" required type="text" placeholder="name" maxlength="255"/>
        <input value="<%= price%>" name="price" required type="number" min="0.01" step="0.01" placeholder="price" maxlength="255" />
        <input value="<%= description%>" name="description" required type="text" placeholder="description" maxlength="255" />
        <%= comp.mostraSelect("categoryId", true, "SELECT c.id as `value`, c.category_name as text FROM vw_category c", null, categoryId)%>
        <input name="img" id="img" accept="image/*" required type="file"/>
        <input value="<%= img%>" style="display:none;" name="src" required readonly type="text"/>
        <input style="display:none;" name="quantity" required readonly type="text" value="0"/>
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