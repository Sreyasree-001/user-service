package com.code.mvc.dtos.requests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@RequiredArgsConstructor cannot use here
public class TokenValidationRequest {
    private String accessToken;

    public TokenValidationRequest() {
    }

    public TokenValidationRequest(String accessToken) {
        this.accessToken = accessToken;
    }
}
