package br.uff.loja.core.interfaces.services;

import java.util.List;

import br.uff.loja.core.dtos.EnderecoDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface IEnderecoService {
    public EnderecoDTO encontraEnderecoPorId(Integer id) throws LojaException;
    public Integer excluiEnderecoPorId(Integer id) throws LojaException;
    public Integer atualizaEnderecoPorId(Integer id, EnderecoDTO endereco) throws LojaException;
    public Integer insereEndereco(EnderecoDTO endereco) throws LojaException;
    public List<EnderecoDTO> listaEnderecosPorUsuarioId(Integer usuarioId) throws LojaException;
}
