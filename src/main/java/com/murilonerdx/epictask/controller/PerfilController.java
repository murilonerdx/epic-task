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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.activation.FileTypeMap;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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

//    @RequestMapping(value = "/cadastrarPerfil", method = RequestMethod.GET)
//    public String routeCadastrarPerfil(Perfil perfil) {
//        return "cadastrarPerfil";
//    }

    @RequestMapping(value = "/perfil/cadastrarPerfil", method = RequestMethod.POST)
    public ModelAndView save(@Valid Perfil perfil,
                             @RequestParam("photo") MultipartFile photo,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             BindingResult result,
                             Model model) throws IOException {
        ModelAndView md = new ModelAndView("cadastrarPerfil");
        Usuario existUsuario = usuarioService.findByEmail(email);

        if(existUsuario != null){
            md.addObject("emailField", "Email já existe no banco de dados");
            return md;
        }else if(password.length() < 8){
            md.addObject("passwordField", "A senha precisa ser maior que 8");
            return md;
        }else if(email.isEmpty() || password.isEmpty() || perfil.getName().isEmpty()){
            md.addObject("fieldsInvalid", "Digite todos campos corretamente");
            return md;
        }

        if (perfil != null && existUsuario == null ) {
            byte[] imageByteDefault = returnBytesDefault();
            perfil.setData(photo.getOriginalFilename().isEmpty() ? imageByteDefault : photo.getBytes());
            Usuario usuario = usuarioService.create(new Usuario(null, email, password, Role.ADMIN, perfil));
            usuarioService.create(usuario);
            return new ModelAndView("tarefas").addObject("tarefas", tarefaService.searchPaginetedTarefas(PageRequest.of(0, 5)));
        }
        return md;
    }

    @RequestMapping(value = "/perfil/cadastrarPerfil", method = RequestMethod.GET)
    public ModelAndView tarefasPendentes(Perfil perfil) throws IOException {
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

        mv.addObject("perfil",mapeamento );
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
