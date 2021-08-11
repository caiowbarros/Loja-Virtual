package br.uff.loja.core.dtos;

import java.util.Map;

public class RoleDTO {
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

    public RoleDTO() {}
    
    public RoleDTO(Map<String,Object> role) {
        this.setId(Integer.valueOf(String.valueOf(role.get("id"))));
        this.setNome(String.valueOf(role.get("nome")));
    }
}
