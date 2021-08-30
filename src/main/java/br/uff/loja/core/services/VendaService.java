package br.uff.loja.core.services;

import java.util.List;

import br.uff.loja.core.dtos.PaginateDTO;
import br.uff.loja.core.dtos.VendaDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.IVendaData;
import br.uff.loja.core.interfaces.services.ICarrinhoService;
import br.uff.loja.core.interfaces.services.IEnderecoService;
import br.uff.loja.core.interfaces.services.IVendaService;
import br.uff.loja.infrastructure.data.VendaData;

public class VendaService implements IVendaService {

    private IVendaData vendaData;
    private ICarrinhoService carrinhoService;
    private IEnderecoService enderecoService;

    public VendaService() {
        vendaData = new VendaData();
        carrinhoService = new CarrinhoService();
        enderecoService = new EnderecoService();
    }

    @Override
    public List<VendaDTO> listaVendasDoUsuario(Integer usuarioId) throws LojaException {
        return vendaData.listaVendasDoUsuario(usuarioId);
    }

    @Override
    public void gravaVenda(Integer usuarioId, Integer carrinhoId, Integer enderecoId) throws LojaException {
        if (Boolean.FALSE.equals(carrinhoService.carrinhoAtivoValido(carrinhoId, usuarioId))) {
            throw new LojaException("O carrinho de id: " + carrinhoId
                    + " não encontra-se válido no momento, verifique se o mesmo possui itens nele, se já foi vendido ou se pertence a outro usuário.");
        }
        if (Boolean.FALSE.equals(enderecoService.verificaEnderecoDoUsuario(enderecoId, usuarioId))) {
            throw new LojaException("O endereço escolhido de id:" + enderecoId
                    + " não pertence ao dono do carrinho (usuário de id: " + usuarioId + "), escolha outro endereço.");
        }
        vendaData.gravaVenda(carrinhoId, enderecoId);
    }

    @Override
    public PaginateDTO<List<VendaDTO>> listaVendasDoUsuario(Integer usuarioId, Integer itensPorPagina,
            Integer paginaAtual) throws LojaException {
        return vendaData.listaVendasDoUsuario(usuarioId, itensPorPagina, paginaAtual);
    }

    @Override
    public Boolean enderecoFoiUsadoEmAlgumaVenda(Integer enderecoId) throws LojaException {
        return vendaData.enderecoFoiUsadoEmAlgumaVenda(enderecoId);
    }
}
