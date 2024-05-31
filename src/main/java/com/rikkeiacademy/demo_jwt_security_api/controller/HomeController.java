package com.rikkeiacademy.demo_jwt_security_api.controller;

import com.rikkeiacademy.demo_jwt_security_api.model.dto.request.FormLogin;
import com.rikkeiacademy.demo_jwt_security_api.model.dto.request.FormRegsiter;
import com.rikkeiacademy.demo_jwt_security_api.model.dto.response.JWTResponse;
import com.rikkeiacademy.demo_jwt_security_api.model.dto.response.ResponseError;
import com.rikkeiacademy.demo_jwt_security_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HomeController {
    @Autowired
    private UserService userService;

    @PostMapping("/public/register")
    public ResponseEntity<?>register(@RequestBody FormRegsiter formRegsiter){
        boolean bl = userService.register(formRegsiter);
        if(bl){
            Map<String,String> map = new HashMap<>();
            map.put("success","Register successfully!");
            return new ResponseEntity<>(map, HttpStatus.CREATED);
        }else{
            ResponseError responseError = ResponseError.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Kết quả đăng ký tài khoản")
                    .detail("Đăng ký tài khoản có lỗi")
                    .build();
            return  new ResponseEntity<>(responseError,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/public/login")
    public ResponseEntity<JWTResponse> login(@RequestBody FormLogin formLogin){
        return new ResponseEntity<>(userService.login(formLogin),HttpStatus.OK);
    }

    @GetMapping("/user/list")
    public ResponseEntity<List<String>> list1(){
        return new ResponseEntity<>(Arrays.asList("Mot","Hai","Ba"),HttpStatus.OK);
    }

    @GetMapping("/admin/list")
    public ResponseEntity<List<String>> list2(){
        return new ResponseEntity<>(Arrays.asList("Nam","Sau","Bay"),HttpStatus.OK);
    }
}
