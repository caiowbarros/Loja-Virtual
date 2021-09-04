# language: pt

Funcionalidade: Testar a inclusao de produtos
  O sistema deve receber as informacoes do produto a ser incluido
  o sistema cadastrar devidamente o produto e inserir estoque desse produto

  Esquema do Cenario: Testar passar dados de produto
    Dado os dados do produto <nome>, <preco>, <descricao>, <imagem> e <categoria> a ser cadastrado
    Quando usuario tenta o insert do produto verificando se o mesmo ja nao esta cadastrado e inclusao de alguma quantidade em estoque
    Entao a quantidade em estoque desse produto sera maior que 0

    Exemplos:
      | nome  | preco | descricao | imagem | categoria |
      | "GTA V" | 149.87 | "A Rockstar traz o quinto jogo da franquia mais popular, um jogo de ação com muita adrenalina e aventuras inusitadas, Grand Theft Auto V continua com o enredo que é sua marca registrada, um mundo repleto de crimes e muitos delitos." | "https://www.casasbahia-imagens.com.br/Control/ArquivoExibir.aspx?IdArquivo=243510462" | "XBOXJOGOS" |
      | "Xbox One" | 2660.00 | "Aventure-se em um universo fantástico e cheio de efeitos." | "https://images-americanas.b2w.io/produtos/2911577960/imagens/console-xbox-one-s-1-tb/2911577960_1_large.jpg" | "XBOXCONSOLES" |
      | "Jogo Chaves" | 9.90 | "Junte-se ao Chaves e mostre que é o melhor no campeonato da vila!" | "https://www.pontofrio-imagens.com.br/Control/ArquivoExibir.aspx?IdArquivo=243451778" | "WIIJOGOS" |
      | "Nintendo Wii" | 1.180 | "Nintendo Wii completo semi nv, muito bem conservado, 6 meses de garantia !" | "https://m.media-amazon.com/images/I/51kxAkJF5mL._AC_SX425_.jpg" | "WIICONSOLES" |