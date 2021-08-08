package com.murilonerdx.epictask.controller;

import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.services.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TarefaController {

  @Autowired
  TarefaService service;

  @RequestMapping(value="/", method = RequestMethod.GET)
  public ModelAndView tarefasPendentes(){
    ModelAndView mv = new ModelAndView("tarefas");
    List<Tarefa> tarefas = service.getAll();
    mv.addObject("tarefas",tarefas);
    return mv;
  }

  @RequestMapping(value="/criarTarefa", method = RequestMethod.GET)
  public String criarTarefa(){
    return "criarTarefa";
  }

  @RequestMapping(value="/concluidas", method = RequestMethod.GET)
  public String tarefasConcluida(){
    return "concluidas";
  }

}
