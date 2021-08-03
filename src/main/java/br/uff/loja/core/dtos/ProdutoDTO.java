package br.uff.loja.core.dtos;

import java.util.Map;

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

    public ProdutoDTO(Map<String,Object> produto) {
        this.setId(Integer.valueOf(String.valueOf(produto.get("id"))));
        this.setNome(String.valueOf(produto.get("name")));
        this.setPreco(Double.valueOf(String.valueOf(produto.get("price"))));
        this.setDescricao(String.valueOf(produto.get("description")));
        this.setImagem(String.valueOf(produto.get("img")));
        this.setCategoriaId(Integer.valueOf(String.valueOf(produto.get("category_id"))));
        this.setQuantidade(Integer.valueOf(String.valueOf(produto.get("quantity"))));
    }

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
