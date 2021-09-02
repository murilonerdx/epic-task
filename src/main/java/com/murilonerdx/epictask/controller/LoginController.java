package com.murilonerdx.epictask.controller;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository repository;

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(Usuario usuario){
        return "login";
    }

    @RequestMapping(value="/fazerLogin", method = RequestMethod.POST)
    public String fazerLogin(@Valid Usuario user,  BindingResult result, Model model){
        Usuario buscarUsuario = repository.findByEmail(user.getEmail());
        if(buscarUsuario != null && buscarUsuario.getPassword().equals(user.getPassword())){
            return "redirect:/tarefas";
        }
        return "login";
    }

    @RequestMapping(value="/cadastrar", method = RequestMethod.GET)
    public String cadastrar(Usuario usuario){
        return "cadastrar";
    }

}
