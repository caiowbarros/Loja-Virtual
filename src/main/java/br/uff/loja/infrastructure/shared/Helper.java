package br.uff.loja.infrastructure.shared;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;

import br.uff.loja.core.dtos.UsuarioDTO;
import br.uff.loja.core.interfaces.shared.IHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

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

    public String tryParseMoneyFormat(Object preco) {
        return "R$" + String.format("%.2f", preco);
    }

    public Double tryParseDouble(String intStr) {
        try {
            return Double.valueOf(intStr);
        } catch (Exception ec) {
            return null;
        }
    }

    public String montaJwt(UsuarioDTO usuario) {
        return Jwts.builder().setSubject(usuario.getId().toString()).claim("usuario", usuario.toJson())
                .signWith(Keys.hmacShaKeyFor("7f-j&amp;CKk=coNzZc0y7_4obMP?#TfcYq%fcD0mDpenW2nc!lfGoZ|d?f&amp;RNbDHUX6"
                        .getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                .compact();
    }

    public UsuarioDTO desmontaJwt(String token) {
        token = token.substring("Bearer".length()).trim();

        Jws<Claims> ret = Jwts.parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor("7f-j&amp;CKk=coNzZc0y7_4obMP?#TfcYq%fcD0mDpenW2nc!lfGoZ|d?f&amp;RNbDHUX6"
                                .getBytes(StandardCharsets.UTF_8)))
                .build().parseClaimsJws(token);
        String usuarioString = ret.getBody().get("usuario").toString();
        return new Gson().fromJson(usuarioString, UsuarioDTO.class);
    }
}
