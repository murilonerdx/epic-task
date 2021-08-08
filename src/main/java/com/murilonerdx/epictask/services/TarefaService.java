package com.murilonerdx.epictask.services;

import com.murilonerdx.epictask.entities.Tarefa;

import java.util.List;

public interface TarefaService {
    List<Tarefa> getAll();
    Tarefa getById(Long id);
    Tarefa update(Tarefa tarefa, Long id);
    void deleteById(Long id);
    Tarefa create(Tarefa tarefa);
}
