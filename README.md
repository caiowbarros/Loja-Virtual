# Loja Virtual
UFF - Trabalho feito em grupo para a matéria de Desenvolvimento Web e melhorado para matéria de Qualidade e Testes

# Requisitos do Professor
* Fazer uma loja virtual completa com carrinho de compras usando APENAS as tecnologias estudadas durante o curso (HTML, CSS, Javascript, JSP, Servlets, MVC, MySQL, XAMPP, Netbeans 8.2)
* Com as características
  * Usuários e gerenciamento de suas contas
  * Cadastro de produtos (um usuário administrador pode inserir, alterar ou remover produtos)
  * Navegação de produtos (5 produtos por página com paginação)
  * Carrinho de compras com usuário requerido apenas no fechamento da compra
  * Ranking ou estrelas para os produtos
  * Avaliações dos usuários desses produtos
  * Lista de produtos favoritos por usuário

# Como rodar o projeto...
## Docker
1. Rode `cp .env.example .env && docker-compose up --build -d`
2. Aguarde o container startar
3. Acesse http://localhost:8181 e verifique se está funcionando
## Padrão
1. Configure seu arquivo `.env`, pode copiar o .env.example para usar como template `cp .env.example .env`
2. Restaure o dump .sql em um banco de dados mysql 5.6, o dump pode ser encontrado em https://github.com/caiowbarros/Loja-Virtual/blob/master/database/dump/dump.sql
3. Rode `mvn package` para buildar o projeto
4. Rode `java -jar target/dependency/webapp-runner.jar --port $PORT target/loja-1.0.1.war` para testar seu projeto, trocando o `$PORT` pela porta que você tiver definido no .env na variável PORT
5. Acesse http://localhost:`$PORT` e verifique se está funcionando