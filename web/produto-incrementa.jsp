<%-- 
    Document   : produto-incrementa
    Created on : 02/10/2019, 02:09:47
    Author     : HP
--%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Adição de Produtos ao Estoque"/>
</jsp:include>
<fieldset>
    <legend>Adicionar Unidades ao Estoque</legend>
    <input name="amount" min="1" required type="number" placeholder="amount" maxlength="11">
    <button type="submit">Aumentar</button>
</fieldset>
<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>