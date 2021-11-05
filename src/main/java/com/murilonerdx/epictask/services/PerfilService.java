package com.murilonerdx.epictask.services;

import com.murilonerdx.epictask.entities.Perfil;

import java.util.List;

public interface PerfilService {
    List<Perfil> getAll();
    Perfil create(Perfil perfil);
}
