package br.uff.loja.core.dtos;

import br.uff.loja.infrastructure.shared.Helper;
import java.util.Map;

public class ProdutoVitrineUsuarioDTO extends BaseDTO {
    private Integer id;
    private String nome;
    private Integer quantidade;
    private String descricao;
    private String imagem;
    private Double preco;
    private String categoria;
    private Boolean favoritoDoUsuario;
    private Integer avaliacaoDadaPeloUsuario;
    private Integer quantidadeAvaliacoes;
    private Integer somaAvaliacoes;
    private Integer quantidadeAvaliacoesNota1;
    private Integer quantidadeAvaliacoesNota2;
    private Integer quantidadeAvaliacoesNota3;
    private Integer quantidadeAvaliacoesNota4;
    private Integer quantidadeAvaliacoesNota5;

    private Double barra5Estrelas;
    private Double barra4Estrelas;
    private Double barra3Estrelas;
    private Double barra2Estrelas;
    private Double barra1Estrelas;
    private Double resumoAvaliacoes;

    public ProdutoVitrineUsuarioDTO() {}

    public ProdutoVitrineUsuarioDTO(Map<String,Object> produto) {
        this.setId(Integer.valueOf(String.valueOf(produto.get("id"))));
        this.setNome(String.valueOf(produto.get("nome")));
        this.setPreco(Double.valueOf(String.valueOf(produto.get("preco"))));
        this.setDescricao(String.valueOf(produto.get("descricao")));
        this.setImagem(String.valueOf(produto.get("imagem")));
        this.setCategoria(String.valueOf(produto.get("categoria")));
        this.setQuantidade(Integer.valueOf(String.valueOf(produto.get("quantidade"))));
        this.setFavoritoDoUsuario(Integer.valueOf(String.valueOf(produto.get("favoritoDoUsuario"))) > 0);
        this.setAvaliacaoDadaPeloUsuario((new Helper()).tryParseInteger(String.valueOf(produto.get("avaliacaoDadaPeloUsuario"))));
        this.setQuantidadeAvaliacoes(Integer.valueOf(String.valueOf(produto.get("quantidadeAvaliacoes"))));
        this.setSomaAvaliacoes(Integer.valueOf(String.valueOf(produto.get("somaAvaliacoes"))));
        this.setQuantidadeAvaliacoesNota1(Integer.valueOf(String.valueOf(produto.get("quantidadeAvaliacoesNota1"))));
        this.setQuantidadeAvaliacoesNota2(Integer.valueOf(String.valueOf(produto.get("quantidadeAvaliacoesNota2"))));
        this.setQuantidadeAvaliacoesNota3(Integer.valueOf(String.valueOf(produto.get("quantidadeAvaliacoesNota3"))));
        this.setQuantidadeAvaliacoesNota4(Integer.valueOf(String.valueOf(produto.get("quantidadeAvaliacoesNota4"))));
        this.setQuantidadeAvaliacoesNota5(Integer.valueOf(String.valueOf(produto.get("quantidadeAvaliacoesNota5"))));
        // calculos pras barras
        if (this.getQuantidadeAvaliacoes() > 0) {
            this.setResumoAvaliacoes(Double.valueOf(this.somaAvaliacoes)/Double.valueOf(this.quantidadeAvaliacoes));
            this.setBarra1Estrelas(Double.valueOf((this.quantidadeAvaliacoesNota1 * 100.0) / this.quantidadeAvaliacoes));
            this.setBarra2Estrelas(Double.valueOf((this.quantidadeAvaliacoesNota2 * 100.0) / this.quantidadeAvaliacoes));
            this.setBarra3Estrelas(Double.valueOf((this.quantidadeAvaliacoesNota3 * 100.0) / this.quantidadeAvaliacoes));
            this.setBarra4Estrelas(Double.valueOf((this.quantidadeAvaliacoesNota4 * 100.0) / this.quantidadeAvaliacoes));
            this.setBarra5Estrelas(Double.valueOf((this.quantidadeAvaliacoesNota5 * 100.0) / this.quantidadeAvaliacoes));
        } else {
            this.setResumoAvaliacoes(0.0);
            this.setBarra1Estrelas(0.0);
            this.setBarra2Estrelas(0.0);
            this.setBarra3Estrelas(0.0);
            this.setBarra4Estrelas(0.0);
            this.setBarra5Estrelas(0.0);
        }
    }
    
