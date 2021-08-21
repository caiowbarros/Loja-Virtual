package br.uff.loja.core.interfaces.data;

import java.util.List;

import br.uff.loja.core.dtos.CategoriaDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface ICategoriaData {

    public List<CategoriaDTO> listaCategorias() throws LojaException;
}
