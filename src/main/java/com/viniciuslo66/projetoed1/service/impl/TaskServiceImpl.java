package com.viniciuslo66.projetoed1.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.viniciuslo66.projetoed1.error.RegraNegocioException;
import com.viniciuslo66.projetoed1.model.entity.Task;
import com.viniciuslo66.projetoed1.model.repository.TaskRepository;
import com.viniciuslo66.projetoed1.service.TaskService;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskServiceImpl implements TaskService {

  private TaskRepository repository;

  public TaskServiceImpl(TaskRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public Task salvar(Task Task) {
    validar(Task);
    return repository.save(Task);
  }

  @Override
  @Transactional
  public Task atualizar(Task Task) {
    Objects.requireNonNull(Task.getId());
    validar(Task);
    return repository.save(Task);
  }

  @Override
  @Transactional
  public void deletar(Task Task) {
    Objects.requireNonNull(Task.getId());
    repository.delete(Task);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Task> buscar(Task TaskFiltro) {
    Example example = Example.of(TaskFiltro,
        ExampleMatcher.matching()
            .withIgnoreCase()
            .withStringMatcher(StringMatcher.CONTAINING));

    return repository.findAll(example);
  }

  @Override
  public void validar(Task lancamento) {

    if (lancamento.getCabecalho() == null || lancamento.getCabecalho().trim().equals("")) {
      throw new RegraNegocioException("Informe uma cabeçalho válido.");
    }

    if (lancamento.getConteudo() == null || lancamento.getConteudo().trim().equals("")) {
      throw new RegraNegocioException("Informe um conteúdo válido");
    }
  }

  @Override
  public Optional<Task> obterPorId(Long id) {
    return repository.findById(id);
  }

}
