package com.code.mvc.dtos.responses;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenValidationMessage {
    private String message;

    public TokenValidationMessage(){}

    public TokenValidationMessage(String message){
        this.message = message;
    }
}
