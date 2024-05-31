package com.rikkeiacademy.demo_jwt_security_api.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(nullable = false,unique = true,length = 100)
    private String username;
    @Column(nullable = false,length = 100)
    private String password;
    @Column(nullable = false,length = 70)
    private String fullName;
    @Column(nullable = false,length = 200)
    private String address;
    @Column(nullable = false,unique = true,length = 100)
    private String email;
    private Boolean status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "userId"),
    inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles;
}
