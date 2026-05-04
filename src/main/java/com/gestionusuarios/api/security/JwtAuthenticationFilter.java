package com.gestionusuarios.api.security;

import com.gestionusuarios.api.model.Cuenta;
import com.gestionusuarios.api.model.Rol;
import com.gestionusuarios.api.repository.CuentaRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CuentaRepository cuentaRepository;

    public JwtAuthenticationFilter(JwtService jwtService, CuentaRepository cuentaRepository) {
        this.jwtService = jwtService;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        try {
            final String username = jwtService.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Optional<Cuenta> cuentaOpt = cuentaRepository.findByNombreUsuario(username);

                if (cuentaOpt.isPresent()) {
                    Cuenta cuenta = cuentaOpt.get();
                    Rol rol = cuenta.getRol();

                    List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(rol.name()));

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
                            null, authorities);

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Token inválido — Spring Security rechazará la request
        }

        filterChain.doFilter(request, response);
    }
}