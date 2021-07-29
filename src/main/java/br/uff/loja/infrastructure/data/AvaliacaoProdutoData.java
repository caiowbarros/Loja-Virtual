package br.uff.loja.infrastructure.data;

import java.util.ArrayList;
import java.util.HashMap;

import br.uff.loja.core.dtos.AvaliacaoProdutoInsertDTO;
import br.uff.loja.core.dtos.AvaliacaoProdutoListDTO;
import br.uff.loja.core.interfaces.data.IAvaliacaoProdutoData;
import br.uff.loja.infrastructure.database.MySQLDAO;

public class AvaliacaoProdutoData implements IAvaliacaoProdutoData {
    private final MySQLDAO mysqlDAO;

    public AvaliacaoProdutoData() {
        this.mysqlDAO = new MySQLDAO();
    }

    @Override
    public Boolean jaFoiAvaliado(Integer usuarioId, Integer produtoId) throws Exception {
        try {
            Object[] bind = {usuarioId, produtoId};
            Integer qtdAvaliacoes = Integer.valueOf(String.valueOf(this.mysqlDAO.dbValor("count(*)", "user_produts_rating", "user_id=? AND product_id=?", bind)));
            return qtdAvaliacoes > 0;
        } catch (Exception e) {
            throw new Exception("Falha ao recuperar quantidade de avaliacoes do usuário para o produto: " + e.getMessage());
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Integer insereAvaliacaoDoProduto(AvaliacaoProdutoInsertDTO avaliacaoProdutoInsertDTO) throws Exception {
        try {
            Object[] bind = {avaliacaoProdutoInsertDTO.usuarioId, avaliacaoProdutoInsertDTO.produtoId, avaliacaoProdutoInsertDTO.avaliacao, avaliacaoProdutoInsertDTO.descricao, avaliacaoProdutoInsertDTO.titulo};
            return this.mysqlDAO.dbGrava("INSERT INTO user_produts_rating (user_id,product_id,rating,description,title,created_at) VALUES (?,?,?,?,?,SYSDATE())", bind, false);
        } catch (Exception e) {
            throw new Exception("Falha ao Inserir a Avaliação do usuário de id: " + avaliacaoProdutoInsertDTO.usuarioId + " para o Produto de id: " + avaliacaoProdutoInsertDTO.produtoId + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public ArrayList<AvaliacaoProdutoListDTO> recuperaAvaliacoesDeUmProduto(Integer produtoId) throws Exception {
        try {
            Object[] bind = {produtoId};
            ArrayList<HashMap<String, Object>> retornoDesformatado = this.mysqlDAO.dbCarrega("SELECT  u.`name` AS avaliador,  r.title AS avaliacaoTitulo,  r.rating AS avaliacao,  r.description AS avaliacaoDescricao,  DATE_FORMAT(r.created_at, '%M %d, %Y') AS avaliacaoData,  DATE_FORMAT(r.created_at, '%d/%m/%Y') AS avaliacaoDataSimples  FROM  user_produts_rating r  LEFT JOIN users u ON (r.user_id = u.id)  WHERE  r.product_id = ?  ORDER BY  r.created_at", bind);
            ArrayList<AvaliacaoProdutoListDTO> retornoFormatado = new ArrayList<AvaliacaoProdutoListDTO>();
            retornoDesformatado.forEach((avaliacao) -> {
                AvaliacaoProdutoListDTO novaAvaliacao = new AvaliacaoProdutoListDTO();
                novaAvaliacao.avaliador = String.valueOf(avaliacao.get("avaliador"));
                novaAvaliacao.avaliacaoTitulo = String.valueOf(avaliacao.get("avaliacaoTitulo"));
                novaAvaliacao.avaliacao = Integer.valueOf(String.valueOf(avaliacao.get("avaliacao")));
                novaAvaliacao.avaliacaoDescricao = String.valueOf(avaliacao.get("avaliacaoDescricao"));
                novaAvaliacao.avaliacaoData = String.valueOf(avaliacao.get("avaliacaoData"));
                novaAvaliacao.avaliacaoDataSimples = String.valueOf(avaliacao.get("avaliacaoDataSimples"));
                retornoFormatado.add(novaAvaliacao);
            });
            return retornoFormatado;
        } catch (Exception ex) {
            throw new Exception("Erro ao recuperar registros do banco: " + ex.getMessage());
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }
}
