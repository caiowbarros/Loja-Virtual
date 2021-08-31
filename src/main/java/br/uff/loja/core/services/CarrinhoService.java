package br.uff.loja.core.services;

import java.util.Date;
import java.util.List;

import br.uff.loja.core.dtos.CarrinhoDTO;
import br.uff.loja.core.dtos.CarrinhoProdutoDTO;
import br.uff.loja.core.dtos.PaginateDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.ICarrinhoData;
import br.uff.loja.core.interfaces.services.ICarrinhoService;
import br.uff.loja.infrastructure.data.CarrinhoData;
import br.uff.loja.infrastructure.shared.Helper;

public class CarrinhoService implements ICarrinhoService {

    private final ICarrinhoData carrinhoData;
    private final Helper helper;
    private static final String TEXTOAVISOESPECIFICACARRINHO = " no carrinho de Id: ";

    public CarrinhoService() {
        carrinhoData = new CarrinhoData();
        helper = new Helper();
    }

    @Override
    public CarrinhoDTO recuperaCarrinhoAtivo(Integer carrinhoId, Integer usuarioId, String ip) throws LojaException {
        if (carrinhoId != null) {
            if (Boolean.TRUE.equals(carrinhoData.carrinhoExiste(carrinhoId)) && Boolean.FALSE.equals(carrinhoData.carrinhoVendido(carrinhoId))) {
                if (usuarioId == null) {
                    if (Boolean.TRUE.equals(carrinhoData.carrinhoSemDono(carrinhoId))) {
                        return carrinhoData.encontraCarrinho(carrinhoId);
                    }
                } else {
                    if (Boolean.TRUE.equals(carrinhoData.carrinhoSemDono(carrinhoId))) {
                        carrinhoData.defineDonoDeUmCarrinhoSemUsuarioNoMomento(carrinhoId, usuarioId);
                    }
                    if (Boolean.TRUE.equals(carrinhoData.carrinhoDoUsuario(carrinhoId, usuarioId))) {
                        return carrinhoData.encontraCarrinho(carrinhoId);
                    }
                }
            }
        }
        if (usuarioId != null) {
            List<CarrinhoDTO> carrinhosNaoVendidos = carrinhoData.listaCarrinhosDoUsuarioNaoVendidos(usuarioId);
            if (Boolean.FALSE.equals(carrinhosNaoVendidos.isEmpty())) {
                return carrinhosNaoVendidos.get(0);
            }
        }
        String criadoEm = helper.convertDateToString("yyyy-MM-dd HH:mm:ss", new Date());
        carrinhoData.criaCarrinho(ip, criadoEm, usuarioId);
        return carrinhoData.encontraCarrinho(ip, criadoEm, usuarioId);
    }

    @Override
    public void insereProdutoCarrinho(Integer carrinhoId, Integer produtoId) throws LojaException {
        if (Boolean.TRUE.equals(carrinhoData.carrinhoExiste(carrinhoId)) && Boolean.FALSE.equals(carrinhoData.carrinhoVendido(carrinhoId))) {
            Integer qtd = carrinhoData.quantidadeProdutoNoCarrinho(carrinhoId, produtoId);
            if (qtd > 0) {
                carrinhoData.atualizaQtdDoProdutoNoCarrinho(carrinhoId, produtoId, qtd + 1);
            } else {
                carrinhoData.adicionaProdutoNoCarrinho(carrinhoId, produtoId);
            }
            return;
        }
        throw new LojaException("Não foi possível inserir o produto de Id: " + produtoId + TEXTOAVISOESPECIFICACARRINHO + carrinhoId + ".");

    }

    @Override
    public void removeProdutoCarrinho(Integer carrinhoId, Integer produtoId) throws LojaException {
        if (Boolean.TRUE.equals(carrinhoData.carrinhoExiste(carrinhoId)) && Boolean.FALSE.equals(carrinhoData.carrinhoVendido(carrinhoId))) {
            Integer qtd = carrinhoData.quantidadeProdutoNoCarrinho(carrinhoId, produtoId);
            if (qtd > 0) {
                carrinhoData.removeProdutoDoCarrinho(carrinhoId, produtoId);
            } else {
                throw new LojaException("O produto de id: " + produtoId + " não encontra-se no carrinho de id: " + carrinhoId + ".");
            }
            return;
        }
        throw new LojaException("Não foi possível remover o produto de Id: " + produtoId + TEXTOAVISOESPECIFICACARRINHO + carrinhoId + ".");
    }

    @Override
    public List<CarrinhoProdutoDTO> listaProdutosCarrinho(Integer id) throws LojaException {
        if (Boolean.TRUE.equals(carrinhoData.carrinhoExiste(id))) {
            return carrinhoData.listaProdutosCarrinho(id);
        }
        throw new LojaException("Não foi possível listar os produtos do carrinho de Id: " + id + ".");
    }

    @Override
    public void alteraQuantidadeProdutoCarrinho(Integer carrinhoId, Integer produtoId, Integer quantidade) throws LojaException {
        if (Boolean.TRUE.equals(carrinhoData.carrinhoExiste(carrinhoId)) && Boolean.FALSE.equals(carrinhoData.carrinhoVendido(carrinhoId))) {
            carrinhoData.atualizaQtdDoProdutoNoCarrinho(carrinhoId, produtoId, quantidade);
            return;
        }
        throw new LojaException("Não foi possível atualizar a quantidade do produto de Id: " + produtoId + TEXTOAVISOESPECIFICACARRINHO + carrinhoId + ".");
    }

    @Override
    public Boolean carrinhoAtivoValido(Integer id, Integer usuarioId) throws LojaException {
        return Boolean.TRUE.equals(carrinhoData.carrinhoExiste(id)) && Boolean.TRUE.equals(carrinhoData.carrinhoDoUsuario(id, usuarioId)) && Boolean.FALSE.equals(carrinhoData.carrinhoVendido(id)) && Boolean.TRUE.equals(carrinhoData.quantidadeProdutosCarrinho(id) > 0);
    }

    @Override
    public Double recuperaPrecoTotalDeUmCarrinho(Integer id) throws LojaException {
        return carrinhoData.recuperaPrecoTotalDeUmCarrinho(id);
    }

    @Override
    public PaginateDTO<List<CarrinhoProdutoDTO>> listaProdutosCarrinho(Integer id, Integer itensPorPagina, Integer paginaAtual) throws LojaException {
        return carrinhoData.listaProdutosCarrinho(id, itensPorPagina, paginaAtual);
    }

    @Override
    public Integer quantidadeProdutoNoCarrinho(Integer id, Integer produtoId) throws LojaException {
        return carrinhoData.quantidadeProdutoNoCarrinho(id, produtoId);
    }
}
