package br.uff.loja.core.dtos;

import java.util.Date;
import java.util.Map;

import br.uff.loja.infrastructure.shared.Helper;

public class CarrinhoDTO extends BaseDTO {
    private Integer id;
    private String ip;
    private Integer usuarioId;
    private Date criadoEm;

    public CarrinhoDTO() {}

    public CarrinhoDTO(Map<String,Object> carrinho) {
        this.setId(Integer.valueOf(String.valueOf(carrinho.get("id"))));
        this.setIp(String.valueOf(carrinho.get("ip")));
        this.setUsuarioId(Integer.valueOf(String.valueOf(carrinho.get("user_id"))));
        this.setCriadoEm((new Helper()).convertStringToDate("yyyy-MM-dd HH:mm:ss", String.valueOf(carrinho.get("created_at"))));
    }

    public void setCriadoEm(Date criadoEm) {
        this.criadoEm = criadoEm;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
    public Date getCriadoEm() {
        return criadoEm;
    }
    public Integer getId() {
        return id;
    }
    public String getIp() {
        return ip;
    }
    public Integer getUsuarioId() {
        return usuarioId;
    }
}
