package com.murilonerdx.epictask.services;

import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.validations.TarefaValidation;

public interface UsuarioService {
    Usuario create(Usuario usuario);
}
