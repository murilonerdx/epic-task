package com.murilonerdx.epictask.controller;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Tarefa;

import com.murilonerdx.epictask.services.TarefaService;
import com.murilonerdx.epictask.validations.TarefaValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
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

    @GetMapping("/tarefa")
    public String tarefasPendentes(HttpServletRequest request, Model model) {
        getModelAndView(request, model);
        return "tarefas";
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
            mv.addObject("errors", result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
            return mv;
        }
        service.create(tarefaValidation);
        return new ModelAndView("tarefas").addObject("tarefas", service.getAll());
    }

    private Model getModelAndView(HttpServletRequest request, Model mv) {
        int page = 0, size =5; //default page number is 0 (yes it is weird)

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
