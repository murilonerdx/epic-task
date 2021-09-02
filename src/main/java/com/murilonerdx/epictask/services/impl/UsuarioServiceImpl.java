package com.murilonerdx.epictask.services.impl;

import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.entities.enums.Role;
import com.murilonerdx.epictask.repository.UsuarioRepository;
import com.murilonerdx.epictask.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    final
    UsuarioRepository repository;

    @Override
    public Usuario create(Usuario usuario) {
        return repository.save(usuario);
    }

    public List<Usuario> findByRole(){
        return repository.findByRole();
    }

    public Usuario findByEmail(String email){
        return repository.findByEmail(email);
    }
}
