package br.uff.loja.infrastructure.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;
import br.uff.loja.core.dtos.AvaliacaoProdutoListDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.IAvaliacaoProdutoData;
import br.uff.loja.infrastructure.database.MySQLDAO;

public class AvaliacaoProdutoData implements IAvaliacaoProdutoData {
    private final MySQLDAO mysqlDAO;

    public AvaliacaoProdutoData() {
        this.mysqlDAO = new MySQLDAO();
    }

    @Override
    public Boolean jaFoiAvaliado(Integer usuarioId, Integer produtoId) throws LojaException {
        try {
            Object[] bind = {usuarioId, produtoId};
            Integer qtdAvaliacoes = Integer.valueOf(String.valueOf(this.mysqlDAO.dbValor("count(*)", "user_produts_rating", "user_id=? AND product_id=?", bind)));
            return qtdAvaliacoes > 0;
        } catch (Exception e) {
            throw new LojaException("Falha ao recuperar quantidade de avaliacoes do usuário para o produto: " + e.getMessage());
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public void insereAvaliacaoDoProduto(AvaliacaoProdutoInsertDTO avaliacaoProdutoInsertDTO) throws LojaException {
        try {
            Object[] bind = {avaliacaoProdutoInsertDTO.getUsuarioId(), avaliacaoProdutoInsertDTO.getProdutoId(), avaliacaoProdutoInsertDTO.getAvaliacao(), avaliacaoProdutoInsertDTO.getDescricao(), avaliacaoProdutoInsertDTO.getTitulo()};
            this.mysqlDAO.dbGrava("INSERT INTO user_produts_rating (user_id,product_id,rating,description,title,created_at) VALUES (?,?,?,?,?,SYSDATE())", bind, false);
        } catch (Exception e) {
            throw new LojaException("Falha ao Inserir a Avaliação do usuário de id: " + avaliacaoProdutoInsertDTO.getUsuarioId() + " para o Produto de id: " + avaliacaoProdutoInsertDTO.getProdutoId() + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public List<AvaliacaoProdutoListDTO> recuperaAvaliacoesDeUmProduto(Integer produtoId) throws LojaException {
        try {
            Object[] bind = {produtoId};
            List<HashMap<String, Object>> retornoDesformatado = this.mysqlDAO.dbCarrega("SELECT  u.`name` AS avaliador,  r.title AS avaliacaoTitulo,  r.rating AS avaliacao,  r.description AS avaliacaoDescricao,  DATE_FORMAT(r.created_at, '%M %d, %Y') AS avaliacaoData,  DATE_FORMAT(r.created_at, '%d/%m/%Y') AS avaliacaoDataSimples  FROM  user_produts_rating r  LEFT JOIN users u ON (r.user_id = u.id)  WHERE  r.product_id = ?  ORDER BY  r.created_at", bind);
            List<AvaliacaoProdutoListDTO> retornoFormatado = new ArrayList<>();
            retornoDesformatado.forEach(avaliacao -> retornoFormatado.add(new AvaliacaoProdutoListDTO(avaliacao)));
            return retornoFormatado;
        } catch (Exception ex) {
            throw new LojaException("Erro ao recuperar registros do banco: " + ex.getMessage());
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }
}
