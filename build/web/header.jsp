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
        <link href="css/minha-conta.css" type="text/css" rel="stylesheet">
        <script src="https://kit.fontawesome.com/24de11fb10.js" crossorigin="anonymous"></script>
    </head>

    <body>
        <header>
            <!-- Logo -->
            <div class="logo-div">
                <a href="./">UFF</a>
            </div>
            <!-- Dropdown -->
            <div class="dropdown-menu">
                <div class="dropdown ps">
                    <button class="dropbtn" onclick="location.href='ProdutosController?categoryId=1';">Playstation</button>
                    <div class="dropdown-content">
                        <a href="ProdutosController?categoryId=5">Consoles</a>
                        <a href="ProdutosController?categoryId=6">Jogos</a>
                        <a href="ProdutosController?categoryId=4">Acessórios</a>
                    </div>
                </div>
                <div class="dropdown">
                    <button class="dropbtn" onclick="location.href='ProdutosController?categoryId=2';">Xbox</button>
                    <div id="feminino" class="dropdown-content">
                        <a href="ProdutosController?categoryId=8">Consoles</a>
                        <a href="ProdutosController?categoryId=9">Jogos</a>
                        <a href="ProdutosController?categoryId=7">Acessórios</a>
                    </div>
                </div>
                <div class="dropdown wii">
                    <button class="dropbtn" onclick="location.href='ProdutosController?categoryId=3';">Wii</button>
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
                        <input name="pesquisa" type="search" placeholder="Buscar..." required>
                    </form>
                </div>
                <!-- Minha Conta -->
                <div class="login-header">
                    <a href="UserController"><img src="img/my-account.png" width="20" style="vertical-align: middle"></a>
                </div>
                <!-- Carrinho -->
                <div class="carrinho-header">
                    <a href="CarrinhoController"><img src="img/cart.png" width="25" style="vertical-align: middle"></a>
                </div>
            </div>
        </header>
        <!-- Pra impedir de ficar elementos em cima do header -->
        <div class="fundo-branco"></div>