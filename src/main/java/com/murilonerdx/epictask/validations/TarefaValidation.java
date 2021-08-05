package com.murilonerdx.epictask.validations;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.murilonerdx.epictask.entities.Perfil;
import com.murilonerdx.epictask.entities.enums.StatusTarefa;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefaValidation implements Serializable {
  @NotEmpty(message="O campo titulo não pode estar vazio")
  @Size(min=10, message="O titulo precisa ter no minimo 10 caracteres")
  private String title;
  @NotEmpty(message="O campo descrição não pode estar vazio")
  @Size(min=20, message="A descrição precisa ter no minimo 20 caracteres")
  private String description;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate date;
  private Boolean subject;
  private StatusTarefa statusTask;
  private Integer progress;
  private Double score;

}
