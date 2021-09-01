package com.murilonerdx.epictask.repository;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Tarefa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    Tarefa findByTitleAndPerfil(String title, Perfil perfil);
    Page<Tarefa> findByTitleContaining(String title, Pageable pageable);
}
