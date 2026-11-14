package com.examen.apilogin.controller;

import com.examen.apilogin.model.LoginRequest;
import com.examen.apilogin.model.LoginResponse;
import com.examen.apilogin.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.autenticar(request);
    }
}

