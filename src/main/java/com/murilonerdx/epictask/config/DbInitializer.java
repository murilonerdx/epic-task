package com.murilonerdx.epictask.config;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.entities.enums.StatusTarefa;
import com.murilonerdx.epictask.repository.PerfilRepository;
import com.murilonerdx.epictask.repository.TarefaRepository;
import com.murilonerdx.epictask.repository.UsuarioRepository;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import javax.persistence.OneToOne;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
@RequiredArgsConstructor
public class DbInitializer {

  private final UsuarioRepository userRepository;
  private final PerfilRepository perfilRepository;
  private final TarefaRepository taskRepository;


  @Bean
  public boolean instantiateDatabase(){
    Usuario user = new Usuario(null, "mu-silva@outlook.com","123");
    userRepository.save(user);

    Perfil perfil = new Perfil(null, "Murilo", user,"a", 200.00);
    Tarefa task = new Tarefa(null, "Criar banco de dados Oracle", "Banco de dados com dados populados", LocalDate
        .now(), true,
        StatusTarefa.ANALISE, 0, 150.00, perfil);
    Tarefa task2 = new Tarefa(null, "Criar modelo no Azure", "Alguma coisa", LocalDate.now().minusDays(5), false,StatusTarefa.ANALISE, 0, 150, null);
    perfilRepository.save(perfil);
    taskRepository.saveAll(Arrays.asList(task, task2));
    return false;
  }


}