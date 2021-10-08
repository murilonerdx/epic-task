package com.murilonerdx.epictask.config;

import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.Tarefa;
import com.murilonerdx.epictask.entities.Usuario;
import com.murilonerdx.epictask.entities.enums.Role;
import com.murilonerdx.epictask.entities.enums.StatusTarefa;
import com.murilonerdx.epictask.repository.PerfilRepository;
import com.murilonerdx.epictask.repository.TarefaRepository;
import com.murilonerdx.epictask.repository.UsuarioRepository;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.persistence.OneToOne;
import javax.persistence.PostPersist;

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
  private final TarefaRepository taskRepository;

  @Bean
  public boolean instantiateDatabase(){

    Perfil perfil = new Perfil(null, "Murilo",null, 200.00, 2, 2);
    //Senha 123
    Usuario user = new Usuario(null, "mu-silva@outlook.com","$2a$12$QyF4w1FII8opXmjlEX53PuK45.a8MBQI40c7kbQ9o5y1fbUKwHrfW", Role.ADMIN, perfil);

    Perfil perfil2 = new Perfil(null, "Robertinho",null, 250.00, 2, 2);
    //Senha 123
    Usuario user2 = new Usuario(null, "robertinho@hotmail.com","$2a$12$QyF4w1FII8opXmjlEX53PuK45.a8MBQI40c7kbQ9o5y1fbUKwHrfW", Role.ADMIN, perfil2);
    userRepository.saveAll(Arrays.asList(user, user2));

    Tarefa task = new Tarefa(null, "Criar banco de dados Oracle", "Banco de dados com dados populados", LocalDate
        .now(), true,
        StatusTarefa.ANALISE, 0, 250.00, perfil);
    Tarefa task2 = new Tarefa(null, "Criar modelo no Azure", "Alguma coisa", LocalDate.now().minusDays(5), false,StatusTarefa.ANALISE, 60, 150, null);
    taskRepository.saveAll(Arrays.asList(task, task2));
    return false;
  }


}
