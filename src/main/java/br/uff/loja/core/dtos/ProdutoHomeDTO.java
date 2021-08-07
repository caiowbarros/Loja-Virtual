package br.uff.loja.core.dtos;

import java.util.Map;

public class ProdutoHomeDTO extends BaseDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private String imagem;

    public ProdutoHomeDTO() {}

    public ProdutoHomeDTO(Map<String,Object> produto) {
        this.setId(Integer.valueOf(String.valueOf(produto.get("id"))));
        this.setNome(String.valueOf(produto.get("name")));
        this.setDescricao(String.valueOf(produto.get("description")));
        this.setImagem(String.valueOf(produto.get("img")));
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
}
