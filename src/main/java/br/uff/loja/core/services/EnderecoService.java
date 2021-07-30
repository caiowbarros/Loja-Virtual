package br.uff.loja.core.services;

import java.util.List;

import br.uff.loja.core.dtos.EnderecoDTO;
import br.uff.loja.core.interfaces.data.IEnderecoData;
import br.uff.loja.core.interfaces.services.IEnderecoService;
import br.uff.loja.infrastructure.data.EnderecoData;

public class EnderecoService implements IEnderecoService {
    private IEnderecoData enderecoData;

    public EnderecoService() {
        enderecoData = new EnderecoData();
    }

    @Override
    public EnderecoDTO encontraEnderecoPorId(Integer id) throws Exception {
        return enderecoData.encontraEnderecoPorId(id);
    }

    @Override
    public Integer excluiEnderecoPorId(Integer id) throws Exception {
        return enderecoData.excluiEnderecoPorId(id);
    }

    @Override
    public Integer atualizaEnderecoPorId(Integer id, EnderecoDTO endereco) throws Exception {
        return enderecoData.atualizaEnderecoPorId(id, endereco);
    }

    @Override
    public Integer insereEndereco(EnderecoDTO endereco) throws Exception {
        return enderecoData.insereEndereco(endereco);
    }

    @Override
    public List<EnderecoDTO> listaEnderecosPorUsuarioId(Integer usuarioId) throws Exception {
        return enderecoData.listaEnderecosPorUsuarioId(usuarioId);
    }
    
}