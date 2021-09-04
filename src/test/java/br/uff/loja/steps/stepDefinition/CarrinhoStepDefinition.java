package br.uff.loja.steps.stepDefinition;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import org.junit.Assert;

import br.uff.loja.core.dtos.CarrinhoDTO;
import br.uff.loja.core.interfaces.services.ICarrinhoService;
import br.uff.loja.core.services.CarrinhoService;

public class CarrinhoStepDefinition {
    Integer carrinhoId;
    Integer usuarioId;
    String ip;
    CarrinhoDTO carrinhoRetorno;
    String exMsg = "";

    ICarrinhoService carrinhoService = new CarrinhoService();

    @Dado("sistema pega as variaveis {int}, {int} e {int} pra recuperar o carrinho")
    public void sistema_pega_as_variaveis_e_pra_recuperar_o_carrinho(Integer int1, Integer int2, Integer int3) {
        this.carrinhoId = (int1.equals(0) ? null : int1);
        this.usuarioId = (int2.equals(0) ? null : int2);
        this.ip = String.valueOf(int3);
    }

    @Quando("usuario solicita o carrinho")
    public void usuario_solicita_o_carrinho() {
        try {
            carrinhoRetorno = carrinhoService.recuperaCarrinhoAtivo(this.carrinhoId, this.usuarioId, this.ip);
        } catch (Exception e) {
            exMsg = e.getMessage();
        }
    }

    @Entao("recebo o carrinho criado ou recuperado")
    public void recebo_o_carrinho_criado_ou_recuperado() {
        Assert.assertEquals("", exMsg);
        exMsg = "";
    }
}
