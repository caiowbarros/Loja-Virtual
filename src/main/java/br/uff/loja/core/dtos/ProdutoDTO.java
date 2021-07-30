package br.uff.loja.core.dtos;

public class ProdutoDTO extends BaseDTO {
    private Integer id;
    private String nome;
    private Double preco;
    private String descricao;
    private String imagem;
    private Integer categoriaId;
    private Integer quantidade;

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getImagem() {
        return imagem;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Integer getCategoriaId() {
        return categoriaId;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public ProdutoDTO() {}

    public ProdutoDTO(
        Integer id,
        String nome,
        Double preco,
        String descricao,
        String imagem,
        Integer categoriaId,
        Integer quantidade
    ) {
        this.setId(id);
        this.setNome(nome);
        this.setPreco(preco);
        this.setDescricao(descricao);
        this.setImagem(imagem);
        this.setCategoriaId(categoriaId);
        this.setQuantidade(quantidade);
    }
}
