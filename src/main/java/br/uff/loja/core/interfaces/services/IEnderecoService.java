package br.uff.loja.core.interfaces.services;

import java.util.List;

import br.uff.loja.core.dtos.EnderecoDTO;

public interface IEnderecoService {
    public EnderecoDTO encontraEnderecoPorId(Integer id) throws Exception;
    public Integer excluiEnderecoPorId(Integer id) throws Exception;
    public Integer atualizaEnderecoPorId(Integer id, EnderecoDTO endereco) throws Exception;
    public Integer insereEndereco(EnderecoDTO endereco) throws Exception;
    public List<EnderecoDTO> listaEnderecosPorUsuarioId(Integer usuarioId) throws Exception;
}
