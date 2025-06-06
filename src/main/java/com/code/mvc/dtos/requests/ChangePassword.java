package com.code.mvc.dtos.requests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ChangePassword {
    String oldPassword;
    String newPassword;
    String confirmPassword;
}
