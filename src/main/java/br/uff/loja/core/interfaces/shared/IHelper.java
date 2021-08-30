package br.uff.loja.core.interfaces.shared;

import java.util.Date;

import br.uff.loja.core.dtos.UsuarioDTO;
import br.uff.loja.core.exceptions.LojaException;

public interface IHelper {

    public Date convertStringToDate(String format, String date) throws LojaException;

    public String montaJwt(UsuarioDTO usuario);

    public UsuarioDTO desmontaJwt(String token);

    public Double tryParseDouble(String intStr);

    public String tryParseMoneyFormat(Object preco);

    public Integer tryParseInteger(String intStr);

    public String convertDateToString(String format, Date date);
}
