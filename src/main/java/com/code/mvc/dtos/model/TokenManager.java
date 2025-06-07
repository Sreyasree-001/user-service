package com.code.mvc.dtos.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
public class TokenManager {
    public String token;
    public String refreshToken;
    private Map<String, String> tokenStore = new HashMap<>();
    private Map<String, String> refreshTokenStore = new HashMap<>();

    public void storeToken(String username,String token){
        this.token = token;
        tokenStore.put(username, token);
    }

    public void storeRefreshToken(String username,String refreshToken){
        this.refreshToken = refreshToken;
        refreshTokenStore.put(username, refreshToken);
    }

    public String getToken(String username){
        return tokenStore.get(username);
    }

    public void removeToken(String username){
        tokenStore.remove(username);
    }
}
