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
        Tarefa object = repository.findById(id).get();
        object.setStatusTask(tarefa.getStatusTask());
        object.setPerfil(tarefa.getPerfil());
        object.setProgress(tarefa.getProgress());
        object.setObtain(tarefa.isObtain());
        return repository.save(object);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Tarefa create(Tarefa tarefa) {
        return repository.save(tarefa);
    }

    @Override
    public Tarefa findByTitleAndPerfil(String title, Perfil perfil){
        return repository.findByTitleAndPerfil(title, perfil);
    }

    @Override
    public Page<Tarefa> searchPaginetedTarefas(Pageable pageable) {
        return repository.findAll(pageable);
    }

}
