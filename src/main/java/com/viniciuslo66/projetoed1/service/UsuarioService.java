package com.viniciuslo66.projetoed1.service;

import java.util.Optional;

import com.viniciuslo66.projetoed1.Util.MyList;
import com.viniciuslo66.projetoed1.model.entity.Usuario;

public interface UsuarioService {
  Usuario autenticar(String email, String senha);

  Usuario salvarUsuario(Usuario usuario);

  void validarEmail(String email);

  MyList<Usuario> listar();

  Optional<Usuario> obterPorId(Long id);
}
