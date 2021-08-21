package br.uff.loja.core.interfaces.services;

import java.util.List;

import br.uff.loja.core.dtos.VendaDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface IVendaService {

    public List<VendaDTO> listaVendasDoUsuario(Integer usuarioId) throws LojaException;

    public void gravaVenda(Integer usuarioId, Integer carrinhoId, Integer enderecoId) throws LojaException;
}
