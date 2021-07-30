package br.uff.loja.core.services;

import java.util.List;

import br.uff.loja.core.dtos.ProdutoDTO;
import br.uff.loja.core.dtos.ProdutoVitrineUsuarioDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.services.IProdutoService;

public class ProdutoService implements IProdutoService {

    @Override
    public ProdutoDTO encontraProdutoPorId(Integer id) throws LojaException {
        return null;
    }

    @Override
    public Integer excluiProdutoPorId(Integer id) throws LojaException {
        return null;
    }

    @Override
    public Integer atualizaProdutoPorId(Integer id, ProdutoDTO produto) throws LojaException {
        return null;
    }

    @Override
    public Integer insereQuantidadeEmEstoqueDoProdutoPorId(Integer id, Integer quantidade) throws LojaException {
        return null;
    }

    @Override
    public Integer insereProduto(ProdutoDTO produto) throws LojaException {
        return null;
    }

    @Override
    public Integer usuarioToogleFavoritaProdutoPorId(Integer produtoId, Integer usuarioId) throws LojaException {
        return null;
    }

    @Override
    public List<ProdutoDTO> listaProdutosAdm() throws LojaException {
        return null;
    }

    @Override
    public List<ProdutoDTO> listaProdutosVitrine() throws LojaException {
        return null;
    }

    @Override
    public ProdutoVitrineUsuarioDTO mostraProdutoVitrineParaUsuario(Integer id, Integer usuarioId)
            throws LojaException {
        return null;
    }
    
}
