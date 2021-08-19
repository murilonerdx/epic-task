package com.murilonerdx.epictask.services;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.validations.TarefaValidation;

import java.util.List;

public interface PerfilService {
    List<Perfil> getAll();
    Perfil getById(Long id);
    Perfil update(Perfil perfil, Long id);
    void deleteById(Long id);
    Perfil create(Perfil perfil);
}
