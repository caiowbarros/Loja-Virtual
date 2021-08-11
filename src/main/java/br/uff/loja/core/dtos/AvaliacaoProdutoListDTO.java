package br.uff.loja.core.dtos;

import java.util.Map;

public class AvaliacaoProdutoListDTO extends BaseDTO {
    private String avaliador;
    private String avaliacaoTitulo;
    private String avaliacao;
    private String avaliacaoDescricao;
    private String avaliacaoData;
    private String avaliacaoDataSimples;

    public AvaliacaoProdutoListDTO() {}

    public AvaliacaoProdutoListDTO(Map<String, Object> avaliacao) {
        this.setAvaliador(String.valueOf(avaliacao.get("avaliador")));
        this.setAvaliacaoTitulo(String.valueOf(avaliacao.get("avaliacaoTitulo")));
        this.setAvaliacao(String.valueOf(avaliacao.get("avaliacao")));
        this.setAvaliacaoDescricao(String.valueOf(avaliacao.get("avaliacaoDescricao")));
        this.setAvaliacaoData(String.valueOf(avaliacao.get("avaliacaoData")));
        this.setAvaliacaoDataSimples(String.valueOf(avaliacao.get("avaliacaoDataSimples")));
    }

    public void setAvaliacaoDataSimples(String avaliacaoDataSimples) {
        this.avaliacaoDataSimples = avaliacaoDataSimples;
    }

    public String getAvaliacaoDataSimples() {
        return avaliacaoDataSimples;
    }

    public void setAvaliacaoData(String avaliacaoData) {
        this.avaliacaoData = avaliacaoData;
    }

    public String getAvaliacaoData() {
        return avaliacaoData;
    }

    public String getAvaliacaoDescricao() {
        return avaliacaoDescricao;
    }

    public void setAvaliacaoDescricao(String avaliacaoDescricao) {
        this.avaliacaoDescricao = avaliacaoDescricao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacaoTitulo(String avaliacaoTitulo) {
        this.avaliacaoTitulo = avaliacaoTitulo;
    }

    public String getAvaliacaoTitulo() {
        return avaliacaoTitulo;
    }

    public void setAvaliador(String avaliador) {
        this.avaliador = avaliador;
    }

    public String getAvaliador() {
        return avaliador;
    }
}
