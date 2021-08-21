package br.uff.loja.infrastructure.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.uff.loja.core.dtos.VendaDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.IVendaData;
import br.uff.loja.infrastructure.database.MySQLDAO;

public class VendaData implements IVendaData {
    private final MySQLDAO mysqlDAO;

    public VendaData() {
        this.mysqlDAO = new MySQLDAO();
    }

    @Override
    public List<VendaDTO> listaVendasDoUsuario(Integer usuarioId) throws LojaException {
        try {
            Object[] bind = {usuarioId};
            List<HashMap<String, Object>> retornoDesformatado = this.mysqlDAO.dbCarrega("SELECT id, cart_id AS carrinhoId, total_price AS precoTotal, created_at AS criadoEm, address_id AS enderecoId, user_id AS usuarioId FROM sales WHERE user_id=? ORDER BY id DESC", bind);
            List<VendaDTO> retornoFormatado = new ArrayList<>();
            retornoDesformatado.forEach(venda -> retornoFormatado.add(new VendaDTO(venda)));
            return retornoFormatado;
        } catch (Exception e) {
            throw new LojaException("Falha ao Listar as Vendas do usuário de id: " + usuarioId + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public void gravaVenda(Integer carrinhoId, Integer enderecoId) throws LojaException {
        try {
            Object[] bind = {carrinhoId,enderecoId};
            this.mysqlDAO.dbGrava("CALL buy_cart_itens(?, ?)", bind, false);
        } catch (Exception e) {
            throw new LojaException("Falha ao realizar a venda. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }
    
    @Override
    public Boolean enderecoFoiUsadoEmAlgumaVenda(Integer enderecoId) throws LojaException {
        try {
            Object[] bind = {enderecoId};
            return Integer.valueOf(String.valueOf(this.mysqlDAO.dbValor("count(*)","sales","address_id=?", bind))) > 0;
        } catch (Exception e) {
            throw new LojaException("Falha ao Verificar se o Endereço de id: " + enderecoId + " foi usado em alguma Venda. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }
}
