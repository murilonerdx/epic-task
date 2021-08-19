package com.murilonerdx.epictask.controller;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.entities.enums.Role;
import com.murilonerdx.epictask.services.impl.PerfilServiceImpl;
import com.murilonerdx.epictask.services.impl.TarefaServiceImpl;
import com.murilonerdx.epictask.services.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping(value = "perfil")
@RequiredArgsConstructor
public class PerfilController {

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

    @RequestMapping(value = "/cadastrarPerfil", method = RequestMethod.POST)
    public ModelAndView save(@Valid Perfil perfil,
                             @RequestParam("photo") MultipartFile photo, @RequestParam("email") String email, @RequestParam("password") String password) throws IOException {
        //Mockado
        if (perfil != null && password != null && email != null) {
            perfil.setData(photo.getBytes());
            Usuario usuario = usuarioService.create(new Usuario(null, email, password, Role.ADMIN, perfil));
            usuarioService.create(usuario);
            return new ModelAndView("tarefas").addObject("tarefas", tarefaService.getAll());
        }
        return new ModelAndView("cadastrarPerfil");

    }

    @RequestMapping(value = "/cadastrarPerfil", method = RequestMethod.GET)
    public ModelAndView tarefasPendentes(Perfil perfil) throws IOException {
        Map<String, Perfil> mapeamento = new HashMap<>();
        File index = new File("src/main/resources/static/img/");
        ModelAndView mv = new ModelAndView("cadastrarPerfil");
        List<Usuario> usuarios = usuarioService.findByRole(Role.ADMIN);

        /** Deletar todos as imagens toda vez que chamar o endpoint
         * Percorrer a pasta e deletar um por um*/
        verifyIfExistsImgs(index);
        createMapAndImgPushView(mapeamento, index, usuarios);

        if(!mapeamento.isEmpty()){
            mv.addObject("perfis", mapeamento);
            return mv;
        }
        return mv;

    }

    private void createMapAndImgPushView(Map<String, Perfil> mapeamento, File index, List<Usuario> usuarios) throws IOException {
        for (Usuario usuario : usuarios) {
            if (usuario.getPerfil().getData() != null) {
                String namespaceURI = usuario.getPerfil().getName().trim();
                File temp = File.createTempFile(namespaceURI, ".jpg", index);
                mapeamento.put(temp.getAbsolutePath(), usuario.getPerfil());
                writesImageInTemp(usuario, temp);
            }
        }
    }

    private void verifyIfExistsImgs(File index) {
        if(index.exists()){
            String[]entries = index.list();
            for(String s: Objects.requireNonNull(entries)){
                File currentFile = new File(index.getPath(),s);
                currentFile.delete();
            }
        }
    }

    private void writesImageInTemp(Usuario usuario, File temp) throws IOException {
        FileOutputStream fos;
        FileDescriptor fd;
        fos = new FileOutputStream(temp.getAbsolutePath());
        fos.write(usuario.getPerfil().getData());
        fd = fos.getFD();
        fos.flush();
        fd.sync();
        fos.close();
    }
}
