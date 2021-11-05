package com.murilonerdx.epictask.repository;

import com.murilonerdx.epictask.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}
