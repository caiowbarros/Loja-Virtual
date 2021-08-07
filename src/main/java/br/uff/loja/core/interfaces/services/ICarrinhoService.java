package br.uff.loja.core.interfaces.services;

import java.util.List;

import br.uff.loja.core.dtos.CarrinhoDTO;
import br.uff.loja.core.dtos.CarrinhoProdutoDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface ICarrinhoService {
    public CarrinhoDTO recuperaCarrinhoAtivo(Integer carrinhoId, Integer usuarioId) throws LojaException;
    public void insereProdutoCarrinho(Integer carrinhoId, Integer usuarioId, Integer produtoId) throws LojaException;
    public void removeProdutoCarrinho(Integer carrinhoId, Integer usuarioId, Integer produtoId) throws LojaException;
    public List<CarrinhoProdutoDTO> listaProdutosCarrinho(Integer id, Integer usuarioId) throws LojaException;
    public void alteraQuantidadeProdutoCarrinho(Integer carrinhoId, Integer usuarioId, Integer produtoId, Integer quantidade) throws LojaException;
}
