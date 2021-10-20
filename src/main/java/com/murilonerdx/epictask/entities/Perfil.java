package com.murilonerdx.epictask.entities;


import javax.persistence.*;
import javax.transaction.Transactional;

import lombok.*;
import org.hibernate.annotations.Type;

import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_perfil")
public class Perfil {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique=true)
  private String name;

  @Basic
  @Lob
  @Type(type = "org.hibernate.type.BinaryType")
  @Column(name = "data")
  private byte[] data;
  private double score;
  private int quantidadeTarefaConcluida;
  private int quantidadeTarefaCriada;


  public Perfil(Long id, String name, byte[] returnBytesDefault) {
    this.id = id;
    this.name=name;
    this.data= returnBytesDefault;
  }
}