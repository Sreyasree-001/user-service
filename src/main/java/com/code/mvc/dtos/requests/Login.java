package com.code.mvc.dtos.requests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Login {
    private String userName;
    private String password;
}
