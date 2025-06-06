package com.code.mvc.dtos.requests;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String phone;
    //The UserDto class intentionally excludes the password field for security reasons. This is a common security practice
    // where sensitive data like passwords are never exposed in API responses or transferred between services.
}
