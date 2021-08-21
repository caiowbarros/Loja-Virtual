package br.uff.loja.core.interfaces.data;

import java.util.List;

import br.uff.loja.core.dtos.PaginateDTO;
import br.uff.loja.core.dtos.VendaDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface IVendaData {

    public List<VendaDTO> listaVendasDoUsuario(Integer usuarioId) throws LojaException;

    public PaginateDTO<List<VendaDTO>> listaVendasDoUsuario(Integer usuarioId, Integer itensPorPagina, Integer paginaAtual) throws LojaException;

    public void gravaVenda(Integer carrinhoId, Integer enderecoId) throws LojaException;

    public Boolean enderecoFoiUsadoEmAlgumaVenda(Integer enderecoId) throws LojaException;
}
