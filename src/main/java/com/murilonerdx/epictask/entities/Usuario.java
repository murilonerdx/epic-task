package com.murilonerdx.epictask.entities;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.murilonerdx.epictask.entities.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_usuario")
public class Usuario implements UserDetails, Comparable<Usuario>{
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public int compareTo(Usuario o) {
    if(getPerfil().getScore() > o.getPerfil().getScore()){
      return -1;
    }if(getPerfil().getScore() < o.getPerfil().getScore()){
      return 1;
    }
    return 0;
  }
}
