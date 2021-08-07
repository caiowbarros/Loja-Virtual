package br.uff.loja.infrastructure.shared;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.uff.loja.core.exceptions.LojaException;
import br.uff.loja.core.interfaces.shared.IHelper;

public class Helper implements IHelper {
    public Date convertStringToDate(String format, String date) throws LojaException { 
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (Exception e) {
            throw new LojaException(e.getMessage());
        }
    }
}
