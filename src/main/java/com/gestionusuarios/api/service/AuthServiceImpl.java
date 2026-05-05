package com.gestionusuarios.api.service;

import com.gestionusuarios.api.dto.LoginRequestDTO;
import com.gestionusuarios.api.dto.LoginResponseDTO;
import com.gestionusuarios.api.dto.RegisterRequestDTO;
import com.gestionusuarios.api.dto.RegisterResponseDTO;
import com.gestionusuarios.api.exception.UsuarioException;
import com.gestionusuarios.api.model.Cuenta;
import com.gestionusuarios.api.model.Perfil;
import com.gestionusuarios.api.model.Rol;
import com.gestionusuarios.api.repository.CuentaRepository;
import com.gestionusuarios.api.repository.PerfilRepository;
import com.gestionusuarios.api.security.JwtService;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements IAuthService {

        @Autowired
        private CuentaRepository cuentaRepository;

        @Autowired
        private PerfilRepository perfilRepository;

        @Autowired
        private JwtService jwtService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Transactional
        @Override
        public RegisterResponseDTO registerUser(RegisterRequestDTO registerRequest) {

                if (!registerRequest.getPass().equals(registerRequest.getConfirmPassword())) {
                        throw new UsuarioException("Las contraseñas no coinciden");
                }

                if (cuentaRepository.existsByNombreUsuario(registerRequest.getNameUser())) {
                        throw new UsuarioException("El nombre de usuario ya está registrado");
                }

                ObjectId id = new ObjectId();

                // Construimos la entidad Cuenta con el Builder
                Cuenta cuenta = Cuenta.builder()
                                .id(id)
                                .nombreUsuario(registerRequest.getNameUser())
                                .contrasena(passwordEncoder.encode(registerRequest.getPass()))
                                .rol(Rol.ROLE_USER)
                                .build();

                // Construimos la entidad Perfil con el Builder
                Perfil perfil = Perfil.builder()
                                .id(id)
                                .nombresCompletos(registerRequest.getFullName())
                                .biografia("")
                                .build();

                perfilRepository.save(perfil);
                cuentaRepository.save(cuenta);

                // Construimos la respuesta con el Builder, incluyendo la fecha actual
                return RegisterResponseDTO.builder()
                                .mensaje("Usuario registrado exitosamente")
                                .fechaRegistro(java.time.LocalDateTime.now())
                                .build();
        }

        @Override
        public RegisterResponseDTO registerAdmin(RegisterRequestDTO registerRequest) {

                if (!registerRequest.getPass().equals(registerRequest.getConfirmPassword())) {
                        throw new UsuarioException("Las contraseñas no coinciden");
                }

                if (cuentaRepository.existsByNombreUsuario(registerRequest.getNameUser())) {
                        throw new UsuarioException("El nombre de usuario ya está registrado");
                }

                ObjectId id = new ObjectId();

                // Construimos la entidad Cuenta con el Builder
                Cuenta cuenta = Cuenta.builder()
                                .id(id)
                                .nombreUsuario(registerRequest.getNameUser())
                                .contrasena(passwordEncoder.encode(registerRequest.getPass()))
                                .rol(Rol.ROLE_ADMIN)
                                .build();

                // Construimos la entidad Perfil con el Builder
                Perfil perfil = Perfil.builder()
                                .id(id)
                                .nombresCompletos(registerRequest.getFullName())
                                .biografia("")
                                .build();

                perfilRepository.save(perfil);
                cuentaRepository.save(cuenta);

                // Construimos la respuesta con el Builder, incluyendo la fecha actual
                return RegisterResponseDTO.builder()
                                .mensaje("Administrador registrado exitosamente")
                                .fechaRegistro(java.time.LocalDateTime.now())
                                .build();
        }

        @Override
        public LoginResponseDTO loginUser(LoginRequestDTO loginRequest) {

                Cuenta cuenta = cuentaRepository.findByNombreUsuario(loginRequest.getNameUser())
                                .orElseThrow(() -> new UsuarioException("Usuario no encontrado"));

                if (!passwordEncoder.matches(loginRequest.getPass(), cuenta.getContrasena())) {
                        throw new UsuarioException("Contraseña incorrecta");
                }

                Perfil perfil = perfilRepository.findById(cuenta.getId())
                                .orElseThrow(() -> new UsuarioException("Perfil no encontrado"));

                Map<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("rol", cuenta.getRol());

                String token = jwtService.generateToken(extraClaims, cuenta.getNombreUsuario());

                return LoginResponseDTO.builder()
                                .token(token)
                                .userName(cuenta.getNombreUsuario())
                                .nombreCompleto(perfil.getNombresCompletos())
                                .bio(perfil.getBiografia())
                                .role(cuenta.getRol().name())
                                .mensaje("Login exitoso")
                                .build();
        }
}
