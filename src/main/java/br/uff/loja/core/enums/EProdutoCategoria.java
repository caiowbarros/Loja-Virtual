package br.uff.loja.core.enums;

public enum EProdutoCategoria {
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
