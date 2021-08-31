package br.uff.loja.infrastructure.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.uff.loja.core.dtos.PaginateDTO;
import br.uff.loja.core.dtos.VendaDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.IVendaData;
import br.uff.loja.infrastructure.database.MySQLDAO;

public class VendaData implements IVendaData {

    private final MySQLDAO mysqlDAO;

    private static final String CONSULTAVENDASDOUSUARIO = "SELECT id, cart_id AS carrinhoId, total_price AS precoTotal, created_at AS criadoEm, address_id AS enderecoId, user_id AS usuarioId FROM sales WHERE user_id=? ORDER BY id DESC";

    public VendaData() {
        this.mysqlDAO = new MySQLDAO();
    }

    @Override
    public List<VendaDTO> listaVendasDoUsuario(Integer usuarioId) throws LojaException {
        try {
            Object[] bind = {usuarioId};
            List<HashMap<String, Object>> retornoDesformatado = this.mysqlDAO.dbCarrega(CONSULTAVENDASDOUSUARIO, bind);
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
            Object[] bind = {carrinhoId, enderecoId};
            this.mysqlDAO.dbGrava("CALL buy_cart_itens(?, ?)", bind, false);
        } catch (Exception e) {
            throw new LojaException("Falha ao realizar a venda. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }

    @Override
    public PaginateDTO<List<VendaDTO>> listaVendasDoUsuario(Integer usuarioId, Integer itensPorPagina, Integer paginaAtual) throws LojaException {
        try {
            Object[] bind = {usuarioId};

            // define offset
            String offset = "";
            if (paginaAtual > 1) {
                Integer calcOffset = (paginaAtual - 1) * itensPorPagina;
                offset = " OFFSET " + calcOffset + " ";
            }

            String limit = " LIMIT " + itensPorPagina + " ";

            List<HashMap<String, Object>> retornoDesformatado = this.mysqlDAO.dbCarrega(CONSULTAVENDASDOUSUARIO + limit + offset, bind);
            List<VendaDTO> retornoFormatado = new ArrayList<>();
            retornoDesformatado.forEach(venda -> retornoFormatado.add(new VendaDTO(venda)));

            Integer ultimaPagina = Integer.valueOf(String.valueOf(this.mysqlDAO.dbValor("ceil(count(*)/" + itensPorPagina + ")", CONSULTAVENDASDOUSUARIO, "", bind)));

            return new PaginateDTO<>(paginaAtual, retornoFormatado, ultimaPagina);
        } catch (Exception e) {
            throw new LojaException("Falha ao Listar as Vendas do usuário de id: " + usuarioId + ". (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }
}
