<%-- 
    Document   : produto-grid
    Created on : 02/10/2019, 02:09:29
    Author     : HP
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.uff.loja.core.enums.EPermissaoUsuario"%>
<%@page import="br.uff.loja.core.dtos.UsuarioDTO"%>
<%@page import="java.util.List"%>
<%
    // se n tiver um usuario logado retorna p userController
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("usuario");
    }

    List<UsuarioDTO> grid = new ArrayList<UsuarioDTO>();
    if (request.getAttribute("usuarios") != null) {
        grid = (List<UsuarioDTO>) request.getAttribute("usuarios");
    }
%>
<!-- Header -->
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Minha Conta"/>
</jsp:include>

<h2 class="meus-pedidos">Minha Conta</h2>
<div class="main-user-container">

    <div class="left-user-container">
        <div class="left-row">
            <p style="font-weight: bold;"><%= (session.getAttribute("userRole").equals(EPermissaoUsuario.ADM.getId()) ? "Lista de Cadastros" : "Meus Dados")%></p>
        </div>
        <%
            for (UsuarioDTO usuario : grid) {
        %>
        <div class="left-row">
            <p style="padding-bottom: 5px;"><%= usuario.getEmail()%></p>
            <div class="editar-row">
                <p><%= usuario.getNome()%></p>
                <a href="usuario?sel=<%= usuario.getId()%>">Editar</a>
            </div>
        </div>
        <%
            }
        %>
    </div>

    <div class="right-user-container">
        <div class="right-row">
            <div class="user-info">
                <div class="user-info-middle" onclick="location.href = 'endereco';">
                    <em class="fas fa-map-marker-alt"></em>
                    <p>Lista de Endereços</p>
                </div>
            </div>
            <div class="user-info">
                <div class="user-info-middle" onclick="location.href = 'produtos?esp=favoritos';">
                    <em class="fas fa-heart"></em>
                    <p>Favoritos</p>
                </div>
            </div>
            <div class="user-info">
                <div class="user-info-middle" onclick="location.href = 'compra?historico';">
                    <em class="fas fa-shopping-bag"></em>
                    <p>Histórico de Pedidos</p>
                </div>
            </div>
        </div>
        <div class="right-row">
            <!-- SE ROLE_ID DO USUARIO FOR ADM ENTAO MOSTRA CADASTRO DE PRODUTOS -->
            <% if (session.getAttribute("userRole").equals(EPermissaoUsuario.ADM.getId())) { %>
            <div class="user-info">
                <div class="user-info-middle" onclick="location.href = 'produto-adm';">
                    <em class="fas fa-barcode"></em>
                    <p>Cadastro de Produtos</p>
                </div>
            </div>
            <%
                }
            %>
            <div class="user-info">
                <div class="user-info-middle" onclick="Logout()">
                    <em class="fas fa-sign-out-alt"></em>
                    <p>
                    <form method="post" action="usuario">
                        <button onclick="return confirm('Tem certeza que deseja fazer o LOGOUT?');false;" type="submit" id="logout-btn" name="action" value="logout" formnovalidate>Logout</button>
                    </form>
                    </p>
                </div>
                <script>
                    function Logout() {
                        document.getElementById("logout-btn").click();
                    }
                </script>
            </div>
        </div>
    </div>

</div>

<!-- Footer -->
<jsp:include page="footer.jsp"></jsp:include>