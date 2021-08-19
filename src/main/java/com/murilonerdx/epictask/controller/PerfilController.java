package com.murilonerdx.epictask.controller;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.services.impl.PerfilServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "perfil")
public class PerfilController {

    PerfilServiceImpl service;

    @RequestMapping(value="/cadastrarPerfil", method = RequestMethod.GET)
    public String routeCadastrarPerfil(){
        return "cadastrarPerfil";
    }


    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public ModelAndView save(@Valid Perfil perfil,
                             @RequestParam("photo") MultipartFile photo) {
        try {
            //Mockado
            perfil.setUser(new Usuario(1L, "mu-silva@outlook.com", "123"));
            perfil.setData(photo.getBytes());
            service.create(perfil);
            return new ModelAndView("cadastrarPerfil", "msg", "Img succesfully inserted into database.");
        } catch (Exception e) {
            return new ModelAndView("cadastrarPerfil", "msg", "Error: " + e.getMessage());
        }
    }
}
