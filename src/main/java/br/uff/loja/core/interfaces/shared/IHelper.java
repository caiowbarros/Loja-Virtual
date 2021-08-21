package br.uff.loja.core.interfaces.shared;

import java.util.Date;

import br.uff.loja.core.exceptions.LojaException;

public interface IHelper {

    public Date convertStringToDate(String format, String date) throws LojaException;
}
