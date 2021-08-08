package br.uff.loja.infrastructure.data;

import java.util.Date;
import java.util.List;

import br.uff.loja.core.dtos.CarrinhoDTO;
import br.uff.loja.core.dtos.CarrinhoProdutoDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.ICarrinhoData;
import br.uff.loja.infrastructure.database.MySQLDAO;

public class CarrinhoData implements ICarrinhoData {
    private final MySQLDAO mysqlDAO;

    public CarrinhoData() {
        this.mysqlDAO = new MySQLDAO();
    }

    @Override
    public CarrinhoDTO encontraCarrinho(Integer id) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CarrinhoDTO> listaCarrinhosDoUsuarioNaoVendidos(Integer usuarioId) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean carrinhoVendido(Integer id) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void defineDonoDeUmCarrinhoSemUsuarioNoMomento(Integer id, Integer usuarioId) throws LojaException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Boolean carrinhoSemDono(Integer id) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean carrinhoDoUsuario(Integer id, Integer usuarioId) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean carrinhoExiste(Integer id) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer quantidadeProdutosCarrinho(Integer id) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer quantidadeProdutoNoCarrinho(Integer id, Integer produtoId) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void criaCarrinho(String ip, Date criadoEm, Integer usuarioId) throws LojaException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public CarrinhoDTO encontraCarrinho(String ip, Date criadoEm, Integer usuarioId) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<CarrinhoProdutoDTO> listaProdutosCarrinho(Integer id) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean verificaProdutoNoCarrinho(Integer id, Integer produtoId) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void adicionaProdutoNoCarrinho(Integer id, Integer produtoId) throws LojaException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeProdutoDoCarrinho(Integer id, Integer produtoId) throws LojaException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void atualizaQtdDoProdutoNoCarrinho(Integer id, Integer produtoId, Integer novaQtd) throws LojaException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void recuperaPrecoTotalDeUmCarirnho(Integer id) throws LojaException {
        // TODO Auto-generated method stub
        
    }
    
}
