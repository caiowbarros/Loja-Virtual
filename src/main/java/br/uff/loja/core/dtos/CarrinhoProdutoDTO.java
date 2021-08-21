package br.uff.loja.core.dtos;

import java.util.Map;

public class CarrinhoProdutoDTO extends BaseDTO {

    private Integer id;
    private Integer produtoId;
    private String nome;
    private String descricao;
    private String imagem;
    private Double preco;
    private Integer quantidadeEmEstoque;
    private Integer quantidade;
    private Double precoTotal;

    public CarrinhoProdutoDTO() {
    }

    public CarrinhoProdutoDTO(Map<String, Object> carrinhoProduto) {
        this.setId(Integer.valueOf(String.valueOf(carrinhoProduto.get("id"))));
        this.setProdutoId(Integer.valueOf(String.valueOf(carrinhoProduto.get("produtoId"))));
        this.setNome(String.valueOf(carrinhoProduto.get("nome")));
        this.setDescricao(String.valueOf(carrinhoProduto.get("descricao")));
        this.setImagem(String.valueOf(carrinhoProduto.get("imagem")));
        this.setPreco(Double.valueOf(String.valueOf(carrinhoProduto.get("preco"))));
        this.setQuantidadeEmEstoque(Integer.valueOf(String.valueOf(carrinhoProduto.get("quantidadeEmEstoque"))));
        this.setQuantidade(Integer.valueOf(String.valueOf(carrinhoProduto.get("quantidade"))));
        this.setPrecoTotal(Double.valueOf(String.valueOf(carrinhoProduto.get("precoTotal"))));
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getId() {
        return id;
    }

    public String getImagem() {
        return imagem;
    }

    public String getNome() {
        return nome;
    }

    public Double getPreco() {
        return preco;
    }

    public Double getPrecoTotal() {
        return precoTotal;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Integer getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setPrecoTotal(Double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }
}
