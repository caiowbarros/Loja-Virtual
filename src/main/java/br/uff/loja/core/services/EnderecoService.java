package br.uff.loja.core.services;

import java.util.List;

import br.uff.loja.core.dtos.EnderecoDTO;
import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.data.IEnderecoData;
import br.uff.loja.core.interfaces.services.IEnderecoService;
import br.uff.loja.core.interfaces.services.IVendaService;
import br.uff.loja.infrastructure.data.EnderecoData;

public class EnderecoService implements IEnderecoService {

    private IEnderecoData enderecoData;

    public EnderecoService() {
        enderecoData = new EnderecoData();
    }

    @Override
    public EnderecoDTO encontraEnderecoPorId(Integer id) throws LojaException {
        return enderecoData.encontraEnderecoPorId(id);
    }

    @Override
    public void excluiEnderecoPorId(Integer id) throws LojaException {
        if (Boolean.TRUE.equals(enderecoData.enderecoFoiUsadoEmAlgumaVenda(id))) {
            throw new LojaException("Esse endereço não pode ser excluído pois foi usado para realizar uma venda...");
        }
        enderecoData.excluiEnderecoPorId(id);
    }

    @Override
    public EnderecoDTO atualizaEnderecoPorId(Integer id, EnderecoDTO endereco) throws LojaException {
        enderecoData.atualizaEnderecoPorId(id, endereco);
        return this.encontraEnderecoPorId(id);
    }

    @Override
    public void insereEndereco(EnderecoDTO endereco) throws LojaException {
        enderecoData.insereEndereco(endereco);
    }

    @Override
    public List<EnderecoDTO> listaEnderecosPorUsuarioId(Integer usuarioId) throws LojaException {
        return enderecoData.listaEnderecosPorUsuarioId(usuarioId);
    }

    @Override
    public Boolean verificaEnderecoDoUsuario(Integer id, Integer usuarioId) throws LojaException {
        return enderecoData.verificaEnderecoDoUsuario(id, usuarioId);
    }
}
