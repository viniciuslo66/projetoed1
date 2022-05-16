package com.viniciuslo66.projetoed1.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lancamento", schema = "financas")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "cabecalho")
  private String cabecalho;

  @Column(name = "conteudo")
  private String conteudo;

  @ManyToOne
  @JoinColumn(name = "id_usuario")
  private Usuario usuario;

  @Column(name = "data_cadastro")
  @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
  private LocalDate dataCadastro;
}
