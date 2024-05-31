package com.rikkeiacademy.demo_jwt_security_api.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FormRegsiter {
    private String username;
    private String password;
    private String fullName;
    private String address;
    private String email;
    private Set<String> roles;
}
