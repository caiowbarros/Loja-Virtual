package br.uff.loja.infrastructure.data;

import java.util.List;

import br.uff.loja.core.dtos.VendaDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.IVendaData;

public class VendaData implements IVendaData {

    @Override
    public List<VendaDTO> listaVendasDoUsuario(Integer usuarioId) throws LojaException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void gravaVenda(Integer carrinhoId, Integer enderecoId) throws LojaException {
        // TODO Auto-generated method stub
        
    }
    
}
