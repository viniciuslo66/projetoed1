package com.viniciuslo66.projetoed1.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UsuarioDTO {
  private Long id;
  private String email;
  private String nome;
  private String senha;
}
