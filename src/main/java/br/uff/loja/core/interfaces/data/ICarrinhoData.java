package br.uff.loja.core.interfaces.data;

import java.util.List;

import br.uff.loja.core.dtos.CarrinhoDTO;
import br.uff.loja.core.dtos.CarrinhoProdutoDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface ICarrinhoData {

    public CarrinhoDTO encontraCarrinho(Integer id) throws LojaException;

    public List<CarrinhoDTO> listaCarrinhosDoUsuarioNaoVendidos(Integer usuarioId) throws LojaException;

    public Boolean carrinhoVendido(Integer id) throws LojaException;

    public void defineDonoDeUmCarrinhoSemUsuarioNoMomento(Integer id, Integer usuarioId) throws LojaException;

    public Boolean carrinhoSemDono(Integer id) throws LojaException;

    public Boolean carrinhoDoUsuario(Integer id, Integer usuarioId) throws LojaException;

    public Boolean carrinhoExiste(Integer id) throws LojaException;

    public Integer quantidadeProdutosCarrinho(Integer id) throws LojaException;

    public Integer quantidadeProdutoNoCarrinho(Integer id, Integer produtoId) throws LojaException;

    public void criaCarrinho(String ip, String criadoEm, Integer usuarioId) throws LojaException;

    public CarrinhoDTO encontraCarrinho(String ip, String criadoEm, Integer usuarioId) throws LojaException;

    public List<CarrinhoProdutoDTO> listaProdutosCarrinho(Integer id) throws LojaException;

    public Boolean verificaProdutoNoCarrinho(Integer id, Integer produtoId) throws LojaException;

    public void adicionaProdutoNoCarrinho(Integer id, Integer produtoId) throws LojaException;

    public void removeProdutoDoCarrinho(Integer id, Integer produtoId) throws LojaException;

    public void atualizaQtdDoProdutoNoCarrinho(Integer id, Integer produtoId, Integer novaQtd) throws LojaException;

    public Double recuperaPrecoTotalDeUmCarrinho(Integer id) throws LojaException;
}
