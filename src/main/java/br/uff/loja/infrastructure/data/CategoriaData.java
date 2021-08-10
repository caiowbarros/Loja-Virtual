package br.uff.loja.infrastructure.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.uff.loja.core.dtos.CategoriaDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.ICategoriaData;
import br.uff.loja.infrastructure.database.MySQLDAO;

public class CategoriaData implements ICategoriaData {
    private final MySQLDAO mysqlDAO;

    public CategoriaData() {
        this.mysqlDAO = new MySQLDAO();
    }

    @Override
    public List<CategoriaDTO> listaCategorias() throws LojaException {
        try {
            Object[] bind = {};
            List<HashMap<String, Object>> retornoDesformatado = this.mysqlDAO.dbCarrega("SELECT id, category_name AS nome FROM vw_category", bind);
            List<CategoriaDTO> retornoFormatado = new ArrayList<>();
            retornoDesformatado.forEach(categoria -> retornoFormatado.add(new CategoriaDTO(categoria)));
            return retornoFormatado;
        } catch (Exception e) {
            throw new LojaException("Falha ao Listar as Categorias. (" + e.getMessage() + ")");
        } finally {
            this.mysqlDAO.destroyDb();
        }
    }
    
}
