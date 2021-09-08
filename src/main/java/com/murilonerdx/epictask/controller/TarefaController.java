package com.murilonerdx.epictask.controller;

import com.murilonerdx.epictask.entities.Tarefa;

import com.murilonerdx.epictask.security.UserDetailsServiceImpl;
import com.murilonerdx.epictask.services.TarefaService;
import com.murilonerdx.epictask.services.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "")
public class TarefaController {

    TarefaService service;


    @Autowired
    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String tarefasPendentes(HttpServletRequest request, Model model) {
        getModelAndView(request, model);
        return "tarefas";
    }

    @GetMapping(value = "/criarTarefa")
    public String criarTarefa(Tarefa tarefa) {
        return "criarTarefa";
    }

    @GetMapping(value = "/concluidas")
    public String tarefasConcluida() {
        return "concluidas";
    }

    @PostMapping("/")
    public ModelAndView listaSalvas(@Valid @ModelAttribute("tarefa") Tarefa tarefa, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("criarTarefa");
            mv.addObject("tarefa", tarefa);
            mv.addObject("errors", result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
            return mv;
        }
        tarefa.setProgress(0);
        service.create(tarefa);
        return new ModelAndView("tarefas").addObject("tarefas", service.searchPaginetedTarefas(PageRequest.of(0, 5)));
    }

//    @PostMapping("/tarefa/desistirTarefa")
//    public String desistirTarefa(@ModelAttribute Tarefa tarefa, HttpServletRequest request, Model model){
//        Tarefa backTarefa = service.findByTitleAndPerfil(tarefa.getTitle(), new Perfil());
//        tarefa.setId(backTarefa.getId());
//        tarefa.setPerfil(null);
//        service.update(tarefa, tarefa.getId());
//        getModelAndView(request, model);
//        return "tarefas";
//    }

    public Model getModelAndView(HttpServletRequest request, Model mv) {
        int page = 0, size =5;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        mv.addAttribute("tarefas", service.searchPaginetedTarefas(PageRequest.of(page, size)));
        return mv;
    }




}
