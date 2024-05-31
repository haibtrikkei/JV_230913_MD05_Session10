package com.rikkeiacademy.demo_jwt_security_api.model.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponseError {
    private HttpStatus status;
    private String message;
    private Object detail;
}
