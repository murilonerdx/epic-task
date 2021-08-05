package com.murilonerdx.epictask.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class TarefaController {

  @RequestMapping(value="/", method = RequestMethod.GET)
  public String getTarefa(){
    return "index";
  }
}
