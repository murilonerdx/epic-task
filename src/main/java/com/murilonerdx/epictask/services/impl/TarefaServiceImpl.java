package com.murilonerdx.epictask.services.impl;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.entities.enums.Role;
import com.murilonerdx.epictask.entities.enums.StatusTarefa;
import com.murilonerdx.epictask.repository.TarefaRepository;
import com.murilonerdx.epictask.services.TarefaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TarefaServiceImpl implements TarefaService {

    private final TarefaRepository repository;

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
        Tarefa createTarefa = toModel(tarefa, new Tarefa());

        return repository.save(tarefa);
    }

    @Override
    public Tarefa findByTitleAndPerfil(String title, Perfil perfil){
        perfil = new Perfil(null, "Murilo",null, 200.00);
        return repository.findByTitleAndPerfil(title, perfil);
    }

    @Override
    public Page<Tarefa> searchPaginetedTarefas(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Tarefa toModel(Tarefa tarefa, Tarefa model){
        Perfil perfil = new Perfil(1L, "Murilo",null,200.00);
        model.setDate(LocalDate.now());
        model.setDescription(tarefa.getDescription());
        model.setScore(tarefa.getScore());
        model.setTitle(tarefa.getTitle());
        model.setPerfil(perfil);
        model.setObtain(true);
        model.setProgress(0);
        model.setStatusTask(StatusTarefa.ANALISE);
        return model;
    }
}
