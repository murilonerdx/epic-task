package com.murilonerdx.epictask.services.impl;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.entities.enums.StatusTarefa;
import com.murilonerdx.epictask.repository.TarefaRepository;
import com.murilonerdx.epictask.services.TarefaService;
import com.murilonerdx.epictask.validations.TarefaValidation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
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
    public Tarefa create(TarefaValidation tarefaValidation) {
        Tarefa tarefa = toModel(tarefaValidation, new Tarefa());

        return repository.save(tarefa);
    }

    public Tarefa toModel(TarefaValidation DTO, Tarefa model){
        Usuario user = new Usuario(1L, "mu-silva@outlook.com","123");
        Perfil perfil = new Perfil(1L, "Murilo", user,new byte[Byte.parseByte("asdopiasdj0912ncasco0na0s0fans0f0i")],200.00);
        model.setDate(LocalDate.now());
        model.setDescription(DTO.getDescription());
        model.setScore(DTO.getScore());
        model.setTitle(DTO.getTitle());
        model.setPerfil(perfil);
        model.setObtain(true);
        model.setProgress(0);
        model.setStatusTask(StatusTarefa.ANALISE);
        return model;
    }
}
