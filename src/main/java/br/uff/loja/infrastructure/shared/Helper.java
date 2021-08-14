package br.uff.loja.infrastructure.shared;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.uff.loja.core.interfaces.shared.IHelper;

public class Helper implements IHelper {
    public Date convertStringToDate(String format, String date) { 
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public String convertDateToString(String format, Date date) { 
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public Integer tryParseInteger(String intStr) {
        try {
            return Integer.valueOf(intStr);
        } catch (Exception ec) {
            return null;
        }
    }
}
