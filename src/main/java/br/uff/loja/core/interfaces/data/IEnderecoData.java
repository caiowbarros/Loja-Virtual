package br.uff.loja.core.interfaces.data;

import java.util.List;

import br.uff.loja.core.dtos.EnderecoDTO;

public interface IEnderecoData {
    public EnderecoDTO encontraEnderecoPorId(Integer id);
    public Integer excluiEnderecoPorId(Integer id);
    public Integer atualizaEnderecoPorId(Integer id, EnderecoDTO endereco);
    public Integer insereEndereco(EnderecoDTO endereco);
    public List<EnderecoDTO> listaEnderecosPorUsuarioId(Integer usuarioId);
}
