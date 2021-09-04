# language: pt

Funcionalidade: Testar a recuperacao de um carrinho
  O sistema deve receber o id do carrinho, o id do usuario e o ip
  o sistema deve retornar um carrinho ativo

  Esquema do Cenario: Testar passar dados aleatorios
    Dado sistema pega as variaveis <carrinho>, <usuario> e <ip> pra recuperar o carrinho
    Quando usuario solicita o carrinho
    Entao recebo o carrinho criado ou recuperado

    Exemplos:
      | carrinho | usuario | ip    |
      | 543      | 2       | 22222 |
      | 0        | 2       | 0     |
      | 1        | 2       | 3     |