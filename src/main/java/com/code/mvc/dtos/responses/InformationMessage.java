package com.code.mvc.dtos.responses;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;


import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformationMessage {
    private Long id;
    private String name;
    private String userName;
    private String email;
    private String phoneNumber;
    private Collection<? extends GrantedAuthority> roles;
}
