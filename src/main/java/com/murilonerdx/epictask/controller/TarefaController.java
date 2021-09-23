package com.murilonerdx.epictask.controller;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.entities.enums.StatusTarefa;
import com.murilonerdx.epictask.repository.TarefaRepository;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "")
public class TarefaController {

    IAuthenticationFacade authenticationFacade;
    TarefaService service;
    UsuarioRepository usuarioRepository;
    TarefaRepository tarefaRepository;

    @Autowired
    public TarefaController(TarefaService service, IAuthenticationFacade authenticationFacade,UsuarioRepository usuarioRepository,TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioRepository = usuarioRepository;
        this.authenticationFacade = authenticationFacade;
        this.service = service;
    }

    @GetMapping({"/", "/tarefas"})
    public String tarefasPendentes(HttpServletRequest request, Model model) {
        getModelAndView(request, model);
        return "tarefas";
    }

    @GetMapping(value = "/tarefas/criarTarefa")
    public String criarTarefa(Tarefa tarefa, HttpServletRequest request, Model mv) {
        getModelAndView(request, mv);
        return "criarTarefa";
    }

    @PostMapping("/tarefas/pegarTarefa/{id}")
    public String pegarTarefa(@PathVariable("id") Long id, HttpServletRequest request, Model mv){
        Tarefa obj = service.getById(id);
        if(obj.isObtain()){
            return "redirect:/tarefas";
        }
        obj.setPerfil(authenticationFacade.getSessionUser(mv).getPerfil());
        obj.setObtain(true);
        service.update(obj, id);
        return "redirect:/tarefas";
    }

    @GetMapping(value = "/concluidas")
    public String tarefasConcluida(HttpServletRequest request, Model mv) {
        getModelAndView(request, mv);
        List<Tarefa> tarefasConcluidasUsuario = tarefaRepository.findByPerfil(authenticationFacade.getSessionUser(mv).getPerfil().getName());
        mv.addAttribute("tarefas", tarefasConcluidasUsuario);
        return "concluidas";
    }

    @PostMapping("/tarefas/criarTarefa")
    public String listaSalvas(@Valid @ModelAttribute("tarefa") Tarefa tarefa, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("tarefa", tarefa);
            model.addAttribute("errors", result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
            return "criarTarefa";
        }
        tarefa.setProgress(0);
        service.create(tarefa);
        getModelAndView(request, model);
        return "redirect:/tarefas";
    }

    @PostMapping("/tarefas/desistirTarefa/{id}")
    public String desistirTarefa(@ModelAttribute("tarefa") Tarefa tarefa, @PathVariable("id") Long id,HttpServletRequest request, Model model){
        Tarefa obj = service.getById(id);
        obj.setPerfil(null);
        obj.setObtain(false);
        service.update(obj, id);
        getModelAndView(request, model);
        return "redirect:/tarefas";
    }

    @GetMapping("/status/tarefa/{id}")
    public ModelAndView statusTarefa(Model model, @PathVariable("id") Long id, HttpServletRequest request){
        Tarefa tarefa = service.getById(id);
        model.addAttribute("tarefa", tarefa);
        if(tarefa.getPerfil() == authenticationFacade.getSessionUser(model).getPerfil()){
            return new ModelAndView("statusTarefa");
        }
        return new ModelAndView("tarefas").addObject("tarefas", service.searchPaginetedTarefas(PageRequest.of(0, 5)));
    }

    @PostMapping("/status/tarefa/{id}")
    public String mudarStatusTarefa(Model model, @PathVariable("id") Long id,HttpServletRequest request, String subjectOrder){
        Tarefa tarefa = service.getById(id);
        tarefa.setStatusTask(Arrays.stream(StatusTarefa.values()).filter(x-> Objects.equals(x.getId(), Integer.parseInt(subjectOrder))).findFirst().orElse(null));
        tarefa.setProgress(Integer.parseInt(subjectOrder) == 0 ? 20 : (Integer.parseInt(subjectOrder) + 1) * 20);
        service.update(tarefa, id);
        return "redirect:/tarefas";
    }

    @PostMapping("/status/finalizarTarefa/{id}")
    public String finalizarTarefa(Model model, @PathVariable("id") Long id,HttpServletRequest request){
        Tarefa tarefa = service.getById(id);
        tarefa.setStatusTask(StatusTarefa.ENTREGUE);
        tarefa.setProgress(100);
        service.update(tarefa, id);
        return "redirect:/tarefas";
    }

    public Model getModelAndView(HttpServletRequest request, Model mv) {
        mv.addAttribute("points", authenticationFacade.getSessionUser(mv).getPerfil().getScore());
        mv.addAttribute("name", authenticationFacade.getSessionUser(mv).getPerfil().getName());
        mv.addAttribute("role", authenticationFacade.getSessionUser(mv).getRole().name().equals("ADMIN"));


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
