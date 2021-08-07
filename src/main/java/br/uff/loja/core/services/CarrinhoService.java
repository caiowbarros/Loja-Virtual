package br.uff.loja.core.services;

import java.util.List;

import br.uff.loja.core.dtos.CarrinhoDTO;
import br.uff.loja.core.dtos.CarrinhoProdutoDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.ICarrinhoData;
import br.uff.loja.core.interfaces.services.ICarrinhoService;

public class CarrinhoService implements ICarrinhoService {
    private ICarrinhoData carrinhoData;

    public CarrinhoService() {
        carrinhoData = CarrinhoData();
    }

    @Override
    public CarrinhoDTO recuperaCarrinhoAtivo(Integer carrinhoId, Integer usuarioId) throws LojaException {
        return new CarrinhoDTO();
    }

    @Override
    public void insereProdutoCarrinho(Integer carrinhoId, Integer produtoId) throws LojaException {
        if(Boolean.TRUE.equals(carrinhoData.carrinhoExiste(carrinhoId)) && Boolean.FALSE.equals(carrinhoData.carrinhoVendido(carrinhoId))) {
            Integer qtd = carrinhoData.quantidadeProdutoNoCarrinho(carrinhoId, produtoId);
            if(qtd > 0) {
                carrinhoData.adicionaProdutoNoCarrinho(carrinhoId, produtoId);
            } else {
                carrinhoData.atualizaQtdDoProdutoNoCarrinho(carrinhoId, produtoId, qtd + 1);
            }
        }
        throw new LojaException("Não foi possível inserir o produto de Id: " + produtoId + " no carrinho de Id: " + carrinhoId + ".");
        
    }

    @Override
    public void removeProdutoCarrinho(Integer carrinhoId, Integer produtoId) throws LojaException {
        if(Boolean.TRUE.equals(carrinhoData.carrinhoExiste(carrinhoId)) && Boolean.FALSE.equals(carrinhoData.carrinhoVendido(carrinhoId))) {
            Integer qtd = carrinhoData.quantidadeProdutoNoCarrinho(carrinhoId, produtoId);
            if(qtd > 0) {
                carrinhoData.removeProdutoDoCarrinho(carrinhoId, produtoId);
            } else {
                throw new LojaException("O produto de id: " + produtoId + " não encontra-se no carrinho de id: " + carrinhoId + ".");
            }
        }
        throw new LojaException("Não foi possível remover o produto de Id: " + produtoId + " no carrinho de Id: " + carrinhoId + ".");
    }

    @Override
    public List<CarrinhoProdutoDTO> listaProdutosCarrinho(Integer id) throws LojaException {
        if(Boolean.TRUE.equals(carrinhoData.carrinhoExiste(id))) {
            return carrinhoData.listaProdutosCarrinho(id);
        }
        throw new LojaException("Não foi possível listar os produtos do carrinho de Id: " + id + ".");
    }

    @Override
    public void alteraQuantidadeProdutoCarrinho(Integer carrinhoId, Integer produtoId, Integer quantidade) throws LojaException {
        if(Boolean.TRUE.equals(carrinhoData.carrinhoExiste(carrinhoId)) && Boolean.FALSE.equals(carrinhoData.carrinhoVendido(carrinhoId))) {
            carrinhoData.atualizaQtdDoProdutoNoCarrinho(carrinhoId, produtoId, quantidade);
        }
        throw new LojaException("Não foi possível atualizar a quantidade do produto de Id: " + produtoId + " no carrinho de Id: " + carrinhoId + ".");
    }
    
}
