package com.murilonerdx.epictask.util;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Usuario;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class ImageTransform {
    static final String SUFFIX_IMG = ".jpg";

    public static void createMapAndImgPushView(Map<String, Perfil> mapeamento, File index, List<Usuario> usuarios) throws IOException {
        for (Usuario usuario : usuarios) {
            if (usuario.getPerfil().getData() != null) {
                String namespaceURI = usuario.getPerfil().getName().trim();
                File temp = File.createTempFile(namespaceURI, SUFFIX_IMG, index);
                writesImageInTemp(usuario, temp);
                mapeamento.put(temp.getName(), usuario.getPerfil());
            }else{
                mapeamento.put("../show/perfil.jpg", usuario.getPerfil());
            }
        }

    }

    public static void verifyIfExistsImgs(File index) {
        if(index.exists()){
            String[]entries = index.list();
            for(String s: Objects.requireNonNull(entries)){
                File currentFile = new File(index.getPath(),s);
                currentFile.delete();
            }
        }
    }

    private static void writesImageInTemp(Usuario usuario, File temp) throws IOException {
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
