package com.viniciuslo66.projetoed1.model.repository;

import com.viniciuslo66.projetoed1.model.entity.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  
}
