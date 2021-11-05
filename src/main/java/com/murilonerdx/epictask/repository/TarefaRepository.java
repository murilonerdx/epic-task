package com.murilonerdx.epictask.repository;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Tarefa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    Tarefa findByTitleAndPerfil(String title, Perfil perfil);
    Page<Tarefa> findByTitleContaining(String title, Pageable pageable);
    @Query("SELECT u FROM Tarefa u WHERE u.perfil.name = :name AND u.progress = 100")
    List<Tarefa> findByPerfil(@Param("name") String name);
    @Query("SELECT u FROM Tarefa u WHERE u.perfil.name = :name")
    List<Tarefa> findUniqueByPerfilName(@Param("name") String name);
    @Query("SELECT u FROM Tarefa u WHERE u.progress = 80")
    List<Tarefa> findByTarefaWhereProgressFinal();
}
