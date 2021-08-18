package com.murilonerdx.epictask.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.entities.enums.StatusTarefa;

import com.murilonerdx.epictask.services.TarefaService;
import com.murilonerdx.epictask.validations.TarefaValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "")
public class TarefaController {

    TarefaService service;

    @Autowired
    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @GetMapping("/tarefa")
    public ModelAndView tarefasPendentes() {
        ModelAndView mv = getModelAndView();
        return mv;
    }

    @GetMapping(value = "/tarefa/criarTarefa")
    public String criarTarefa(Tarefa tarefa) {
        return "criarTarefa";
    }

    @GetMapping(value = "/tarefa/concluidas")
    public String tarefasConcluida() {
        return "concluidas";
    }

    @PostMapping("/tarefa")
    public ModelAndView save(@Valid TarefaValidation tarefaValidation, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("criarTarefa");
            mv.addObject("tarefa", tarefaValidation);
            return mv;
        }
        service.create(tarefaValidation);
        return new ModelAndView("tarefas").addObject("tarefas", service.getAll());
    }

    private ModelAndView getModelAndView() {
        ModelAndView mv = new ModelAndView("tarefas");
        mv.addObject("tarefas", service.getAll());
        return mv;
    }

}
