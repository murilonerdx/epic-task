package com.murilonerdx.epictask.controller;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.entities.enums.Role;
import com.murilonerdx.epictask.services.impl.PerfilServiceImpl;
import com.murilonerdx.epictask.services.impl.TarefaServiceImpl;
import com.murilonerdx.epictask.services.impl.UsuarioServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping(value = "perfil")
@RequiredArgsConstructor
public class PerfilController {

    PerfilServiceImpl perfilService;
    TarefaServiceImpl tarefaService;
    UsuarioServiceImpl usuarioService;

    @Autowired
    public PerfilController(PerfilServiceImpl perfilService, TarefaServiceImpl tarefaService, UsuarioServiceImpl usuarioService){
        this.perfilService = perfilService;
        this.tarefaService = tarefaService;
        this.usuarioService = usuarioService;
    }

    @RequestMapping(value = "/cadastrarPerfil", method = RequestMethod.GET)
    public String routeCadastrarPerfil(Perfil perfil) {
        return "cadastrarPerfil";
    }

    @RequestMapping(value = "/cadastrarPerfil", method = RequestMethod.POST)
    public ModelAndView save(@Valid Perfil perfil,
                             @RequestParam("photo") MultipartFile photo, @RequestParam("email") String email, @RequestParam("password") String password) throws IOException {
        //Mockado
        if(perfil != null && password != null && email != null){
            Usuario usuario = usuarioService.create(new Usuario(null, email, password, Role.ADMIN));
            perfil.setUser(usuario);
            perfil.setData(photo.getBytes());
            perfilService.create(perfil);
            return new ModelAndView("tarefas").addObject("tarefas", tarefaService.getAll());
        }
        return new ModelAndView("cadastrarPerfil");

    }
}
