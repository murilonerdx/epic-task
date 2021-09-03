package com.murilonerdx.epictask.controller;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.repository.UsuarioRepository;
import com.murilonerdx.epictask.services.impl.TarefaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TarefaServiceImpl tarefaService;

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(Usuario usuario){
        return "login";
    }

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public ModelAndView fazerLogin(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model){
        Usuario buscarUsuario = repository.findByEmail(usuario.getEmail());
        if(buscarUsuario != null && buscarUsuario.getPassword().equals(usuario.getPassword())){
            return new ModelAndView("tarefas").addObject("tarefas", tarefaService.searchPaginetedTarefas(PageRequest.of(0, 5)));
        }
        model.addAttribute("errorValid", "Email ou senha invalido");
        return new ModelAndView("login");
    }

    @RequestMapping(value="/cadastrar", method = RequestMethod.GET)
    public String cadastrar(Usuario usuario){
        return "cadastrar";
    }

}
