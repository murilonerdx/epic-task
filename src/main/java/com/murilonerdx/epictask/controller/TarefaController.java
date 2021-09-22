package com.murilonerdx.epictask.controller;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.repository.UsuarioRepository;
import com.murilonerdx.epictask.services.TarefaService;
import com.murilonerdx.epictask.services.security.IAuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "")
public class TarefaController {

    IAuthenticationFacade authenticationFacade;
    TarefaService service;
    UsuarioRepository usuarioRepository;

    @Autowired
    public TarefaController(TarefaService service, IAuthenticationFacade authenticationFacade,UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.authenticationFacade = authenticationFacade;
        this.service = service;
    }

    @GetMapping({"/", "/tarefas"})
    public String tarefasPendentes(HttpServletRequest request, Model model) {
        getModelAndView(request, model);
        return "tarefas";
    }

    @GetMapping(value = "/criarTarefa")
    public String criarTarefa(Tarefa tarefa, HttpServletRequest request, Model mv) {
        getModelAndView(request, mv);
        return "criarTarefa";
    }

    @GetMapping(value = "/concluidas")
    public String tarefasConcluida(HttpServletRequest request, Model mv) {
        getModelAndView(request, mv);
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

    @PostMapping("/tarefa/desistirTarefa")
    public String desistirTarefa(@ModelAttribute Tarefa tarefa, HttpServletRequest request, Model model){
        Tarefa backTarefa = service.findByTitleAndPerfil(tarefa.getTitle(), authenticationFacade.getSessionUser(model).getPerfil());
        tarefa.setId(backTarefa.getId());
        tarefa.setPerfil(null);
        service.update(tarefa, tarefa.getId());
        getModelAndView(request, model);
        return "tarefas";
    }

    public Model getModelAndView(HttpServletRequest request, Model mv) {
        mv.addAttribute("points", authenticationFacade.getSessionUser(mv).getPerfil().getScore());
        mv.addAttribute("name", authenticationFacade.getSessionUser(mv).getPerfil().getName());

        int page = 0, size = 5;

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
