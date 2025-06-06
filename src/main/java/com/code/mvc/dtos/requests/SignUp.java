package com.code.mvc.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
//it is Lombok annotation that
// generates a constructor with required arguments
// (fields marked as final or annotated with @NonNull).
public class SignUp {
    @NotBlank(message = "Please write your full name here")
    @Size(min = 1, max = 100)
    private String name;

    @NotBlank(message = "Please enter your unique username")
    @Size(min = 1, max = 100)
    private String userName;

    @NotBlank
    @Size(max = 50)
    @Email(message = "Please enter a valid email id")
    private String email;

    @NotBlank
    @Pattern(regexp = "^(\\\\+91)?[6-9][0-9]{9}$", message = "Enter correct phone number ex:+919876543210")
    @Size(min = 10,max=15, message = "Phone number length is invalid")
    private String phoneNumber;

    @NotBlank(message = "Please provide a strong password")
    @Size(min=6, max = 100, message = "Password must be at least of 6 characters")
    private String password;

    private Set<String> roles;
}
