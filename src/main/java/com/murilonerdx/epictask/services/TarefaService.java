package com.murilonerdx.epictask.services;

import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.validations.TarefaValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TarefaService {
    List<Tarefa> getAll();
    Tarefa getById(Long id);
    Tarefa update(Tarefa tarefa, Long id);
    void deleteById(Long id);
    Tarefa create(TarefaValidation tarefaValidation);
    Page<Tarefa> searchPaginetedTarefas(Pageable pageable);
}
