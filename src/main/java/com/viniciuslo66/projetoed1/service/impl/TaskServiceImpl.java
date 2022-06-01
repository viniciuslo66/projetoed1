package com.viniciuslo66.projetoed1.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.viniciuslo66.projetoed1.Util.MyList;
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

  @Transactional
  public Task salvar(Task Task) {
    validar(Task);
    return repository.save(Task);
  }

  @Transactional
  public Task atualizar(Task Task) {
    Objects.requireNonNull(Task.getId());
    validar(Task);
    return repository.save(Task);
  }

  @Transactional
  public void deletar(Task Task) {
    Objects.requireNonNull(Task.getId());
    repository.delete(Task);
  }

  @Transactional(readOnly = true)
  public MyList<Task> listar() {
    MyList<Task> tList = new MyList<Task>();
    tList.addAll(repository.findAll());
    return tList;
  }

  @Transactional(readOnly = true)
  public List<Task> buscar(Task taskFiltro) {
    Example example = Example.of(taskFiltro,
        ExampleMatcher.matching()
            .withIgnoreCase()
            .withStringMatcher(StringMatcher.CONTAINING));

    return repository.findAll(example);
  }

  @Override
  public void validar(Task tarefa) {

    if (tarefa.getCabecalho() == null || tarefa.getCabecalho().trim().equals("")) {
      throw new RegraNegocioException("Informe uma cabeçalho válido.");
    }

    if (tarefa.getConteudo() == null || tarefa.getConteudo().trim().equals("")) {
      throw new RegraNegocioException("Informe um conteúdo válido");
    }
  }

  @Override
  public Optional<Task> obterPorId(Long id) {
    return repository.findById(id);
  }

}
