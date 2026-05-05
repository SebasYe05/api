package com.gestionusuarios.api.service;

import com.gestionusuarios.api.dto.RegisterRequestDTO;
import com.gestionusuarios.api.dto.RegisterResponseDTO;
import com.gestionusuarios.api.dto.UpdatePerfilRequest;
import com.gestionusuarios.api.dto.UserResponseDTO;
import com.gestionusuarios.api.exception.UsuarioException;
import com.gestionusuarios.api.mapper.UsuarioMapper;
import com.gestionusuarios.api.model.Cuenta;
import com.gestionusuarios.api.model.Perfil;
import com.gestionusuarios.api.repository.CuentaRepository;
import com.gestionusuarios.api.repository.PerfilRepository;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final CuentaRepository cuentaRepository;

    private final PerfilRepository perfilRepository;

    private final UsuarioMapper usuarioMapper;

    private final PasswordEncoder passwordEncoder;
    
    public UsuarioServiceImpl(CuentaRepository cuentaRepository, PerfilRepository perfilRepository, UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder) {
        this.cuentaRepository = cuentaRepository;
        this.perfilRepository = perfilRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<Perfil> perfiles = perfilRepository.findAll();

        return perfiles.stream().map(perfil -> {
            Cuenta cuenta = cuentaRepository.findById(perfil.getId())
                    .orElse(Cuenta.builder().build());

            return usuarioMapper.toUserResponseDTO(perfil, cuenta);
        }).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(ObjectId id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuario no encontrado en Perfil"));
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuario no encontrado en Cuenta"));

        return UserResponseDTO.builder()
                .id(perfil.getId().toString())
                .nameUser(cuenta.getNombreUsuario())
                .fullName(perfil.getNombresCompletos())
                .bio(perfil.getBiografia())
                .rol(cuenta.getRol().name())
                .build();
    }

    @Transactional
    @Override
    public RegisterResponseDTO createUser(RegisterRequestDTO request) {
        if (cuentaRepository.existsByNombreUsuario(request.getNameUser())) {
            throw new UsuarioException("El nombre de usuario ya está registrado");
        }

        ObjectId id = new ObjectId();
        Cuenta cuenta = Cuenta.builder()
                .id(id)
                .nombreUsuario(request.getNameUser())
                .contrasena(passwordEncoder.encode(request.getPass()))
                .rol(com.gestionusuarios.api.model.Rol.ROLE_USER)
                .build();

        Perfil perfil = Perfil.builder()
                .id(id)
                .nombresCompletos(request.getFullName())
                .build();

        perfilRepository.save(perfil);
        cuentaRepository.save(cuenta);

        return RegisterResponseDTO.builder()
                .mensaje("Usuario creado exitosamente por el Administrador")
                .fechaRegistro(LocalDateTime.now())
                .build();
    }

    @Transactional
    @Override
    public UserResponseDTO updateUser(ObjectId id, UpdatePerfilRequest request) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Cuenta no encontrada"));
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Perfil no encontrado"));

        cuenta.setNombreUsuario(request.getNameUser());
        if (request.getPass() != null && !request.getPass().isEmpty()) {
            cuenta.setContrasena(passwordEncoder.encode(request.getPass()));
        }

        perfil.setNombresCompletos(request.getFullName());
        perfil.setBiografia(request.getBio());

        cuentaRepository.save(cuenta);
        perfilRepository.save(perfil);

        return UserResponseDTO.builder()
                .id(id.toString())
                .nameUser(cuenta.getNombreUsuario())
                .fullName(perfil.getNombresCompletos())
                .bio(perfil.getBiografia())
                .rol(cuenta.getRol().name())
                .build();
    }

    @Transactional
    @Override
    public void deleteUser(ObjectId id) {
        if (!cuentaRepository.existsById(id)) {
            throw new UsuarioException("Usuario no encontrado");
        }

        cuentaRepository.deleteById(id);
        perfilRepository.deleteById(id);
    }
}