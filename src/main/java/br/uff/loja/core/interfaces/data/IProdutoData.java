package br.uff.loja.core.interfaces.data;

import java.util.List;

import br.uff.loja.core.dtos.ProdutoDTO;
import br.uff.loja.core.dtos.ProdutoVitrineUsuarioDTO;

public interface IProdutoData {
    public ProdutoDTO encontraProdutoPorId(Integer id) throws Exception;
    public Integer excluiProdutoPorId(Integer id) throws Exception;
    public Integer atualizaProdutoPorId(Integer id, ProdutoDTO produto) throws Exception;
    public Integer insereQuantidadeEmEstoqueDoProdutoPorId(Integer id, Integer quantidade) throws Exception;
    public Integer insereProduto(ProdutoDTO produto) throws Exception;
    public Integer usuarioToogleFavoritaProdutoPorId(Integer produtoId, Integer usuarioId) throws Exception;
    public List<ProdutoDTO> listaProdutosAdm() throws Exception;
    public List<ProdutoDTO> listaProdutosVitrine() throws Exception;
    public ProdutoVitrineUsuarioDTO mostraProdutoVitrineParaUsuario(Integer id, Integer usuarioId) throws Exception;
}
