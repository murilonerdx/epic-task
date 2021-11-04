package com.murilonerdx.epictask.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.murilonerdx.epictask.entities.enums.StatusTarefa;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_tarefa")
public class Tarefa implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message="O campo titulo não pode estar vazio")
  @Size(min=5, message="O titulo precisa ter no minimo 10 caracteres")
  private String title;

  @NotBlank(message="O campo descrição não pode estar vazio")
  @Size(min=10, max=8000, message="A descrição precisa ter no minimo 20 caracteres")
  private String description;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate date;

  @Getter
  private boolean obtain;

  @Enumerated(EnumType.STRING)
  private StatusTarefa statusTask;

  private Integer progress;

  private double score;

  @OneToOne(fetch = FetchType.EAGER)
  private Perfil perfil;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Tarefa task = (Tarefa) o;

    return id != null && id.equals(task.id);
  }

  @Override
  public int hashCode() {
    return 1976597858;
  }
}
