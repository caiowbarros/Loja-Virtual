package br.uff.loja.core.dtos;

import java.util.Map;

public class UsuarioDTO extends BaseDTO {
    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private Integer permissaoId;

    public UsuarioDTO() {}

    public UsuarioDTO(
        Integer id,
        String nome,
        String email,
        String senha,
        Integer permissaoId
    ) {
        this.setId(id);
        this.setNome(nome);
        this.setEmail(email);
        this.setSenha(senha);
        this.setPermissaoId(permissaoId);
    }

    public UsuarioDTO(Map<String,Object> usuario) {
        this.setId(Integer.valueOf(String.valueOf(usuario.get("id"))));
        this.setNome(String.valueOf(usuario.get("name")));
        this.setEmail(String.valueOf(usuario.get("email")));
        this.setSenha(String.valueOf(usuario.get("password")));
        this.setPermissaoId(Integer.valueOf(String.valueOf(usuario.get("role_id"))));
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setPermissaoId(Integer permissaoId) {
        this.permissaoId = permissaoId;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public String getNome() {
        return nome;
    }
    public Integer getPermissaoId() {
        return permissaoId;
    }
    public String getSenha() {
        return senha;
    }
    public Integer getId() {
        return id;
    }
}
