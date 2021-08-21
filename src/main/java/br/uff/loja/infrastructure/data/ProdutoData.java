package br.uff.loja.infrastructure.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.uff.loja.core.dtos.FiltraProdutoDTO;
import br.uff.loja.core.dtos.PaginateDTO;
import br.uff.loja.core.dtos.ProdutoDTO;
import br.uff.loja.core.dtos.ProdutoHomeDTO;
import br.uff.loja.core.dtos.ProdutoListaDTO;
import br.uff.loja.core.dtos.ProdutoVitrineUsuarioDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.IProdutoData;
import br.uff.loja.infrastructure.database.MySQLDAO;

public class ProdutoData implements IProdutoData {

    private final MySQLDAO mysqlDAO;
    private static final String CONSULTAPRODUTO = "SELECT p.id AS id, p.name AS nome, p.price AS preco, p.description AS descricao,p.img AS imagem, p.category_id AS categoriaId, p.quantity AS quantidade, c.category_name AS categoria FROM products p LEFT JOIN vw_category c ON (p.category_id = c.id)";

    public ProdutoData() {
        this.mysqlDAO = new MySQLDAO();
    }

    @Override
    public ProdutoDTO encontraProdutoPorId(Integer id) throws LojaException {
        try {
            Object[] bind = {id};
            List<HashMap<String, Object>> retorno = this.mysqlDAO.dbCarrega(CONSULTAPRODUTO + " WHERE p.id=?", bind);
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
            List<HashMap<String, Object>> retornoDesformatado = this.mysqlDAO.dbCarrega(CONSULTAPRODUTO, bind);
            List<ProdutoDTO> retornoFormatado = new ArrayList<>();
            retornoDesformatado.forEach(produto -> retornoFormatado.add(new ProdutoDTO(produto)));
            return retornoFormatado;
        } catch (Exception e) {
            throw new LojaException("Falha ao Listar os Produtos para ADMs. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public PaginateDTO<List<ProdutoListaDTO>> listaProdutosVitrine(FiltraProdutoDTO filtroProduto) throws LojaException {
        try {
            ArrayList<Object> bind = new ArrayList<>();
            String consulta = "SELECT p.id AS id,p.name AS nome,p.price AS preco,p.img AS imagem,c.category_name AS categoria FROM products p LEFT JOIN vw_category c on(p.category_id=c.id) ";

            String filtro = "";
            String limit = " LIMIT " + filtroProduto.getItensPorPagina() + " ";

            String whereTxt = " WHERE ";
            String andTXT = " AND ";
            String orTxt = " OR ";

            // filtra favoritos
            if (Boolean.TRUE.equals(filtroProduto.getApenasFavoritados())) {
                // se tem usuario logado mostra filtra produtos por favoritos
                if (filtroProduto.getUsuarioId() != null) {
                    filtro += (filtro.equals("") ? whereTxt : andTXT) + " p.id in (SELECT f.product_id FROM favorite_products f WHERE f.user_id=?) ";
                    bind.add(filtroProduto.getUsuarioId());
                } else {
                    throw new LojaException("Sem usuário logado, para buscar produtos favoritos realize login.");
                }
            }

            // filtra categorias
            if (filtroProduto.getCategorias() != null && !filtroProduto.getCategorias().isEmpty()) {
                StringBuilder categoriaFiltro = new StringBuilder();
                for (String value : filtroProduto.getCategorias()) {
                    bind.add("%" + value.toUpperCase() + "%");
                    bind.add(value);
                    categoriaFiltro.append((categoriaFiltro.toString().equals("") ? " ( " : orTxt) + " UPPER(c.category_name) LIKE ? OR c.id = ? ");
                }
                categoriaFiltro.append((categoriaFiltro.toString().equals("") ? "" : " ) "));
                filtro += (filtro.equals("") ? whereTxt : andTXT) + categoriaFiltro.toString();
            }

            // filtra sub categorias
            if (filtroProduto.getSubCategorias() != null && !filtroProduto.getSubCategorias().isEmpty()) {
                StringBuilder subCategoriaFiltro = new StringBuilder();
                for (String value : filtroProduto.getSubCategorias()) {
                    bind.add("%" + value.toUpperCase() + "%");
                    bind.add(value);
                    subCategoriaFiltro.append((subCategoriaFiltro.toString().equals("") ? " ( " : orTxt) + " UPPER(c.category_name) LIKE ? OR c.id = ? ");
                }
                subCategoriaFiltro.append((subCategoriaFiltro.toString().equals("") ? "" : " ) "));
                filtro += (filtro.equals("") ? whereTxt : andTXT) + subCategoriaFiltro.toString();
            }

            if (Boolean.TRUE.equals(filtroProduto.getApenasLancamentos())) {
                filtro += (filtro.equals("") ? whereTxt : andTXT) + " p.created_at >= DATE_SUB(NOW(), INTERVAL 1 MONTH) ";
            }

            if (filtroProduto.getPesquisa() != null) {
                bind.add("%" + filtroProduto.getPesquisa().toUpperCase() + "%");
                bind.add("%" + filtroProduto.getPesquisa().toUpperCase() + "%");
                filtro += (filtro.equals("") ? whereTxt : andTXT) + " (UPPER(p.name) LIKE ? OR UPPER(p.description) LIKE ?) ";
            }

            //recupera apenas produtos que tem no estoque
            filtro += (filtro.equals("") ? whereTxt : andTXT) + " p.quantity>0 ";

            // filtra menor preco
            if (filtroProduto.getPrecoMinimo() != null) {
                bind.add(filtroProduto.getPrecoMinimo());
                filtro += (filtro.equals("") ? whereTxt : andTXT) + " p.price >= ? ";
            }

            // filtra maior preco
            if (filtroProduto.getPrecoMaximo() != null) {
                bind.add(filtroProduto.getPrecoMaximo());
                filtro += (filtro.equals("") ? whereTxt : andTXT) + " p.price <= ? ";
            }

            // define offset
            String offset = "";
            if (filtroProduto.getPaginaAtual() > 1) {
                Integer calcOffset = (filtroProduto.getPaginaAtual() - 1) * filtroProduto.getItensPorPagina();
                offset = " OFFSET " + calcOffset + " ";
            }

            List<HashMap<String, Object>> retornoDesformatado = this.mysqlDAO.dbCarrega(consulta + filtro + limit + offset, bind.toArray());
            List<ProdutoListaDTO> retornoFormatado = new ArrayList<>();
            retornoDesformatado.forEach(produto -> retornoFormatado.add(new ProdutoListaDTO(produto)));

            Integer ultimaPagina = Integer.valueOf(String.valueOf(this.mysqlDAO.dbValor("ceil(count(*)/" + filtroProduto.getItensPorPagina() + ")", consulta + filtro, "", bind.toArray())));

            return new PaginateDTO<>(filtroProduto.getPaginaAtual(), retornoFormatado, ultimaPagina);
        } catch (Exception e) {
            throw new LojaException("Falha ao Listar os Produtos. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public ProdutoVitrineUsuarioDTO mostraProdutoVitrineParaUsuario(Integer id, Integer usuarioId) throws LojaException {
        try {
            Object[] bind = {String.valueOf(usuarioId), String.valueOf(usuarioId), id};
            List<HashMap<String, Object>> retorno = this.mysqlDAO.dbCarrega("SELECT p.id, p.name AS nome, p.quantity AS quantidade, p.description AS descricao, p.price AS preco, p.img AS imagem, c.category_name AS categoria, (SELECT count(*) FROM favorite_products f WHERE f.product_id = p.id AND f.user_id=?) favoritoDoUsuario, (SELECT f.rating FROM user_produts_rating f WHERE f.product_id = p.id AND f.user_id=?) avaliacaoDadaPeloUsuario, (SELECT count(*) FROM user_produts_rating f WHERE f.product_id = p.id) quantidadeAvaliacoes, (SELECT COALESCE(sum(f.rating),0) FROM user_produts_rating f WHERE f.product_id = p.id) somaAvaliacoes, (SELECT count(*) FROM user_produts_rating f WHERE f.product_id = p.id AND f.rating='1') quantidadeAvaliacoesNota1, (SELECT count(*) FROM user_produts_rating f WHERE f.product_id = p.id AND f.rating='2') quantidadeAvaliacoesNota2, (SELECT count(*) FROM user_produts_rating f WHERE f.product_id = p.id AND f.rating='3') quantidadeAvaliacoesNota3, (SELECT count(*) FROM user_produts_rating f WHERE f.product_id = p.id AND f.rating='4') quantidadeAvaliacoesNota4, (SELECT count(*) FROM user_produts_rating f WHERE f.product_id = p.id AND f.rating='5') quantidadeAvaliacoesNota5 FROM products p LEFT JOIN vw_category c ON (p.category_id = c.id) WHERE p.id=?", bind);
            return new ProdutoVitrineUsuarioDTO(retorno.get(0));
        } catch (Exception e) {
            throw new LojaException("Falha ao Listar os Produtos. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Boolean produtoFavoritadoPeloUsuario(Integer produtoId, Integer usuarioId) throws LojaException {
        try {
            Object[] bind = {produtoId, usuarioId};
            return Integer.valueOf(String.valueOf(this.mysqlDAO.dbValor("count(*)", "favorite_products", "product_id=? AND user_id=?", bind))) > 0;
        } catch (Exception e) {
            throw new LojaException("Falha ao verificar se o produto de id:" + produtoId + " já foi favoritado pelo usuário de id: " + usuarioId + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public void removeFavoritacaoProdutoPeloUsuario(Integer produtoId, Integer usuarioId) throws LojaException {
        try {
            Object[] bind = {produtoId, usuarioId};
            this.mysqlDAO.dbGrava("DELETE FROM favorite_products WHERE product_id=? AND user_id=?", bind, false);
        } catch (Exception e) {
            throw new LojaException("Falha ao desfavoritar o produto de id:" + produtoId + " pelo usuário de id: " + usuarioId + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public void adicionaFavoritacaoProdutoPeloUsuario(Integer produtoId, Integer usuarioId) throws LojaException {
        try {
            Object[] bind = {produtoId, usuarioId};
            this.mysqlDAO.dbGrava("INSERT INTO favorite_products (product_id,user_id) VALUES (?,?)", bind, false);
        } catch (Exception e) {
            throw new LojaException("Falha ao favoritar o produto de id:" + produtoId + " pelo usuário de id: " + usuarioId + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public List<ProdutoHomeDTO> listaProdutosBanner() throws LojaException {
        try {
            Object[] bind = {};
            List<HashMap<String, Object>> retornoDesformatado = this.mysqlDAO.dbCarrega("SELECT id, name AS nome, description AS descricao, img AS imagem FROM products WHERE quantity > 0 ORDER BY quantity DESC LIMIT 3", bind);
            List<ProdutoHomeDTO> retornoFormatado = new ArrayList<>();
            retornoDesformatado.forEach(produto -> retornoFormatado.add(new ProdutoHomeDTO(produto)));
            return retornoFormatado;
        } catch (Exception e) {
            throw new LojaException("Falha ao listar produtos do banner da Home. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }
}
