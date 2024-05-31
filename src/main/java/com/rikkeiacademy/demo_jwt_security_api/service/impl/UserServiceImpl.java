package com.rikkeiacademy.demo_jwt_security_api.service.impl;

import com.rikkeiacademy.demo_jwt_security_api.model.dto.request.FormLogin;
import com.rikkeiacademy.demo_jwt_security_api.model.dto.request.FormRegsiter;
import com.rikkeiacademy.demo_jwt_security_api.model.dto.response.JWTResponse;
import com.rikkeiacademy.demo_jwt_security_api.model.entity.Role;
import com.rikkeiacademy.demo_jwt_security_api.model.entity.User;
import com.rikkeiacademy.demo_jwt_security_api.repository.RoleRepository;
import com.rikkeiacademy.demo_jwt_security_api.repository.UserRepository;
import com.rikkeiacademy.demo_jwt_security_api.security.jwt.JWTProvider;
import com.rikkeiacademy.demo_jwt_security_api.security.principals.CustomUserDetails;
import com.rikkeiacademy.demo_jwt_security_api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTProvider jwtProvider;

    @Override
    public boolean register(FormRegsiter formRegsiter) {
        User user = User.builder()
                .address(formRegsiter.getAddress())
                .username(formRegsiter.getUsername())
                .email(formRegsiter.getEmail())
                .fullName(formRegsiter.getFullName())
                .status(true)
                .password(passwordEncoder.encode(formRegsiter.getPassword()))
                .build();
        Set<Role> roles = new HashSet<>();
        if(formRegsiter.getRoles()!=null && !formRegsiter.getRoles().isEmpty()){
            formRegsiter.getRoles().forEach(role -> {
                switch (role){
                    case "ROLE_ADMIN":
                        roles.add(roleRepository.findRoleByRoleName(role).orElseThrow(()->new NoSuchElementException("Khong ton tai role admin")));
                        break;
                    case "ROLE_USER":
                        roles.add(roleRepository.findRoleByRoleName(role).orElseThrow(()->new NoSuchElementException("Khong ton tai role user")));
                        break;
                    case "ROLE_MODERATOR":
                        roles.add(roleRepository.findRoleByRoleName(role).orElseThrow(()->new NoSuchElementException("Khong ton tai role moderator")));
                        break;
                }
            });
        }else{
            roles.add(roleRepository.findRoleByRoleName("ROLE_USER").orElseThrow(()->new NoSuchElementException("Khong ton tai role user")));
        }
        user.setRoles(roles);
        userRepository.save(user);
        return true;
    }

    @Override
    public JWTResponse login(FormLogin formLogin) {
        Authentication authentication = null;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.getUsername(),formLogin.getPassword()));
        }catch (AuthenticationException ex){
            log.error("Sai username hoac password");
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String accessToken = jwtProvider.generateAccessToken(userDetails);
        return JWTResponse.builder()
                .address(userDetails.getAddress())
                .fullName(userDetails.getFullName())
                .email(userDetails.getEmail())
                .status(userDetails.getStatus())
                .authorities(userDetails.getAuthorities())
                .accessToken(accessToken)
                .build();
    }
}
