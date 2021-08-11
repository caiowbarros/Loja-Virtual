package br.uff.loja.core.interfaces.data;

import java.util.List;

import br.uff.loja.core.dtos.EnderecoDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface IEnderecoData {
    public EnderecoDTO encontraEnderecoPorId(Integer id) throws LojaException;
    public void excluiEnderecoPorId(Integer id) throws LojaException;
    public void atualizaEnderecoPorId(Integer id, EnderecoDTO endereco) throws LojaException;
    public Boolean verificaEnderecoDoUsuario(Integer id, Integer usuarioId) throws LojaException;
    public void insereEndereco(EnderecoDTO endereco) throws LojaException;
    public List<EnderecoDTO> listaEnderecosPorUsuarioId(Integer usuarioId) throws LojaException;
}
