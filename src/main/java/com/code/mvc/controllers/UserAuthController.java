package com.code.mvc.controllers;

import com.code.mvc.dtos.requests.Login;
import com.code.mvc.dtos.requests.SignUp;
import com.code.mvc.dtos.responses.JwtResponseMessage;
import com.code.mvc.dtos.responses.ResponseMessage;
import com.code.mvc.services.implementations.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {
    private UserService userService;

    public UserAuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public Mono<ResponseMessage> register(@RequestBody SignUp signUp) {
        return userService.register(signUp)
                .map(user -> new ResponseMessage("Created user with username '" + signUp.getUserName() + "' successfully."))
                .onErrorResume(error -> {
                    // Log the full error (optional)
                    error.printStackTrace(); // Or use a logger
                    return Mono.just(new ResponseMessage("Error: " + error.getMessage()));
                });
    }
//    public Mono<ResponseMessage> register(@RequestBody SignUp signUp) {
//        return userService.register(signUp)
//                .map(user -> new ResponseMessage("Created user: " + signUp.getUserName() + " successfully."))
//                .onErrorResume(error ->
//                        Mono.just(new ResponseMessage("Error occurred while creating the account."))
//                );
//    }

    @PostMapping("/login")
    public Mono<ResponseEntity<JwtResponseMessage>> login (@RequestBody Login login){
        return userService.login(login)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    JwtResponseMessage errorjwtResponseMessage = new JwtResponseMessage(
                            null,
                            null,
                            "Error: Retry Again"
                    );
                    return Mono.just(new ResponseEntity<>(errorjwtResponseMessage, HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }

    @GetMapping("/hello")
    @Operation(summary = "Get a hello message",
            description = "Returns a hello message for authenticated users")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello, authenticated user!");
    }
}
