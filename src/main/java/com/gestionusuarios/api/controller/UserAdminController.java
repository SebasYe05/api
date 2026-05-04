package com.gestionusuarios.api.controller;

import com.gestionusuarios.api.dto.RegisterRequestDTO;
import com.gestionusuarios.api.dto.RegisterResponseDTO;
import com.gestionusuarios.api.dto.UserResponseDTO; 
import com.gestionusuarios.api.service.IUsuarioService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')") 
public class UserAdminController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = usuarioService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id) {
        UserResponseDTO user = usuarioService.getUserById(new ObjectId(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RegisterResponseDTO> createUser(@RequestBody RegisterRequestDTO request) {
        RegisterResponseDTO response = usuarioService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String id, @RequestBody RegisterRequestDTO request) {
        UserResponseDTO response = usuarioService.updateUser(new ObjectId(id), request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        usuarioService.deleteUser(new ObjectId(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}