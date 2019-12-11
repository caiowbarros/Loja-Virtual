# e-store
UFF - Trabalho de Desenvolvimento Web (feito correndo devido ao pouco tempo para o desenvolvimento)

# Requisito
## Banco de Dados MySql
* caso as credenciais estejam corretas, rode o script bd.sql na raiz do projeto para carregar as tabelas, procedures e triggers do nosso projeto
### Com a configuração abaixo
* nome
  * test
* usuario para acesso
  * root
* senha para acesso
  * [STRING VAZIA]
* host
  * 127.0.0.1
### Ou altere as credenciais de acesso ao Banco de Dados
* localizada no arquivo
  * /src/java/br/uff/dao/MySql.java

# Problemas
## Muito If
* devido a pressa e a falta de necessidade de suporte ao longo do tempo optamos por encher os controllers de If`s ao invés de tratar cada necessidade de forma distinta a fim de modularizar mais nosso projeto
## links quebrados
* o banco de dados foi abastecido por @caiowbarros , com isso, as imagens do banner da tela inicial não te leva a lugar algum
