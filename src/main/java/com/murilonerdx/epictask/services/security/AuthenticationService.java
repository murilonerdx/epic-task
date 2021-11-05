package com.murilonerdx.epictask.services.security;

import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> user = repository.findByEmail(email);
        if (user.isEmpty()) throw new UsernameNotFoundException("e-mail not found");
        return user.get();
    }

}
