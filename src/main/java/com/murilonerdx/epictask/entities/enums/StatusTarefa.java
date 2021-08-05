package com.murilonerdx.epictask.entities.enums;

public enum StatusTarefa {
  /**
   * Quando a tarefa for entregue de acordo com o prazo
   */
  ENTREGUE("Entregue"),
  /**
   * Quando a tarefa for finalizada
   */
  CONCLUIDO("Concluido"),
  /**
   * Quando a tarefa estiver sendo validada e implementando testes
   */
  TESTE("Teste e validação"),
  /**
   * Tarefa sendo desenvolvida
   */
  DESENVOLVIMENTO("Em Desenvolvimento"),
  /**
   * Analisando requisitos para desenvolver a tarefa
   */
  ANALISE("Analise de requisitos");

  private String description;

  StatusTarefa(String action) {
    this.description = action;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
