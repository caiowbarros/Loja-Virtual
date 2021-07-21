package br.uff.core.enums;

public enum ERole {
    ADM(1),CLIENTE(2);

    private final Integer id;

    private ERole(Integer id) {
        this.id=id;
    }

    public Integer getId() {
        return id;
    }
}
