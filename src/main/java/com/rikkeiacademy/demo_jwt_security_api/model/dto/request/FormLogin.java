package com.rikkeiacademy.demo_jwt_security_api.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FormLogin {
    private String username;
    private String password;
}
