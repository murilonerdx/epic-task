package com.murilonerdx.epictask.services.security;

import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public Usuario getSessionUser(Model mv) {
        Usuario obj = (Usuario) getAuthentication().getPrincipal();
        return usuarioRepository.findByEmail(obj.getEmail()).get();
    }

}
