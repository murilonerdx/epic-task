package com.murilonerdx.epictask.entities;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.murilonerdx.epictask.entities.enums.Role;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_usuario")
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  @Email
  private String email;
  @Size(min=8, message="A senha deve ter no minimo 8 caracters")
  private String password;
  @Enumerated(value=EnumType.STRING)
  private Role role;

  @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  private Perfil perfil;

  public Usuario(Long id, String email, String password, Role role){
    this.id = id;
    this.email = email;
    this.password = password;
    this.role = role;
  }
}
