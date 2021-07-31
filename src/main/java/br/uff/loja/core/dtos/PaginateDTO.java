package br.uff.loja.core.dtos;

public class PaginateDTO<T> {
    private Integer paginaAtual;
    private T dados;
    private Integer ultimaPagina;

    public PaginateDTO() {}

    public PaginateDTO(
        Integer paginaAtual,
        T dados,
        Integer ultimaPagina
    ) {
        this.setPaginaAtual(paginaAtual);
        this.setDados(dados);
        this.setUltimaPagina(ultimaPagina);
    }

    public void setDados(T dados) {
        this.dados = dados;
    }

    public T getDados() {
        return dados;
    }

    public Integer getPaginaAtual() {
        return paginaAtual;
    }

    public Integer getUltimaPagina() {
        return ultimaPagina;
    }

    public void setUltimaPagina(Integer ultimaPagina) {
        this.ultimaPagina = ultimaPagina;
    }

    public void setPaginaAtual(Integer paginaAtual) {
        this.paginaAtual = paginaAtual;
    }
}
