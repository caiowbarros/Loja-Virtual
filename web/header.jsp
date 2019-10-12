<%-- 
    Document   : header
    Created on : 12/09/2019, 19:35:15
    Author     : Caio
--%>
<!DOCTYPE html>
<html lang="pt-br">

    <head>
        <title>UFF | ${param.title}</title>
        <meta charset="UTF-8">
        <!-- tirando cache p obrigar a pag a recarregar -->
        <meta http-equiv="cache-control" content="no-cache">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/style.css" type="text/css" rel="stylesheet">
        <link href="css/produto.css" type="text/css" rel="stylesheet">
        <link href="css/login.css" type="text/css" rel="stylesheet">
        <link href="css/carrinho.css" type="text/css" rel="stylesheet">
        <link href="css/carrinho-confirma.css" type="text/css" rel="stylesheet">
        <link href="css/produtos.css" type="text/css" rel="stylesheet">
        <link href="css/home.css" type="text/css" rel="stylesheet">
    </head>

    <body>
        <header>
            <!-- Logo -->
            <div class="logo-div">
                <a href="index.jsp">UFF</a>
            </div>
            <!-- Dropdown -->
            <div class="dropdown-menu">
                <div class="dropdown ps">
                    <button class="dropbtn" onclick="location.href='';">Playstation</button>
                    <div class="dropdown-content">
                        <a href="ProdutosController?categoryId=5">Consoles</a>
                        <a href="ProdutosController?categoryId=6">Jogos</a>
                        <a href="ProdutosController?categoryId=4">Acessórios</a>
                    </div>
                </div>
                <div class="dropdown">
                    <button class="dropbtn" onclick="location.href='';">Xbox</button>
                    <div id="feminino" class="dropdown-content">
                        <a href="ProdutosController?categoryId=8">Consoles</a>
                        <a href="ProdutosController?categoryId=9">Jogos</a>
                        <a href="ProdutosController?categoryId=7">Acessórios</a>
                    </div>
                </div>
                <div class="dropdown wii">
                    <button class="dropbtn" onclick="location.href='';">Wii</button>
                    <div id="infantil" class="dropdown-content">
                        <a href="ProdutosController?categoryId=11">Consoles</a>
                        <a href="ProdutosController?categoryId=12">Jogos</a>
                        <a href="ProdutosController?categoryId=10">Acessórios</a>
                    </div>
                </div>
            </div>
            <div class="canto-header">
                <!-- Pesquisa -->
                <div class="consulta-header">
                    <form id="pesquisa" method="POST" action="ProdutosController">
                        <input type="search" placeholder="Search" required>
                    </form>
                </div>
                <!-- Minha Conta -->
                <div class="login-header">
                    <a href="UserController"><img src="https://cdn1.iconfinder.com/data/icons/freeline/32/account_friend_human_man_member_person_profile_user_users-512.png" width="20" style="vertical-align: middle"></a>
                </div>
                <!-- Carrinho -->
                <div class="carrinho-header">
                    <a href="CarrinhoController"><img src="data:image/png;base64,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" width="25" style="vertical-align: middle"></a>
                </div>
            </div>
        </header>
        <!-- Pra impedir de ficar elementos em cima do header -->
        <div class="fundo-branco"></div>