package com.murilonerdx.epictask.entities;
import javax.persistence.*;

import com.murilonerdx.epictask.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
  private String email;
  private String password;
  @Enumerated(value=EnumType.STRING)
  private Role role;
}
