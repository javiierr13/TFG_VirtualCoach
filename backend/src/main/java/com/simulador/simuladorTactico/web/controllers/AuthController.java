package com.simulador.simuladorTactico.web.controllers;

import com.simulador.simuladorTactico.services.AuthService;
import com.simulador.simuladorTactico.services.dto.LoginRequest;
import com.simulador.simuladorTactico.services.dto.LoginResponse;
import com.simulador.simuladorTactico.services.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.registrar(request);
        return ResponseEntity.ok("Entrenador registrado con éxito");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}