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

# Como testar
## Padrão
1. Configure sua configuração de banco de dados mysql, criando o arquivo `.env`
2. Restaure o dump .sql no seu banco de dados escolhido presente em https://github.com/caiowbarros/Loja-Virtual/blob/master/dump/bd.sql
3. Rode `mvn package` para buildar o projeto
4. Rode `java -jar target/endorsed/webapp-runner.jar --port $PORT target/loja-1.0-SNAPSHOT.war` para testar seu projeto, trocando o `$PORT` pela porta que desejar
5. Acesse http://localhost:`$PORT` e verifique se está funcionando
## Docker
1. Rode `docker-compose up --build -d`
2. Aguarde o container startar
3. Acesse http://localhost:8181 e verifique se está funcionando