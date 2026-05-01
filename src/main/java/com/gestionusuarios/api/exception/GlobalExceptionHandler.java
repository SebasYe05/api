package com.gestionusuarios.api.exception;

import com.gestionusuarios.api.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Manejo de excepción personalizada
    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<ErrorResponseDTO> handleUsuarioException(UsuarioException ex) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .mensaje(ex.getMessage())
                .fechaError(LocalDateTime.now())
                .codigoStatus(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 2. Manejo de errores de validación en DTOs (ej. @NotBlank, @Email)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .mensaje("Error de validación: " + errores.toString())
                .fechaError(LocalDateTime.now())
                .codigoStatus(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 3. Manejo de errores de autenticación de Spring Security
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .mensaje("Credenciales inválidas, por favor verifica tus datos")
                .fechaError(LocalDateTime.now())
                .codigoStatus(HttpStatus.UNAUTHORIZED.value())
                .build();

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    // 4. Manejador genérico para errores inesperados del servidor
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .mensaje("Error interno del servidor: " + ex.getMessage())
                .fechaError(LocalDateTime.now())
                .codigoStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.FORBIDDEN.value());
        body.put("error", "Access Denied");
        String message = ex.getMessage();
        if (message == null || message.isEmpty()) {
            message = "Access is denied";
        }
        body.put("message", message);
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }
}