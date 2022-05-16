package com.viniciuslo66.projetoed1.service.impl;

import java.util.Optional;

import com.viniciuslo66.projetoed1.error.ErroAutenticacao;
import com.viniciuslo66.projetoed1.error.RegraNegocioException;
import com.viniciuslo66.projetoed1.model.entity.Usuario;
import com.viniciuslo66.projetoed1.model.repository.UsuarioRepository;
import com.viniciuslo66.projetoed1.service.UsuarioService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  private UsuarioRepository repository;

  public UsuarioServiceImpl(UsuarioRepository repository) {
    this.repository = repository;
  }

  @Override
  public Usuario autenticar(String email, String senha) {
    Optional<Usuario> usuario = repository.findByEmail(email);

    if (!usuario.isPresent()) {
      throw new ErroAutenticacao("Usuário não encontrado.");
    }

    if (!usuario.get().getSenha().equals(senha)) {
      throw new ErroAutenticacao("Senha inválida.");
    }

    return usuario.get();
  }

  @Override
  @Transactional
  public Usuario salvarUsuario(Usuario usuario) {
    validarEmail(usuario.getEmail());
    return repository.save(usuario);
  }

  @Override
  public void validarEmail(String email) {
    boolean existe = repository.existsByEmail(email);
    if (existe) {
      throw new RegraNegocioException("Já existe um usuário com esse email!");
    }
  }

  @Override
  public Optional<Usuario> obterPorId(Long id) {
    return repository.findById(id);
  }
  
}
