package br.uff.loja.core.enums;

public enum EProdutoCategoria {
    PLAYSTATION(1, "Playstation"),
    XBOX(2, "Xbox"),
    WII(3, "Wii"),
    PLAYSTATIONACESSORIOS(4, "Playstation > Acessórios"),
    PLAYSTATIONCONSOLES(5, "Playstation > Consoles"),
    PLAYSTATIONJOGOS(6, "Playstation > Jogos"),
    XBOXACESSORIOS(7, "Xbox > Acessórios"),
    XBOXCONSOLES(8, "Xbox > Consoles"),
    XBOXJOGOS(9, "Xbox > Jogos"),
    WIIACESSORIOS(10, "Wii > Acessórios"),
    WIICONSOLES(11, "Wii > Consoles"),
    WIIJOGOS(12, "Wii > Jogos");

    private Integer id;
    private String nome;

    EProdutoCategoria(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public Integer getId() {
        return id;
    }
}
