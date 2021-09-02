package com.murilonerdx.epictask.entities;
import javax.persistence.*;
import javax.validation.constraints.Email;

import com.murilonerdx.epictask.entities.enums.Role;
import lombok.*;
import org.hibernate.validator.constraints.Length;


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
