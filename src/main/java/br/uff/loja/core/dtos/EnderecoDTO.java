package br.uff.loja.core.dtos;

import java.util.HashMap;

public class EnderecoDTO extends BaseDTO {
    public EnderecoDTO () {}

    public EnderecoDTO (HashMap<String, Object> endereco) {
        this.id = Integer.valueOf(String.valueOf(endereco.get("id")));
        this.usuarioId = Integer.valueOf(String.valueOf(endereco.get("user_id")));
        this.nome = String.valueOf(endereco.get("name"));
        this.cep = Integer.valueOf(String.valueOf(endereco.get("zipcode")));
        this.logradouro = String.valueOf(endereco.get("address"));
        this.estado = String.valueOf(endereco.get("state"));
        this.cidade = String.valueOf(endereco.get("city"));
        this.pais = String.valueOf(endereco.get("country"));
    }

    public Integer id;
    public String nome;
    public Integer usuarioId;
    public Integer cep;
    public String logradouro;
    public String cidade;
    public String estado;
    public String pais;
}
