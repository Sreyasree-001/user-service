package com.code.mvc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId",unique = true,nullable = false)
    private Long id;

    @NotBlank(message = "Please write your full name here")
    @Column(name = "name",nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Please enter your unique username")
    @Column(name = "userName",unique = true,nullable = false, length = 100)
    private String userName;

    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email(message = "Please enter a valid email id")
    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Pattern(regexp = "^(\\\\+91)?[6-9][0-9]{9}$", message = "Enter correct phone number ex:+919876543210")
    @Size(min = 10,max=15, message = "Phone number length is invalid")
    @Column(name = "phoneNumber",nullable = false,unique = true)
    private String phoneNumber;

    @JsonIgnore
    @NotBlank(message = "Please provide a strong password")
    @Size(min=6, max = 100, message = "Password must be at least of 6 characters")
    @Column(name = "password",nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

}
