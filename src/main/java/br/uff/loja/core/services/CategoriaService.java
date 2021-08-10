package br.uff.loja.core.services;

import java.util.List;

import br.uff.loja.core.dtos.CategoriaDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.ICategoriaData;
import br.uff.loja.core.interfaces.services.ICategoriaService;
import br.uff.loja.infrastructure.data.CategoriaData;

public class CategoriaService implements ICategoriaService {
    private ICategoriaData categoriaData;

    public CategoriaService() {
        categoriaData = new CategoriaData();
    }
    @Override
    public List<CategoriaDTO> listaCategorias() throws LojaException {
        return categoriaData.listaCategorias();
    }
    
}
