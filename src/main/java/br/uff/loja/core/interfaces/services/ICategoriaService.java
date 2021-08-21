package br.uff.loja.core.interfaces.services;

import java.util.List;

import br.uff.loja.core.dtos.CategoriaDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface ICategoriaService {

    public List<CategoriaDTO> listaCategorias() throws LojaException;
}
