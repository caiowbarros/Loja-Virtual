package br.uff.loja.core.dtos;

import java.util.Map;

public class EnderecoDTO extends BaseDTO {
    public EnderecoDTO () {}

    public EnderecoDTO (Map<String, Object> endereco) {
        this.setId(Integer.valueOf(String.valueOf(endereco.get("id"))));
        this.setUsuarioId(Integer.valueOf(String.valueOf(endereco.get("usuarioId"))));
        this.setNome(String.valueOf(endereco.get("nome")));
        this.setCep(Integer.valueOf(String.valueOf(endereco.get("cep"))));
        this.setLogradouro(String.valueOf(endereco.get("logradouro")));
        this.setEstado(String.valueOf(endereco.get("estado")));
        this.setCidade(String.valueOf(endereco.get("cidade")));
        this.setPais(String.valueOf(endereco.get("pais")));
    }

    public EnderecoDTO(String nome, Integer usuarioId, Integer cep, String logradouro, String cidade, String estado, String pais) {
        this.setUsuarioId(usuarioId);
        this.setNome(nome);
        this.setCep(cep);
        this.setLogradouro(logradouro);
        this.setEstado(estado);
        this.setCidade(cidade);
        this.setPais(pais);
    }

    private Integer id;
    private String nome;
    private Integer usuarioId;
    private Integer cep;
    private String logradouro;
    private String cidade;
    private String estado;
    private String pais;

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPais() {
        return pais;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCidade() {
        return cidade;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }
    
    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public Integer getCep() {
        return cep;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
