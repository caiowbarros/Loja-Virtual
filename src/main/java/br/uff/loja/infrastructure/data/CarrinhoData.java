package br.uff.loja.infrastructure.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.uff.loja.core.dtos.CarrinhoDTO;
import br.uff.loja.core.dtos.CarrinhoProdutoDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.ICarrinhoData;
import br.uff.loja.infrastructure.database.MySQLDAO;

public class CarrinhoData implements ICarrinhoData {
    private final MySQLDAO mysqlDAO;

    private static final String COUNTSTRING = "count(*)";
    private static final String CARTTABLESTRING = "carts";
    private static final String CARTSPRODUCTSTABLESTRING = "carts_products";
    private static final String ERROPREFIXOSTRING = "Falha ao verificar se o Carrinho de id: ";
    private static final String ERROESPECIFICANDOCARRINHO = " no Carrinho de id: ";

    public CarrinhoData() {
        this.mysqlDAO = new MySQLDAO();
    }

    @Override
    public CarrinhoDTO encontraCarrinho(Integer id) throws LojaException {
        try {
            Object[] bind = {id};
            List<HashMap<String, Object>> retorno = this.mysqlDAO.dbCarrega("SELECT id, user_id AS usuarioId, created_at AS criadoEm, ip FROM carts WHERE id=?", bind);
            return new CarrinhoDTO(retorno.get(0));
        } catch (Exception e) {
            throw new LojaException("Falha ao Recuperar o Carrinho de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public List<CarrinhoDTO> listaCarrinhosDoUsuarioNaoVendidos(Integer usuarioId) throws LojaException {
        try {
            Object[] bind = {usuarioId};
            List<HashMap<String, Object>> retornoDesformatado = this.mysqlDAO.dbCarrega("SELECT id, user_id AS usuarioId, created_at AS criadoEm, ip FROM carts WHERE user_id=? AND id NOT IN (SELECT cart_id FROM sales)", bind);
            List<CarrinhoDTO> retornoFormatado = new ArrayList<>();
            retornoDesformatado.forEach(carrinho -> retornoFormatado.add(new CarrinhoDTO(carrinho)));
            return retornoFormatado;
        } catch (Exception e) {
            throw new LojaException("Falha ao Listar os Carrinhos não vendidos do usuário de id: " + usuarioId + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Boolean carrinhoVendido(Integer id) throws LojaException {
        try {
            Object[] bind = {id};
            return Integer.valueOf(String.valueOf(this.mysqlDAO.dbValor(COUNTSTRING, CARTTABLESTRING, "id=? AND id IN (SELECT cart_id FROM sales)", bind))) > 0;
        } catch (Exception e) {
            throw new LojaException(ERROPREFIXOSTRING + id + " já foi vendido. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public void defineDonoDeUmCarrinhoSemUsuarioNoMomento(Integer id, Integer usuarioId) throws LojaException {
        try {
            Object[] bind = {usuarioId,id};
            this.mysqlDAO.dbGrava("UPDATE carts SET user_id=? WHERE id=? AND user_id IS NULL", bind, false);
        } catch (Exception e) {
            throw new LojaException("Falha ao Atualizar o Dono do Carrinho de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Boolean carrinhoSemDono(Integer id) throws LojaException {
        try {
            Object[] bind = {id};
            return Integer.valueOf(String.valueOf(this.mysqlDAO.dbValor(COUNTSTRING, CARTTABLESTRING, "id=? AND user_id IS NULL", bind))) > 0;
        } catch (Exception e) {
            throw new LojaException(ERROPREFIXOSTRING + id + " está sem dono. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Boolean carrinhoDoUsuario(Integer id, Integer usuarioId) throws LojaException {
        try {
            Object[] bind = {id,usuarioId};
            return Integer.valueOf(String.valueOf(this.mysqlDAO.dbValor(COUNTSTRING, CARTTABLESTRING, "id=? AND user_id=?", bind))) > 0;
        } catch (Exception e) {
            throw new LojaException(ERROPREFIXOSTRING + id + " é do usuário de id: " + usuarioId + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Boolean carrinhoExiste(Integer id) throws LojaException {
        try {
            Object[] bind = {id};
            return Integer.valueOf(String.valueOf(this.mysqlDAO.dbValor(COUNTSTRING, CARTTABLESTRING, "id=?", bind))) > 0;
        } catch (Exception e) {
            throw new LojaException(ERROPREFIXOSTRING + id + " existe. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Integer quantidadeProdutosCarrinho(Integer id) throws LojaException {
        try {
            Object[] bind = {id};
            return Integer.valueOf(String.valueOf(this.mysqlDAO.dbValor(COUNTSTRING, CARTSPRODUCTSTABLESTRING, "cart_id=?", bind)));
        } catch (Exception e) {
            throw new LojaException("Falha ao verificar a quantidade de produtos no Carrinho de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Integer quantidadeProdutoNoCarrinho(Integer id, Integer produtoId) throws LojaException {
        try {
            Object[] bind = {produtoId,id};
            String quantity = String.valueOf(this.mysqlDAO.dbValor("quantity", CARTSPRODUCTSTABLESTRING, "product_id=? AND cart_id=?", bind));
            return Integer.valueOf(quantity.equals("null") ? "0" : quantity);
        } catch (Exception e) {
            throw new LojaException("Falha ao verificar a quantidade do produto de id: " + produtoId +  ERROESPECIFICANDOCARRINHO + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public void criaCarrinho(String ip, String criadoEm, Integer usuarioId) throws LojaException {
        try {
            Object[] bind = {ip,criadoEm,usuarioId};
            this.mysqlDAO.dbGrava("INSERT INTO carts (ip,created_at,user_id) VALUES (?,?,?)", bind, false);
        } catch (Exception e) {
            throw new LojaException("Falha ao Inserir o Carrinho de para o Usuário de id: " + usuarioId + " e ip: " + ip + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public CarrinhoDTO encontraCarrinho(String ip, String criadoEm, Integer usuarioId) throws LojaException {
        try {
            Object[] bind = {ip,criadoEm,usuarioId};
            List<HashMap<String, Object>> retorno = this.mysqlDAO.dbCarrega("SELECT id, user_id AS usuarioId, created_at AS criadoEm, ip FROM carts WHERE ip=? AND created_at=? AND user_id" + (usuarioId == null ? " IS NULL " : "=?") + " ORDER BY id DESC LIMIT 1", bind);
            return new CarrinhoDTO(retorno.get(0));
        } catch (Exception e) {
            throw new LojaException("Falha ao Recuperar o Carrinho de ip: " + ip + " do usuário de id: " + usuarioId + " que foi criado em " + criadoEm + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public List<CarrinhoProdutoDTO> listaProdutosCarrinho(Integer id) throws LojaException {
        try {
            Object[] bind = {id};
            List<HashMap<String, Object>> retornoDesformatado = this.mysqlDAO.dbCarrega("SELECT c.cart_id AS id, p.id AS produtoId, p.name AS nome, p.description AS descricao, p.img AS imagem, p.price AS preco, p.quantity AS quantidadeEmEstoque, c.quantity AS quantidade, p.price * c.quantity AS precoTotal FROM carts_products c LEFT JOIN products p ON (c.product_id = p.id) WHERE c.cart_id=?", bind);
            List<CarrinhoProdutoDTO> retornoFormatado = new ArrayList<>();
            retornoDesformatado.forEach(carrinho -> retornoFormatado.add(new CarrinhoProdutoDTO(carrinho)));
            return retornoFormatado;
        } catch (Exception e) {
            throw new LojaException("Falha ao Listar os Produtos do Carrinho de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Boolean verificaProdutoNoCarrinho(Integer id, Integer produtoId) throws LojaException {
        try {
            Object[] bind = {produtoId,id};
            return Integer.valueOf(String.valueOf(this.mysqlDAO.dbValor(COUNTSTRING, CARTSPRODUCTSTABLESTRING, "product_id=? AND cart_id=?", bind))) > 0;
        } catch (Exception e) {
            throw new LojaException("Falha ao verificar se o produto de id: " + produtoId + " está no Carrinho de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public void adicionaProdutoNoCarrinho(Integer id, Integer produtoId) throws LojaException {
        try {
            Object[] bind = {produtoId,id};
            this.mysqlDAO.dbGrava("INSERT INTO carts_products (product_id,cart_id,quantity) VALUES (?,?,1)", bind, false);
        } catch (Exception e) {
            throw new LojaException("Falha ao Inserir o Produto de id: " + produtoId + ERROESPECIFICANDOCARRINHO + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public void removeProdutoDoCarrinho(Integer id, Integer produtoId) throws LojaException {
        try {
            Object[] bind = {produtoId,id};
            this.mysqlDAO.dbGrava("DELETE FROM carts_products WHERE product_id=? AND cart_id=?", bind, false);
        } catch (Exception e) {
            throw new LojaException("Falha ao Remover o Produto de id: " + produtoId + ERROESPECIFICANDOCARRINHO + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public void atualizaQtdDoProdutoNoCarrinho(Integer id, Integer produtoId, Integer novaQtd) throws LojaException {
        try {
            Object[] bind = {novaQtd,produtoId,id};
            this.mysqlDAO.dbGrava("UPDATE carts_products SET quantity=? WHERE product_id=? AND cart_id=?", bind, false);
        } catch (Exception e) {
            throw new LojaException("Falha ao Atualizar quantidade do Produto de id: " + produtoId + ERROESPECIFICANDOCARRINHO + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public Double recuperaPrecoTotalDeUmCarrinho(Integer id) throws LojaException {
        try {
            Object[] bind = {id};
            String totalPrice = String.valueOf(this.mysqlDAO.dbValor("total_price", "SELECT sum(p.price * c.quantity) AS precoTotal FROM carts_products c LEFT JOIN products p ON (c.product_id = p.id) WHERE c.cart_id=? GROUP BY c.cart_id", "", bind));
            return Double.valueOf(totalPrice.equals("null") ? "0" : totalPrice);
        } catch (Exception e) {
            throw new LojaException("Falha ao verificar o valor total do carinho de id: " + id + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }
    
}
