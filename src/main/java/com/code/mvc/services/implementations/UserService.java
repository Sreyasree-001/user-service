package com.code.mvc.services.implementations;

import com.code.mvc.dtos.requests.Login;
import com.code.mvc.dtos.requests.SignUp;
import com.code.mvc.dtos.responses.JwtResponseMessage;
import com.code.mvc.entities.RoleName;
import com.code.mvc.entities.User;
import com.code.mvc.repositories.UserRepository;
import com.code.mvc.security.jwt.TokenProvider;
import com.code.mvc.services.RoleServiceInterface;
import com.code.mvc.services.UserServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    //private final UserDetailService userDetailService;
    private final ModelMapper modelMapper;
    private final RoleServiceInterface roleServiceInterface;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       TokenProvider tokenProvider,
                       ModelMapper modelMapper,
                       RoleServiceInterface roleServiceInterface) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.modelMapper = modelMapper;
        this.roleServiceInterface = roleServiceInterface;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUserName(username);
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    private RoleName mapToRoleName(String roleName) {
        return switch (roleName) {
            case "ADMIN", "admin", "Admin" -> RoleName.ADMIN;
            case "USER", "user", "User" -> RoleName.USER;
            default -> null;
        };
    }

    @Override
    public Mono<User> register(SignUp signUp){
        return Mono.defer(() -> {
            try {
                if (existsByUsername(signUp.getUserName())) {
                    return Mono.error(new RuntimeException("Username " + signUp.getUserName() + " already exists!"));
                }
                if (existsByEmail(signUp.getEmail())) {
                    return Mono.error(new RuntimeException("Email id " + signUp.getEmail() + " already exists!"));
                }
                User user = modelMapper.map(signUp, User.class);
                user.setPassword(passwordEncoder.encode(signUp.getPassword()));
                user.setRoles(signUp.getRoles()
                        .stream()
                        .map(role -> roleServiceInterface.findByRoleName(mapToRoleName(role))
                                .orElseThrow(() -> new RuntimeException("Role does not exist.")))
                        .collect(Collectors.toSet()));

                userRepository.save(user);
                return Mono.just(user);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Mono<JwtResponseMessage> login(Login login){
        return Mono.fromCallable(() -> {
            try {
                String userName = login.getUserName();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
