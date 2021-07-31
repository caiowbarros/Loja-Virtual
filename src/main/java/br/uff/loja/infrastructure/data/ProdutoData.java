package br.uff.loja.infrastructure.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.uff.loja.core.dtos.PaginateDTO;
import br.uff.loja.core.dtos.ProdutoDTO;
import br.uff.loja.core.dtos.ProdutoListaDTO;
import br.uff.loja.core.dtos.ProdutoVitrineUsuarioDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.IProdutoData;
import br.uff.loja.infrastructure.database.MySQLDAO;

public class ProdutoData implements IProdutoData {
    private final MySQLDAO mysqlDAO;

    public ProdutoData() {
        this.mysqlDAO = new MySQLDAO();
    }

    @Override
    public ProdutoDTO encontraProdutoPorId(Integer id) throws LojaException {
        try {
            Object[] bind = {id};
            List<HashMap<String, Object>> retorno = this.mysqlDAO.dbCarrega("SELECT id, name AS nome, price AS preco, description AS descricao,img AS imagem, category_id AS categoryId, quantity AS quantidade FROM products WHERE id=?", bind);
            return new ProdutoDTO(retorno.get(0));
        } catch (Exception e) {
            throw new LojaException("Falha ao Recuperar o Produto de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public void excluiProdutoPorId(Integer id) throws LojaException {
        try {
            Object[] bind = {id};
            this.mysqlDAO.dbGrava("DELETE FROM products WHERE id=?", bind, false);
        } catch (Exception e) {
            throw new LojaException("Falha ao Excluir o Produto de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public void atualizaProdutoPorId(Integer id, ProdutoDTO produto) throws LojaException {
        try {
            Object[] bind = {
                produto.getNome(),
                produto.getPreco(),
                produto.getDescricao(),
                produto.getImagem(),
                produto.getCategoriaId(),
                id
            };
            this.mysqlDAO.dbGrava("UPDATE products SET name=?, price=?, description=?, img=?, category_id=? WHERE id=?", bind, false);
        } catch (Exception e) {
            throw new LojaException("Falha ao Atualizar o Produto de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public void insereQuantidadeEmEstoqueDoProdutoPorId(Integer id, Integer quantidade) throws LojaException {
        try {
            Object[] bind = {
                quantidade,
                id
            };
            this.mysqlDAO.dbGrava("UPDATE products SET quantity = quantity + ? WHERE id=?", bind, false);
        } catch (Exception e) {
            throw new LojaException("Falha ao Inserir estoque do Produto de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public void insereProduto(ProdutoDTO produto) throws LojaException {
        try {
            Object[] bind = {
                produto.getNome(),
                produto.getPreco(),
                produto.getDescricao(),
                produto.getImagem(),
                produto.getCategoriaId()
            };
            this.mysqlDAO.dbGrava("INSERT INTO products (name,price,description,img,category_id,created_at,quantity) VALUES (?,?,?,?,?,SYSDATE(),0)", bind, false);
        } catch (Exception e) {
            throw new LojaException("Falha ao Inserir Produto. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public List<ProdutoDTO> listaProdutosAdm() throws LojaException {
        try {
            Object[] bind = {};
            List<HashMap<String, Object>> retornoDesformatado = this.mysqlDAO.dbCarrega("SELECT id, name AS nome, price AS preco, description AS descricao,img AS imagem, category_id AS categoryId, quantity AS quantidade FROM products", bind);
            List<ProdutoDTO> retornoFormatado = new ArrayList<>();
            retornoDesformatado.forEach(produto -> retornoFormatado.add(new ProdutoDTO(produto)));
            return retornoFormatado;
        } catch (Exception e) {
            throw new LojaException("Falha ao Listar os Produtos. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public PaginateDTO<List<ProdutoListaDTO>> listaProdutosVitrine(Integer paginaAtual, String pesquisa, Double precoMinimo, Double precoMaximo, Boolean apenasFavoritados, Integer usuarioId, Boolean apenasLancamentos, List<String> categorias, List<String> subCategorias) throws LojaException {
        try {
            ArrayList<Object> bind = new ArrayList<>();
            Integer qtdPorPagina = 5;
            String consulta = "SELECT p.id AS id,p.name AS nome,p.price AS preco,p.img AS imagem,c.category_name AS categoria FROM products p LEFT JOIN vw_category c on(p.category_id=c.id) ";
                        
            String filtro = "";
            String limit = " LIMIT " + qtdPorPagina + " ";

            String whereTxt = " WHERE ";
            String andTXT = " AND ";
            String orTxt = " OR ";

            // filtra favoritos
            if (Boolean.TRUE.equals(apenasFavoritados)) {
                // se tem usuario logado mostra filtra produtos por favoritos
                if (usuarioId != null) {
                    filtro += (filtro.equals("") ? whereTxt : andTXT) + " p.id in (SELECT f.product_id FROM favorite_products f WHERE f.user_id=?) ";
                    bind.add(usuarioId);
                } else {
                    throw new LojaException("Sem usuário logado, para buscar produtos favoritos realize login.");
                }
            }

            // filtra categorias
            if (categorias != null && !categorias.isEmpty()) {
                StringBuilder categoriaFiltro = new StringBuilder();
                for (String value : categorias) {
                    bind.add("%" + value.toUpperCase() + "%");
                    bind.add(value);
                    categoriaFiltro.append((categoriaFiltro.toString().equals("") ? " ( " : orTxt) + " UPPER(c.category_name) LIKE ? OR c.id = ? ");
                }
                categoriaFiltro.append((categoriaFiltro.toString().equals("") ? "" : " ) "));
                filtro += (filtro.equals("") ? whereTxt : andTXT) + categoriaFiltro.toString();
            }

            // filtra sub categorias
            if (subCategorias != null && !subCategorias.isEmpty()) {
                StringBuilder subCategoriaFiltro = new StringBuilder();
                for (String value : subCategorias) {
                    bind.add("%" + value.toUpperCase() + "%");
                    bind.add(value);
                    subCategoriaFiltro.append((subCategoriaFiltro.toString().equals("") ? " ( " : orTxt) + " UPPER(c.category_name) LIKE ? OR c.id = ? ");
                }
                subCategoriaFiltro.append((subCategoriaFiltro.toString().equals("") ? "" : " ) "));
                filtro += (filtro.equals("") ? whereTxt : andTXT) + subCategoriaFiltro.toString();
            }
            
            if (Boolean.TRUE.equals(apenasLancamentos)) {
                filtro += (filtro.equals("") ? whereTxt : andTXT) + " p.created_at >= DATE_SUB(NOW(), INTERVAL 1 MONTH) ";
            }

            if (pesquisa != null) {
                bind.add("%" + pesquisa.toUpperCase() + "%");
                bind.add("%" + pesquisa.toUpperCase() + "%");
                filtro += (filtro.equals("") ? whereTxt : andTXT) + " (UPPER(p.name) LIKE ? OR UPPER(p.description) LIKE ?) ";
            }

            //recupera apenas produtos que tem no estoque
            filtro += (filtro.equals("") ? whereTxt : andTXT) + " p.quantity>0 ";

            // filtra menor preco
            if (precoMinimo != null) {
                bind.add(precoMinimo);
                filtro += (filtro.equals("") ? whereTxt : andTXT) + " p.price >= ? ";
            }

            // filtra maior preco
            if (precoMaximo != null) {
                bind.add(precoMaximo);
                filtro += (filtro.equals("") ? whereTxt : andTXT) + " p.price <= ? ";
            }

            // define offset
            String offset = "";
            if (paginaAtual > 1) {
                Integer calcOffset = (paginaAtual - 1) * qtdPorPagina;
                offset = " OFFSET " + calcOffset + " ";
            }

            List<HashMap<String, Object>> retornoDesformatado = this.mysqlDAO.dbCarrega(consulta + filtro + limit + offset, bind.toArray());
            List<ProdutoListaDTO> retornoFormatado = new ArrayList<>();
            retornoDesformatado.forEach(produto -> retornoFormatado.add(new ProdutoListaDTO(produto)));
        
            Integer ultimaPagina = Integer.valueOf(String.valueOf(this.mysqlDAO.dbValor("ceil(count(*)/" + qtdPorPagina + ")", consulta + filtro, "", bind.toArray())));

            if(paginaAtual < 1 || paginaAtual > ultimaPagina) {
                throw new LojaException("O número da página inválido, a página deve estar entre o intervalo: 1-" + ultimaPagina + ".");
            }

            return new PaginateDTO<>(paginaAtual,retornoFormatado,ultimaPagina);
        } catch (Exception e) {
            throw new LojaException("Falha ao Listar os Produtos. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public ProdutoVitrineUsuarioDTO mostraProdutoVitrineParaUsuario(Integer id, Integer usuarioId)
            throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean produtoFavoritadoPeloUsuario(Integer produtoId, Integer usuarioId) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeFavoritacaoProdutoPeloUsuario(Integer produtoId, Integer usuarioId) throws LojaException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void adicionaFavoritacaoProdutoPeloUsuario(Integer produtoId, Integer usuarioId) throws LojaException {
        // TODO Auto-generated method stub
        
    }


}
