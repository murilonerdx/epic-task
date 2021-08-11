package com.murilonerdx.epictask.services.impl;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.entities.enums.StatusTarefa;
import com.murilonerdx.epictask.repository.TarefaRepository;
import com.murilonerdx.epictask.services.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaServiceImpl implements TarefaService {

    @Autowired
    TarefaRepository repository;

    @Override
    public List<Tarefa> getAll() {
        return repository.findAll();
    }

    @Override
    public Tarefa getById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Tarefa update(Tarefa tarefa, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Tarefa create(Tarefa tarefa) {
        Usuario user = new Usuario(1L, "mu-silva@outlook.com","123");
        Perfil perfil = new Perfil(1L, "Murilo", user,"a", 200.00);

        tarefa.setPerfil(perfil);
        tarefa.setObtain(true);
        tarefa.setProgress(0);
        tarefa.setStatusTask(StatusTarefa.ANALISE);
        return repository.save(tarefa);
    }
}
