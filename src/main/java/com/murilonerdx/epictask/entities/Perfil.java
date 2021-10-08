package com.murilonerdx.epictask.entities;


import javax.persistence.*;

import lombok.*;

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
  @Lob
  private byte[] data;
  private double score;
  private int quantidadeTarefaConcluida;
  private int quantidadeTarefaCriada;


}