package com.murilonerdx.epictask.services.impl;

import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.repository.UsuarioRepository;
import com.murilonerdx.epictask.services.UsuarioService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    final
    UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario create(Usuario usuario) {
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        return repository.save(usuario);
    }

    public List<Usuario> findByRole(){
        return repository.findByRole();
    }

    public Usuario findByEmail(String email){
        return repository.findByEmail(email).orElse(null);
    }
}
