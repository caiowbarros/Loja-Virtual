# Loja Virtual
UFF - Trabalho feito em grupo para a matéria de Desenvolvimento Web (feito correndo devido ao pouco tempo para o desenvolvimento)

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
1. Configure seu banco de dados mysql no arquivo https://github.com/igor-lisboa/e-store/blob/master/src/main/java/br/uff/dao/MySql.java#L23
2. Restaure o dump .sql no seu banco de dados escolhido presente em https://github.com/igor-lisboa/e-store/blob/master/bd.sql
3. Rode `mvn package` para buildar o projeto
4. Rode `java -jar target/endorsed/webapp-runner.jar --port $PORT target/loja-1.0.war` para testar seu projeto, trocando o `$PORT` pela porta que desejar
5. Acesse http://localhost:`$PORT` e verifique se está funcionando

# Problemas
## Muito If
* devido a pressa e a falta de necessidade de suporte ao longo do tempo optamos por encher os controllers de If`s
 * tratar cada necessidade de forma distinta (modularizando mais nosso projeto) seria o ideal
## Links quebrados
* o banco de dados foi abastecido por @caiowbarros , com isso, as imagens do banner da tela inicial não te leva a lugar algum
## Mvc não está sendo respeitado
* cada models devem ser criados para serem usados para atualizar, extrair e deletar dados do banco de dados
* Atualizações estão sendo feitas direto nos controllers
