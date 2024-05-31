package com.rikkeiacademy.demo_jwt_security_api.security.principals;

import com.rikkeiacademy.demo_jwt_security_api.model.entity.Role;
import com.rikkeiacademy.demo_jwt_security_api.model.entity.User;
import com.rikkeiacademy.demo_jwt_security_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new NoSuchElementException("Khong ton tai username"));
        return CustomUserDetails.builder()
                .username(user.getUsername())
                .status(user.getStatus())
                .email(user.getEmail())
                .address(user.getAddress())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .authorities(mapRoleToAuthorties(user.getRoles()))
                .build();
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthorties(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).toList();
    }
}
