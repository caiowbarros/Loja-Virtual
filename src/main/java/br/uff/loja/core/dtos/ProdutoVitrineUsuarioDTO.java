package br.uff.loja.core.dtos;

public class ProdutoVitrineUsuarioDTO extends BaseDTO {
    public Integer id;
    public String nome;
    public Integer quantidade;
    public String descricao;
    public Double preco;
    public String categoria;
    public Boolean favoritoDoUsuario;
    public Integer avaliacaoDadaPeloUsuario;
    public Integer quantidadeAvaliacoes;
    public Integer somaAvaliacoes;
    public Integer quantidadeAvaliacoesNota1;
    public Integer quantidadeAvaliacoesNota2;
    public Integer quantidadeAvaliacoesNota3;
    public Integer quantidadeAvaliacoesNota4;
    public Integer quantidadeAvaliacoesNota5;
}
