package com.viniciuslo66.projetoed1.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TaskDTO {
  private Long id;
	private String cabecalho;
	private String conteudo;
	private Long usuario;
}
