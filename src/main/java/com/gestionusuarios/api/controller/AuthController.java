package com.gestionusuarios.api.controller;

import com.gestionusuarios.api.dto.LoginRequestDTO;
import com.gestionusuarios.api.dto.LoginResponseDTO;
import com.gestionusuarios.api.dto.RegisterRequestDTO;
import com.gestionusuarios.api.dto.RegisterResponseDTO;
import com.gestionusuarios.api.service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        RegisterResponseDTO response = authService.registerUser(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = authService.loginUser(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}