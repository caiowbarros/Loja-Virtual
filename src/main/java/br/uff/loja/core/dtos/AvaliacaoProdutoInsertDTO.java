package br.uff.loja.core.dtos;

public class AvaliacaoProdutoInsertDTO extends BaseDTO {

    private Integer usuarioId;
    private Integer produtoId;
    private Integer avaliacao;
    private String descricao;
    private String titulo;

    public AvaliacaoProdutoInsertDTO() {
    }

    public AvaliacaoProdutoInsertDTO(
            Integer usuarioId, Integer produtoId, Integer avaliacao, String descricao, String titulo
    ) {
        this.setUsuarioId(usuarioId);
        this.setProdutoId(produtoId);
        this.setAvaliacao(avaliacao);
        this.setDescricao(descricao);
        this.setTitulo(titulo);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Integer avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
