package br.uff.loja.core.dtos;

import com.google.gson.Gson;

import br.uff.loja.core.interfaces.dtos.IBaseDTO;

public class BaseDTO implements IBaseDTO {

    @Override
    public String toJson() {
        return new Gson().toJson(this);
    }
    
}
