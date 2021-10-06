package com.murilonerdx.epictask.services.security;

import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UsuarioRepository repository;


    public AuthorizationFilter(TokenService tokenService, UsuarioRepository repository){
        this.repository = repository;
        this.tokenService = tokenService;
    }
    @Override
    protected void doFilterInternal
            (
                    HttpServletRequest request,
                    HttpServletResponse response,
                    FilterChain filterChain
            ) throws ServletException, IOException {
        String token = extractToken(request);
        boolean valid = tokenService.isValid(token);
        if (valid) authorizeUser(token);

        filterChain.doFilter(request, response);
    }

    private void authorizeUser(String token) {
        Long id = Long.valueOf(tokenService.getById(token));

        Usuario user = repository.findById(id).get();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getEmail(), null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header == null || header.isEmpty() || !header.startsWith("Bearer "))
            return null;

        return header.substring(7, header.length());
    }
}
