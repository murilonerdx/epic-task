package com.murilonerdx.epictask.controller;

import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.services.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/tarefa")
public class TarefaController {

    TarefaService service;

    @Autowired
    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView tarefasPendentes() {
        ModelAndView mv = createListTaskView();
        return mv;
    }

    @GetMapping(value = "/criarTarefa")
    public String criarTarefa() {
        return "criarTarefa";
    }

    @GetMapping(value = "/concluidas")
    public String tarefasConcluida() {
        return "concluidas";
    }

    @PostMapping()
    public ModelAndView save(Tarefa task){
        service.create(task);
        ModelAndView mv = createListTaskView();
        return mv;
    }

    private ModelAndView createListTaskView() {
        ModelAndView mv = new ModelAndView("tarefas");
        mv.addObject("tarefas", service.getAll());
        return mv;
    }



}
