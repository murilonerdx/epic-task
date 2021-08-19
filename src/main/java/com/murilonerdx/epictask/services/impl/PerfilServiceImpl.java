package com.murilonerdx.epictask.services.impl;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.repository.PerfilRepository;
import com.murilonerdx.epictask.repository.TarefaRepository;
import com.murilonerdx.epictask.services.PerfilService;
import com.murilonerdx.epictask.services.TarefaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PerfilServiceImpl implements PerfilService {

    private final PerfilRepository repository;

    @Override
    public List<Perfil> getAll() {
        return repository.findAll();
    }

    @Override
    public Perfil getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public Perfil update(Perfil perfil, Long id) {
        return repository.save(perfil);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Perfil create(Perfil perfil) {
        return repository.save(perfil);
    }
}
