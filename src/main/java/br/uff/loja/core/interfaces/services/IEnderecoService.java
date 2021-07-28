package br.uff.loja.core.interfaces.services;

import br.uff.loja.core.dtos.EnderecoDTO;

public interface IEnderecoService {
    public EnderecoDTO encontraPorId(Integer id);
}
