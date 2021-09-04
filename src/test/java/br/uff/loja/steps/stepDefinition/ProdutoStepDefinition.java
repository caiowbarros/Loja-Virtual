package br.uff.loja.steps.stepDefinition;

import java.util.List;
import java.util.Random;

import org.junit.Assert;

import br.uff.loja.core.dtos.ProdutoDTO;
import br.uff.loja.core.enums.EProdutoCategoria;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.services.IProdutoService;
import br.uff.loja.core.services.ProdutoService;
import io.cucumber.java.es.Dado;
import io.cucumber.java.it.Quando;
import io.cucumber.java.pt.Entao;

public class ProdutoStepDefinition {
    private String nome;
    private Double preco;
    private String descricao;
    private String imagem;
    private String categoria;
    private Integer idProdutoJaCadastrado = null;

    @Dado("os dados do produto {string}, {double}, {string}, {string} e {string} a ser cadastrado")
    public void os_dados_do_produto_e_a_ser_cadastrado(String string, Double double1, String string2, String string3,
            String string4) {
        this.nome = string;
        this.preco = double1;
        this.descricao = string2;
        this.imagem = string3;
        this.categoria = string4;
        this.idProdutoJaCadastrado = null;
    }

    @Quando("usuario tenta o insert do produto verificando se o mesmo ja nao esta cadastrado e inclusao de alguma quantidade em estoque")
    public void usuario_tenta_o_insert_do_produto_verificando_se_o_mesmo_ja_nao_esta_cadastrado_e_inclusao_de_alguma_quantidade_em_estoque()
            throws LojaException {
        IProdutoService produtoService = new ProdutoService();
        List<ProdutoDTO> produtos = produtoService.listaProdutosAdm();

        Integer categoriaId = EProdutoCategoria.valueOf(this.categoria).getId();

        produtos.forEach(produto -> {
            if (produto.getCategoriaId().equals(categoriaId) && produto.getNome().equals(this.nome)) {
                this.idProdutoJaCadastrado = produto.getId();
            }
        });

        if (this.idProdutoJaCadastrado == null) {
            produtoService.insereProduto(new ProdutoDTO(this.idProdutoJaCadastrado, this.nome, this.preco,
                    this.descricao, this.imagem, categoriaId, 0));

            produtos = produtoService.listaProdutosAdm();

            produtos.forEach(produto -> {
                if (produto.getCategoriaId().equals(categoriaId) && produto.getNome().equals(this.nome)) {
                    this.idProdutoJaCadastrado = produto.getId();
                }
            });
        }

        if (this.idProdutoJaCadastrado == null) {
            throw new LojaException("Produto não cadastrado!");
        }

        produtoService.insereQuantidadeEmEstoqueDoProdutoPorId(this.idProdutoJaCadastrado,
                new Random().nextInt(1000) + 1);
    }

    @Entao("a quantidade em estoque desse produto sera maior que {int}")
    public void a_quantidade_em_estoque_desse_produto_sera_maior_que(Integer int1) throws LojaException {
        IProdutoService produtoService = new ProdutoService();

        Integer usuarioId = null;
        Integer qtd = produtoService.mostraProdutoVitrineParaUsuario(this.idProdutoJaCadastrado, usuarioId)
                .getQuantidade();

        Assert.assertEquals(true, Boolean.TRUE.equals(qtd > int1));
    }
}
