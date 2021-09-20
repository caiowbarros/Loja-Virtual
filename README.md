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
  * Para acompanhar os logs de criação do container maven, execute o seguinte comando: `docker logs --tail 50 -f e_store_mvn` 
2. Aguarde o container startar
3. Acesse http://localhost:8181 e verifique se está funcionando
## Padrão
1. Configure seu arquivo `.env`, pode copiar o .env.example para usar como template `cp .env.example .env`
2. Restaure o dump .sql em um banco de dados mysql 5.6, o dump pode ser encontrado em https://github.com/caiowbarros/Loja-Virtual/blob/master/database/dump/dump.sql
3. Rode `rm -R target -f && cp .env.local .env && mvn package` para buildar o projeto
4. Rode `java -jar target/dependency/webapp-runner.jar --port $PORT target/loja-1.0.1.war` para testar seu projeto, trocando o `$PORT` pela porta que você tiver definido no .env na variável PORT
5. Acesse http://localhost:`$PORT` e verifique se está funcionando
# Api
## Documentação
* [CLIQUE AQUI PARA VER A DOCUMENTAÇÃO DA API NO POSTMAN](https://documenter.getpostman.com/view/13081554/U16bvUBT)
* [CLIQUE AQUI PARA IR ATÉ O ARQUIVO ONDE ENCONTRA-SE A COLLECTION DO POSTMAN PARA SER IMPORTADA NO SEU POSTMAN](https://github.com/caiowbarros/Loja-Virtual/blob/master/Loja.postman_collection.json)

# PiTest
* O PIT é um sistema de teste de mutação de última geração, fornecendo cobertura de teste padrão ouro para Java e jvm.
* O que é teste de mutação?
  * O teste de mutação é conceitualmente muito simples.
  * Falhas (ou mutações) são propagadas automaticamente em seu código e, em seguida, seus testes são executados. Se seus testes falharem, a mutação é morta; se seus testes passarem, a mutação sobreviveu.
  * A qualidade dos seus testes pode ser avaliada pela porcentagem de mutações eliminadas.
  * Colocando de outra forma - o PIT executa seus testes de unidade em versões modificadas automaticamente do código do seu aplicativo. Quando o código do aplicativo muda, ele deve produzir resultados diferentes e fazer com que os testes de unidade falhem. Se um teste de unidade não falhar nessa situação, isso pode indicar um problema com o conjunto de testes.
  * A cobertura de teste tradicional (ou seja, linha, instrução, ramificação, etc.) mede apenas qual código é executado por seus testes. Ele não verifica se seus testes são realmente capazes de detectar falhas no código executado. Portanto, ele só é capaz de identificar códigos que definitivamente não foram testados.
  * Os exemplos mais extremos do problema são testes sem asserções. Felizmente, eles são incomuns na maioria das bases de código. Muito mais comum é o código que é apenas parcialmente testado por seu conjunto. Uma suíte que testa apenas parcialmente o código ainda pode executar todas as suas ramificações.
  * Como ele é realmente capaz de detectar se cada afirmação foi testada de forma significativa, o teste de mutação é o padrão ouro contra o qual todos os outros tipos de cobertura são medidos.
## Como gerar o relatório
* Para rodar o PiTest, execute o seguinte comando após já ter buildado o projeto:
```sh
mvn org.pitest:pitest-maven:mutationCoverage
```