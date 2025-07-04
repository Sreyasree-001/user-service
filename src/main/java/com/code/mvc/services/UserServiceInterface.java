package com.code.mvc.services;

import com.code.mvc.dtos.requests.*;
import com.code.mvc.dtos.responses.JwtResponseMessage;
import com.code.mvc.entities.User;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface UserServiceInterface {
    Mono<User> register(SignUp signUp);
    Mono<JwtResponseMessage> login(Login login);

//    Mono<Void> logout();

//    Mono<User> update(Long id, SignUp update);
//    Mono<String> changePassword(ChangePassword request);
//    Mono<String> resetPassword(ResetPasswordRequest resetPasswordRequest);
//    String deleteUser(Long id);

 //   Optional<User> findById(Long id);
 //   Optional<User> findByUserName(String userName);
//    Page<UserDto> findAllUsers(int page, int size, String sortBy, String sortOrder);
}
