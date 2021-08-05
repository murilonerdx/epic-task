package com.murilonerdx.epictask.repository;

import com.murilonerdx.epictask.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
