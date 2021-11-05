package com.murilonerdx.epictask.controller;

import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.entities.enums.Role;
import com.murilonerdx.epictask.services.impl.UsuarioServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.IOException;

import static com.murilonerdx.epictask.util.ImageTransform.returnBytesDefault;


@Controller
public class  CadastroController {

    private final UsuarioServiceImpl service;

    public CadastroController(UsuarioServiceImpl service) {
        this.service = service;
    }

    @RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
    public String cadastrar(@Valid @ModelAttribute("usuario") Usuario usuario,
                            BindingResult result, Model model) throws IOException {
        boolean verifyExist = service.findByEmail(usuario.getEmail()) != null;
        if (result.hasErrors()) {
            return "cadastrar";
        }
        if (verifyExist) {
            model.addAttribute("passwordValid", "A senha deve ser maior que 8");
            return "cadastrar";
        }

        byte[] imageByteDefault = returnBytesDefault();

        usuario.setRole(Role.USER);
        usuario.getPerfil().setData(imageByteDefault);
        service.create(usuario);

        return "redirect:/login";
    }
}
