package com.murilonerdx.epictask.repository;

import com.murilonerdx.epictask.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.role = 'ADMIN' OR u.role = 'USER'")
    List<Usuario> findByRole();
    Optional<Usuario> findByEmail(String email);
}
