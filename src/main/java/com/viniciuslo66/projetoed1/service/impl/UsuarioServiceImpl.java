package com.viniciuslo66.projetoed1.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.viniciuslo66.projetoed1.Util.MyList;
import com.viniciuslo66.projetoed1.error.ErroAutenticacao;
import com.viniciuslo66.projetoed1.error.RegraNegocioException;
import com.viniciuslo66.projetoed1.model.entity.Usuario;
import com.viniciuslo66.projetoed1.model.repository.UsuarioRepository;
import com.viniciuslo66.projetoed1.service.UsuarioService;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  private UsuarioRepository repository;

  public UsuarioServiceImpl(UsuarioRepository repository) {
    this.repository = repository;
  }

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

  @Transactional
  public Usuario salvarUsuario(Usuario usuario) {
    validarEmail(usuario.getEmail());
    return repository.save(usuario);
  }

  @Transactional
  public Usuario atualizar(Usuario Task) {
    Objects.requireNonNull(Task.getId());
    validar(Task);
    return repository.save(Task);
  }

  @Override
  public void deletar(Usuario usuario) {
    Objects.requireNonNull(usuario.getId());
    repository.delete(usuario);
  }

  @Transactional(readOnly = true)
  public MyList<Usuario> listar() {
    MyList<Usuario> uList = new MyList<Usuario>();
    uList.addAll(repository.findAll());
    return uList;
  }

  @Transactional(readOnly = true)
  public List<Usuario> buscar(Usuario taskFiltro) {
    Example example = Example.of(taskFiltro,
        ExampleMatcher.matching()
            .withIgnoreCase()
            .withStringMatcher(StringMatcher.EXACT));

    return repository.findAll(example);
  }

  public void validarEmail(String email) {
    boolean existe = repository.existsByEmail(email);
    if (existe) {
      throw new RegraNegocioException("Já existe um usuário com esse email!");
    }
  }

  public Optional<Usuario> obterPorId(Long id) {
    return repository.findById(id);
  }

  @Override
  public void validar(Usuario usuario) {

    if (usuario.getNome() == null || usuario.getNome().trim().equals("")) {
      throw new RegraNegocioException("Informe um nome válido.");
    }

    if (usuario.getEmail() == null) {
      throw new RegraNegocioException("Informe um email válido");
    }

    if (usuario.getSenha() == null) {
      throw new RegraNegocioException("Informe uma senha válida");
    }
  }

}
