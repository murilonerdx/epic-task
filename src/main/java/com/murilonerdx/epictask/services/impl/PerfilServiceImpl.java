package com.murilonerdx.epictask.services.impl;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.repository.PerfilRepository;
import com.murilonerdx.epictask.services.PerfilService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PerfilServiceImpl implements PerfilService {

    private final PerfilRepository repository;

    @Override
    public List<Perfil> getAll() {
        return repository.findAll();
    }

    @Override
    public Perfil create(Perfil perfil) {
        return repository.save(perfil);
    }


}
