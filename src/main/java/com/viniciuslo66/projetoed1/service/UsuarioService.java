package com.viniciuslo66.projetoed1.service;

import java.util.List;
import java.util.Optional;

import com.viniciuslo66.projetoed1.Util.MyList;
import com.viniciuslo66.projetoed1.model.entity.Usuario;

public interface UsuarioService {
  Usuario autenticar(String email, String senha);

  Usuario salvarUsuario(Usuario usuario);

  void validarEmail(String email);

  Usuario atualizar(Usuario usuario);

  MyList<Usuario> listar();

  List<Usuario> buscar(Usuario usuario);

  void deletar(Usuario usuario);

  void validar(Usuario usuario);

  Optional<Usuario> obterPorId(Long id);
}
