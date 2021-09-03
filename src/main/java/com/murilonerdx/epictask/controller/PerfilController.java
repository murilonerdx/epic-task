package com.murilonerdx.epictask.controller;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.entities.enums.Role;
import com.murilonerdx.epictask.services.impl.PerfilServiceImpl;
import com.murilonerdx.epictask.services.impl.TarefaServiceImpl;
import com.murilonerdx.epictask.services.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.activation.FileTypeMap;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.murilonerdx.epictask.util.ImageTransform.*;

@SuppressWarnings("ALL")
@Controller
@RequiredArgsConstructor
public class PerfilController {

    static final String PATH_LOCAL_IMG = "src/main/resources/static/img/";


    PerfilServiceImpl perfilService;
    TarefaServiceImpl tarefaService;
    UsuarioServiceImpl usuarioService;


    @Autowired
    public PerfilController(PerfilServiceImpl perfilService, TarefaServiceImpl tarefaService, UsuarioServiceImpl usuarioService) {
        this.perfilService = perfilService;
        this.tarefaService = tarefaService;
        this.usuarioService = usuarioService;
    }

    @RequestMapping(value = "/perfil/cadastrarPerfil", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("usuario") @Valid Usuario usuario,
                             @RequestParam("photo") MultipartFile photo,
                             BindingResult result,
                             Model model
    ) throws IOException {
        Usuario existUsuario = usuarioService.findByEmail(usuario.getEmail());

        if (result.hasErrors()) {
            return new ModelAndView("cadastrarPerfil");
        }

        //Validação para password não está funcionando de acordo com o esperado
        if (usuario.getPassword().length() < 8) {
            new ModelAndView("cadastrarPerfil").addObject("fieldPasswordInvalid", "A senha deve ter no minimo 8 caracters");
            return new ModelAndView("cadastrarPerfil");
        }

        if (existUsuario != null) {
            new ModelAndView("cadastrarPerfil").addObject("emailField", "Email já existe no banco de dados");
            return new ModelAndView("cadastrarPerfil");
        }

        if (usuario.getPerfil() != null && existUsuario == null) {
            byte[] imageByteDefault = returnBytesDefault();
            usuario.getPerfil().setData(photo.getOriginalFilename().isEmpty() ? imageByteDefault : photo.getBytes());
            usuarioService.create(new Usuario(null, usuario.getEmail(), usuario.getPassword(), Role.ADMIN, usuario.getPerfil()));
            return new ModelAndView("tarefas").addObject("tarefas", tarefaService.searchPaginetedTarefas(PageRequest.of(0, 5)));
        }
        return new ModelAndView("cadastrarPerfil");
    }

    @RequestMapping(value = "/perfil/cadastrarPerfil", method = RequestMethod.GET)
    public ModelAndView tarefasPendentes(Usuario usuario) throws IOException {
        Map<String, Perfil> mapeamento = new HashMap<>();
        File index = new File(PATH_LOCAL_IMG);
        ModelAndView mv = new ModelAndView("cadastrarPerfil");
        List<Usuario> usuarios = usuarioService.findByRole();

        /* Deletar todos as imagens toda vez que chamar o endpoint
          Percorrer a pasta e deletar um por um*/
        verifyIfExistsImgs(index);
        createMapAndImgPushView(mapeamento, index, usuarios);

        if (!mapeamento.isEmpty()) {
            mv.addObject("perfis", mapeamento);
            return mv;
        }

        mv.addObject("perfil", mapeamento);
        return mv;
    }

    @RequestMapping(value = "/download/{linkImage}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<byte[]> testphoto(@PathVariable("linkImage") String imagem) throws IOException {
        File file = new File(PATH_LOCAL_IMG + imagem);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + file.getName())
                .contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(file)))
                .body(Files.readAllBytes(file.toPath()));
    }

    //Mostrar imagem
    @RequestMapping(value = "/img/{linkImage}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("linkImage") String imagem) throws IOException {
        File img = new File(PATH_LOCAL_IMG + imagem);
        return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));
    }


}
