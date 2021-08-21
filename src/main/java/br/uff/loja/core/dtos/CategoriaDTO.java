package br.uff.loja.core.dtos;

import java.util.Map;

public class CategoriaDTO {

    private Integer id;
    private String nome;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public CategoriaDTO() {
    }

    public CategoriaDTO(Map<String, Object> categoria) {
        this.setId(Integer.valueOf(String.valueOf(categoria.get("id"))));
        this.setNome(String.valueOf(categoria.get("nome")));
    }
}
