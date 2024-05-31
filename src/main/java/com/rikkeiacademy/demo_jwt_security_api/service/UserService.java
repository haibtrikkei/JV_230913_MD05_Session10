package com.rikkeiacademy.demo_jwt_security_api.service;

import com.rikkeiacademy.demo_jwt_security_api.model.dto.request.FormLogin;
import com.rikkeiacademy.demo_jwt_security_api.model.dto.request.FormRegsiter;
import com.rikkeiacademy.demo_jwt_security_api.model.dto.response.JWTResponse;

public interface UserService {
    public boolean register(FormRegsiter formRegsiter);
    public JWTResponse login(FormLogin formLogin);
}