    public void setResumoAvaliacoes(Double resumoAvaliacoes) {
        this.resumoAvaliacoes = resumoAvaliacoes;
    }
    
    public Double getResumoAvaliacoes() {
        return this.resumoAvaliacoes;
    }

    public void setBarra1Estrelas(Double barra1Estrelas) {
        this.barra1Estrelas = barra1Estrelas;
    }

    public void setBarra2Estrelas(Double barra2Estrelas) {
        this.barra2Estrelas = barra2Estrelas;
    }

    public void setBarra3Estrelas(Double barra3Estrelas) {
        this.barra3Estrelas = barra3Estrelas;
    }

    public void setBarra4Estrelas(Double barra4Estrelas) {
        this.barra4Estrelas = barra4Estrelas;
    }

    public void setBarra5Estrelas(Double barra5Estrelas) {
        this.barra5Estrelas = barra5Estrelas;
    }

    public Double getBarra1Estrelas() {
        return barra1Estrelas;
    }
    public Double getBarra2Estrelas() {
        return barra2Estrelas;
    }
    public Double getBarra3Estrelas() {
        return barra3Estrelas;
    }
    public Double getBarra4Estrelas() {
        return barra4Estrelas;
    }
    public Double getBarra5Estrelas() {
        return barra5Estrelas;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getImagem() {
        return imagem;
    }

    public void setAvaliacaoDadaPeloUsuario(Integer avaliacaoDadaPeloUsuario) {
        this.avaliacaoDadaPeloUsuario = avaliacaoDadaPeloUsuario;
    }

    public Integer getAvaliacaoDadaPeloUsuario() {
        return avaliacaoDadaPeloUsuario;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setFavoritoDoUsuario(Boolean favoritoDoUsuario) {
        this.favoritoDoUsuario = favoritoDoUsuario;
    }

    public Boolean getFavoritoDoUsuario() {
        return favoritoDoUsuario;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidadeAvaliacoes(Integer quantidadeAvaliacoes) {
        this.quantidadeAvaliacoes = quantidadeAvaliacoes;
    }

    public Integer getQuantidadeAvaliacoes() {
        return quantidadeAvaliacoes;
    }

    public void setQuantidadeAvaliacoesNota1(Integer quantidadeAvaliacoesNota1) {
        this.quantidadeAvaliacoesNota1 = quantidadeAvaliacoesNota1;
    }

    public Integer getQuantidadeAvaliacoesNota1() {
        return quantidadeAvaliacoesNota1;
    }

    public void setQuantidadeAvaliacoesNota2(Integer quantidadeAvaliacoesNota2) {
        this.quantidadeAvaliacoesNota2 = quantidadeAvaliacoesNota2;
    }

    public Integer getQuantidadeAvaliacoesNota2() {
        return quantidadeAvaliacoesNota2;
    }

    public void setQuantidadeAvaliacoesNota3(Integer quantidadeAvaliacoesNota3) {
        this.quantidadeAvaliacoesNota3 = quantidadeAvaliacoesNota3;
    }

    public Integer getQuantidadeAvaliacoesNota3() {
        return quantidadeAvaliacoesNota3;
    }

    public void setQuantidadeAvaliacoesNota5(Integer quantidadeAvaliacoesNota5) {
        this.quantidadeAvaliacoesNota5 = quantidadeAvaliacoesNota5;
    }

    public Integer getQuantidadeAvaliacoesNota4() {
        return quantidadeAvaliacoesNota4;
    }

    public void setQuantidadeAvaliacoesNota4(Integer quantidadeAvaliacoesNota4) {
        this.quantidadeAvaliacoesNota4 = quantidadeAvaliacoesNota4;
    }

    public Integer getSomaAvaliacoes() {
        return somaAvaliacoes;
    }

    public void setSomaAvaliacoes(Integer somaAvaliacoes) {
        this.somaAvaliacoes = somaAvaliacoes;
    }

    public Integer getQuantidadeAvaliacoesNota5() {
        return quantidadeAvaliacoesNota5;
    }
}
