<%-- 
    Document   : produto-cadastro
    Created on : 02/10/2019, 02:09:04
    Author     : HP
--%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Cadastro de Produtos"/>
</jsp:include>
<fieldset>
    <legend>product</legend>
    <input name="name" required type="text" placeholder="name" maxlength="255" />
    <input name="price" required type="number" min="0.01" step="0.01" placeholder="price" maxlength="255" />
    <input name="description" required type="text" placeholder="description" maxlength="255" />
    <input name="category_id" required type="text" maxlength="255" />
    <input name="created_at" type="datetime" required/>
    <input name="img" required type="file"/>
    <button type="submit">save</button>
</fieldset>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>