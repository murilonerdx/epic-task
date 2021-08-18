package com.murilonerdx.epictask.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.murilonerdx.epictask.entities.enums.StatusTarefa;
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
public class Tarefa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Length(min=10, max=8000)
  private String title;

  @Length(max=8000)
  private String description;

  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate date;

  private boolean obtain;

  @Enumerated(EnumType.STRING)
  private StatusTarefa statusTask;

  private Integer progress;

  private double score;

  @OneToOne(fetch = FetchType.LAZY)
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
