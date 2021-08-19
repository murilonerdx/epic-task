package com.murilonerdx.epictask.services.impl;

import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.repository.UsuarioRepository;
import com.murilonerdx.epictask.services.UsuarioService;
import com.murilonerdx.epictask.validations.TarefaValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    final
    UsuarioRepository repository;

    @Override
    public Usuario create(Usuario usuario) {
        return repository.save(usuario);
    }
}
