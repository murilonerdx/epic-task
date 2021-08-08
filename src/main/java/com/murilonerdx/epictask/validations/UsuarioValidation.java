package com.murilonerdx.epictask.validations;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioValidation {
  @NotBlank(message="O campo email não pode estar vazio")
  @Email
  private String email;
  @NotBlank(message="O campo password não pode estar vazio")
  @Size(min=10, max=24, message="A senha deve estar entre 10 caracters e 24")
  private String password;
}
