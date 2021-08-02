package br.uff.loja.core.enums;

public enum EPermissaoUsuario {
    ADM(1),
    CLIENTE(2);

    private Integer id;

    EPermissaoUsuario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
