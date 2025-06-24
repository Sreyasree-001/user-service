package com.code.mvc.services.implementations;

import com.code.mvc.dtos.requests.Login;
import com.code.mvc.dtos.requests.SignUp;
import com.code.mvc.dtos.responses.InformationMessage;
import com.code.mvc.dtos.responses.JwtResponseMessage;
import com.code.mvc.entities.RoleName;
import com.code.mvc.entities.User;
import com.code.mvc.repositories.UserRepository;
import com.code.mvc.security.jwt.TokenProvider;
import com.code.mvc.security.usersecurity.UserDetail;
import com.code.mvc.security.usersecurity.UserDetailService;
import com.code.mvc.services.RoleServiceInterface;
import com.code.mvc.services.UserServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final UserDetailService userDetailService;
    private final ModelMapper modelMapper;
    private final RoleServiceInterface roleServiceInterface;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       TokenProvider tokenProvider,
                       UserDetailService userDetailsService,
                       ModelMapper modelMapper,
                       RoleServiceInterface roleServiceInterface) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.userDetailService = userDetailsService;
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

                Set<String> incomingRoles = signUp.getRoles() == null ? Set.of("USER") : signUp.getRoles();
                user.setRoles(incomingRoles.stream()
                        .map(role -> roleServiceInterface.findByRoleName(mapToRoleName(role))
                                .orElseThrow(() -> new RuntimeException("Role " + role + " does not exist.")))
                        .collect(Collectors.toSet()));
//                user.setRoles(signUp.getRoles()
//                        .stream()
//                        .map(role -> roleServiceInterface.findByRoleName(mapToRoleName(role))
//                                .orElseThrow(() -> new RuntimeException("Role does not exist.")))
//                        .collect(Collectors.toSet()));

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
                //needed user security details
                UserDetails userDetails = userDetailService.loadUserByUsername(userName);
                // check username
                if (userDetails == null) {
                    throw new RuntimeException("Username not found");
                }
                // Check password
                if (!passwordEncoder.matches(login.getPassword(), userDetails.getPassword())) {
                    throw new RuntimeException("Incorrect password");
                }

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, login.getPassword(), userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                String accessToken = tokenProvider.generateToken(authentication);
                String refreshToken = tokenProvider.generateRefreshToken(authentication);

                UserDetail userDetail = (UserDetail) userDetails;

                return JwtResponseMessage.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .info("Login Successful!")
                        .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
