package br.uff.loja.core.dtos;

import java.util.Date;
import java.util.List;
import java.util.Map;

import br.uff.loja.infrastructure.shared.Helper;

public class VendaDTO extends BaseDTO {
    private Integer id;
    private Integer carrinhoId;
    private Double precoTotal;
    private Date criadoEm;
    private Integer enderecoId;
    private Integer usuarioId;
    private List<CarrinhoProdutoDTO> produtosDoCarrinho;
    private EnderecoDTO endereco;

    public VendaDTO() {}

    public VendaDTO(Map<String,Object> venda) {
        this.setId(Integer.valueOf(String.valueOf(venda.get("id"))));
        this.setCarrinhoId(Integer.valueOf(String.valueOf(venda.get("carrinhoId"))));
        this.setPrecoTotal(Double.valueOf(String.valueOf(venda.get("precoTotal"))));
        this.setCriadoEm((new Helper()).convertStringToDate("yyyy-MM-dd HH:mm:ss", String.valueOf(venda.get("criadoEm"))));
        this.setEnderecoId(Integer.valueOf(String.valueOf(venda.get("enderecoId"))));
        this.setUsuarioId(Integer.valueOf(String.valueOf(venda.get("usuarioId"))));
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public void setProdutosDoCarrinho(List<CarrinhoProdutoDTO> produtosDoCarrinho) {
        this.produtosDoCarrinho = produtosDoCarrinho;
    }

    public List<CarrinhoProdutoDTO> getProdutosDoCarrinho() {
        return produtosDoCarrinho;
    }

    public Integer getCarrinhoId() {
        return carrinhoId;
    }
    public Date getCriadoEm() {
        return criadoEm;
    }
    public Integer getEnderecoId() {
        return enderecoId;
    }
    public Integer getId() {
        return id;
    }
    public Double getPrecoTotal() {
        return precoTotal;
    }
    public Integer getUsuarioId() {
        return usuarioId;
    }
    public void setCarrinhoId(Integer carrinhoId) {
        this.carrinhoId = carrinhoId;
    }
    public void setCriadoEm(Date criadoEm) {
        this.criadoEm = criadoEm;
    }
    public void setEnderecoId(Integer enderecoId) {
        this.enderecoId = enderecoId;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setPrecoTotal(Double precoTotal) {
        this.precoTotal = precoTotal;
    }
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
