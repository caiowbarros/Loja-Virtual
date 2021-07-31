package br.uff.loja.core.dtos;

import java.util.Map;

public class ProdutoListaDTO extends BaseDTO {    
    private Integer id;
    private String nome;
    private Double preco;
    private String descricao;
    private String imagem;
    private String categoria;

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

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
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

    public ProdutoListaDTO() {}

    public ProdutoListaDTO(Map<String,Object> produto) {
        this.setId(Integer.valueOf(String.valueOf(produto.get("id"))));
        this.setNome(String.valueOf(produto.get("nome")));
        this.setPreco(Double.valueOf(String.valueOf(produto.get("preco"))));
        this.setDescricao(String.valueOf(produto.get("descricao")));
        this.setImagem(String.valueOf(produto.get("imagem")));
        this.setCategoria(String.valueOf(produto.get("categoria")));
    }

    public ProdutoListaDTO(
        Integer id,
        String nome,
        Double preco,
        String descricao,
        String imagem,
        String categoria
    ) {
        this.setId(id);
        this.setNome(nome);
        this.setPreco(preco);
        this.setDescricao(descricao);
        this.setImagem(imagem);
        this.setCategoria(categoria);
    }
}
