package com.murilonerdx.epictask.entities.enums;

public enum StatusTarefa {
  /**
   * Quando a tarefa for entregue de acordo com o prazo
   */
  ENTREGUE(4,"Entregue"),
  /**
   * Quando a tarefa for finalizada
   */
  CONCLUIDO(3,"Concluido"),
  /**
   * Quando a tarefa estiver sendo validada e implementando testes
   */
  TESTE(2,"Teste e validação"),
  /**
   * Tarefa sendo desenvolvida
   */
  DESENVOLVIMENTO(1,"Em Desenvolvimento"),
  /**
   * Analisando requisitos para desenvolver a tarefa
   */
  ANALISE(0,"Analise de requisitos");

  private Integer id;
  private String description;

  StatusTarefa(Integer id, String action) {
    this.id = id;
    this.description = action;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
