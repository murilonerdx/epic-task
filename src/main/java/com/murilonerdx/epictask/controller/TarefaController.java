package com.murilonerdx.epictask.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
  public TarefaController(
      TarefaService service,
      IAuthenticationFacade authenticationFacade,
      UsuarioRepository usuarioRepository,
      TarefaRepository tarefaRepository) {
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
  public String pegarTarefa(@PathVariable("id") Long id, Model mv) {
    Tarefa obj = service.getById(id);
    if (obj.isObtain()) {
      return "redirect:/tarefas";
    }
    obj.setPerfil(authenticationFacade.getSessionUser(mv).getPerfil());
    obj.setObtain(true);
    tarefaRepository.save(obj);
    return "redirect:/tarefas";
  }

  @GetMapping(value = "/tarefas/concluidas")
  public String tarefasConcluida(HttpServletRequest request, Model mv) {
    getModelAndView(request, mv);
    List<Tarefa> tarefasConcluidasUsuario =
        tarefaRepository.findByPerfil(
            authenticationFacade.getSessionUser(mv).getPerfil().getName());
    mv.addAttribute("tarefas", tarefasConcluidasUsuario);
    return "concluidas";
  }

  @GetMapping(value = "/tarefas/minhasTarefas")
  public String minhasTarefas(HttpServletRequest request, Model mv) {
    getModelAndView(request, mv);
    mv.addAttribute(
        "tarefas",
        tarefaRepository.findUniqueByPerfilName(
            authenticationFacade.getSessionUser(mv).getPerfil().getName()));
    return "minhasTarefas";
  }

  @GetMapping(value = "/tarefas/listarFinalizarTarefas")
  public String listarTarefasFinalizadas(HttpServletRequest request, Model mv) {
    getModelAndView(request, mv);
    mv.addAttribute("tarefas", tarefaRepository.findByTarefaWhereProgressFinal());
    return "listarFinalizarTarefas";
  }

  @PostMapping(value = "/tarefas/concluidas/pegarPontos/{id}")
  public String pegarPontos(Model mv, @PathVariable("id") Long id) {
    Double points = authenticationFacade.getSessionUser(mv).getPerfil().getScore();

    Tarefa tarefa = service.getById(id);
    Usuario usuario =
        usuarioRepository.findByEmail(authenticationFacade.getSessionUser(mv).getEmail()).get();
    usuario
        .getPerfil()
        .setQuantidadeTarefaConcluida(usuario.getPerfil().getQuantidadeTarefaConcluida() + 1);
    if (tarefa.getPerfil().getName().equals(usuario.getPerfil().getName())) {
      usuario.getPerfil().setScore(points + tarefa.getScore());
      usuarioRepository.save(usuario);
      service.deleteById(id);
    }
    return "redirect:/tarefas";
  }

  @PostMapping("/tarefas/criarTarefa")
  public String listaSalvas(
      @Valid @ModelAttribute("tarefa") Tarefa tarefa,
      BindingResult result,
      Model model,
      HttpServletRequest request) {
    if (result.hasErrors()) {
      model.addAttribute("tarefa", tarefa);
      model.addAttribute(
          "errors",
          result.getFieldErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.toList()));
      return "criarTarefa";
    }
    tarefa.setDate(LocalDate.now());
    tarefa.setStatusTask(StatusTarefa.ANALISE);

    Usuario usuario =
        usuarioRepository.findByEmail(authenticationFacade.getSessionUser(model).getEmail()).get();
    usuario
        .getPerfil()
        .setQuantidadeTarefaCriada(usuario.getPerfil().getQuantidadeTarefaCriada() + 1);
    usuarioRepository.save(usuario);

    tarefa.setProgress(0);
    service.create(tarefa);
    return "redirect:/tarefas";
  }

  @PostMapping("/tarefas/desistirTarefa/{id}")
  public String desistirTarefa(
      @ModelAttribute("tarefa") Tarefa tarefa,
      @PathVariable("id") Long id,
      HttpServletRequest request,
      Model model) {
    tarefa.setPerfil(null);
    tarefa.setObtain(false);
    if(tarefa.getProgress() == null) tarefa.setProgress(0);
    service.update(tarefa, id);
    return "redirect:/tarefas";
  }

  @GetMapping("/status/tarefa/{id}")
  public String statusTarefa(
      Model model, @PathVariable("id") Long id, HttpServletRequest request) {

    Tarefa tarefa = tarefaRepository.findById(id).get();
    getModelAndView(request, model);
    model.addAttribute(tarefa);
    return "statusTarefa";
  }

  @PostMapping("/status/tarefa/{id}")
  public String mudarStatusTarefa(
      Model model, @PathVariable("id") Long id, HttpServletRequest request, String subjectOrder) {
    Tarefa tarefa = service.getById(id);
    if (Integer.parseInt(subjectOrder) < 4) {
      tarefa.setStatusTask(
          Arrays.stream(StatusTarefa.values())
              .filter(x -> Objects.equals(x.getId(), Integer.parseInt(subjectOrder)))
              .findFirst()
              .orElse(null));
      tarefa.setProgress(
          Integer.parseInt(subjectOrder) == 0 ? 20 : (Integer.parseInt(subjectOrder) + 1) * 20);
      service.update(tarefa, id);
      return "redirect:/tarefas";
    }
    return "redirect:/tarefas";
  }

  @PostMapping("/status/finalizarTarefa/{id}")
  public String finalizarTarefa(
      Model model, @PathVariable("id") Long id, HttpServletRequest request) {
    Tarefa tarefa = service.getById(id);
    tarefa.setStatusTask(StatusTarefa.ENTREGUE);
    tarefa.setProgress(100);
    service.update(tarefa, id);
    return "redirect:/tarefas";
  }

  public Model getModelAndView(HttpServletRequest request, Model mv) {
    mv.addAttribute("points", authenticationFacade.getSessionUser(mv).getPerfil().getScore());
    mv.addAttribute("name", authenticationFacade.getSessionUser(mv).getPerfil().getName());
    mv.addAttribute(
        "role", authenticationFacade.getSessionUser(mv).getRole().name().equals("ADMIN"));

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

  @GetMapping(value = "/tarefas/ranking")
  public ModelAndView ranking(Model mv, HttpServletRequest request) {
    getModelAndView(request, mv);
    List<Usuario> usuarios = usuarioRepository.findAll();
    return new ModelAndView("ranking")
        .addObject(
            "usuarios",
            usuarios.subList(0, Math.min(usuarios.size(), 10)).stream()
                .sorted(Usuario::compareTo)
                .collect(Collectors.toList()));
  }
}
