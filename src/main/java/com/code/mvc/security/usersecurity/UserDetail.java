package com.code.mvc.security.usersecurity;

import com.code.mvc.entities.User;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@With
@Builder
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail implements UserDetails {
    private long id;
    private String userName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;

    private Collection<? extends GrantedAuthority> roles;

    public static UserDetail build(User user) {
        // list of roles (as GrantedAuthority) the user has,
        // which is used by Spring Security to grant or deny access.
        List<GrantedAuthority> authorityList = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.roleName().name()))
                .collect(Collectors.toList());

        return UserDetail.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .fullName(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }
}
