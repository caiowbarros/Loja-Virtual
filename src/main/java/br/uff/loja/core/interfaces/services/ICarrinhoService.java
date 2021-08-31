package br.uff.loja.core.interfaces.services;

import java.util.List;

import br.uff.loja.core.dtos.CarrinhoDTO;
import br.uff.loja.core.dtos.CarrinhoProdutoDTO;
import br.uff.loja.core.dtos.PaginateDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface ICarrinhoService {

    public CarrinhoDTO recuperaCarrinhoAtivo(Integer carrinhoId, Integer usuarioId, String ip) throws LojaException;

    public void insereProdutoCarrinho(Integer carrinhoId, Integer produtoId) throws LojaException;

    public void removeProdutoCarrinho(Integer carrinhoId, Integer produtoId) throws LojaException;

    public List<CarrinhoProdutoDTO> listaProdutosCarrinho(Integer id) throws LojaException;

    public PaginateDTO<List<CarrinhoProdutoDTO>> listaProdutosCarrinho(Integer id, Integer itensPorPagina, Integer paginaAtual) throws LojaException;

    public void alteraQuantidadeProdutoCarrinho(Integer carrinhoId, Integer produtoId, Integer quantidade) throws LojaException;

    public Boolean carrinhoAtivoValido(Integer id, Integer usuarioId) throws LojaException;

    public Double recuperaPrecoTotalDeUmCarrinho(Integer id) throws LojaException;

    public Integer quantidadeProdutoNoCarrinho(Integer id, Integer produtoId) throws LojaException;
}
