package com.sedin.dto;

import com.sedin.model.MUser;

import java.util.HashMap;
import java.util.Map;

public class UserIdentity extends MUser {
     
    private String token;//JWT生成的令牌

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    private String verifCode;//校验码


    public String getVerifCode() {
        return verifCode;
    }

    public void setVerifCode(String verifCode) {
        this.verifCode = verifCode;
    }
    
}
