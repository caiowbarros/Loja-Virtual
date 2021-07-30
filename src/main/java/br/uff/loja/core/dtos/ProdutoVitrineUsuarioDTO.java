package br.uff.loja.core.dtos;

public class ProdutoVitrineUsuarioDTO extends BaseDTO {
    private Integer id;
    private String nome;
    private Integer quantidade;
    private String descricao;
    private Double preco;
    private String categoria;
    private Boolean favoritoDoUsuario;
    private Integer avaliacaoDadaPeloUsuario;
    private Integer quantidadeAvaliacoes;
    private Integer somaAvaliacoes;
    private Integer quantidadeAvaliacoesNota1;
    private Integer quantidadeAvaliacoesNota2;
    private Integer quantidadeAvaliacoesNota3;
    private Integer quantidadeAvaliacoesNota4;
    private Integer quantidadeAvaliacoesNota5;

    public void setAvaliacaoDadaPeloUsuario(Integer avaliacaoDadaPeloUsuario) {
        this.avaliacaoDadaPeloUsuario = avaliacaoDadaPeloUsuario;
    }

    public Integer getAvaliacaoDadaPeloUsuario() {
        return avaliacaoDadaPeloUsuario;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setFavoritoDoUsuario(Boolean favoritoDoUsuario) {
        this.favoritoDoUsuario = favoritoDoUsuario;
    }

    public Boolean getFavoritoDoUsuario() {
        return favoritoDoUsuario;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getPreco() {
        return preco;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidadeAvaliacoes(Integer quantidadeAvaliacoes) {
        this.quantidadeAvaliacoes = quantidadeAvaliacoes;
    }

    public Integer getQuantidadeAvaliacoes() {
        return quantidadeAvaliacoes;
    }

    public void setQuantidadeAvaliacoesNota1(Integer quantidadeAvaliacoesNota1) {
        this.quantidadeAvaliacoesNota1 = quantidadeAvaliacoesNota1;
    }

    public Integer getQuantidadeAvaliacoesNota1() {
        return quantidadeAvaliacoesNota1;
    }

    public void setQuantidadeAvaliacoesNota2(Integer quantidadeAvaliacoesNota2) {
        this.quantidadeAvaliacoesNota2 = quantidadeAvaliacoesNota2;
    }

    public Integer getQuantidadeAvaliacoesNota2() {
        return quantidadeAvaliacoesNota2;
    }

    public void setQuantidadeAvaliacoesNota3(Integer quantidadeAvaliacoesNota3) {
        this.quantidadeAvaliacoesNota3 = quantidadeAvaliacoesNota3;
    }

    public Integer getQuantidadeAvaliacoesNota3() {
        return quantidadeAvaliacoesNota3;
    }

    public void setQuantidadeAvaliacoesNota5(Integer quantidadeAvaliacoesNota5) {
        this.quantidadeAvaliacoesNota5 = quantidadeAvaliacoesNota5;
    }

    public Integer getQuantidadeAvaliacoesNota4() {
        return quantidadeAvaliacoesNota4;
    }

    public void setQuantidadeAvaliacoesNota4(Integer quantidadeAvaliacoesNota4) {
        this.quantidadeAvaliacoesNota4 = quantidadeAvaliacoesNota4;
    }

    public Integer getSomaAvaliacoes() {
        return somaAvaliacoes;
    }

    public void setSomaAvaliacoes(Integer somaAvaliacoes) {
        this.somaAvaliacoes = somaAvaliacoes;
    }

    public Integer getQuantidadeAvaliacoesNota5() {
        return quantidadeAvaliacoesNota5;
    }
}
