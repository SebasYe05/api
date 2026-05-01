package com.gestionusuarios.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestionusuarios.api.dto.RegisterRequestDTO;
import com.gestionusuarios.api.dto.RegisterResponseDTO;
import com.gestionusuarios.api.service.IAuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<RegisterResponseDTO> registerAdmin(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        RegisterResponseDTO response = authService.registerAdmin(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
