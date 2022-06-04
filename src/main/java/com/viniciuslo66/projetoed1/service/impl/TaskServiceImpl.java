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
  public Task salvar(Task task) {
    validar(task);
    return repository.save(task);
  }

  @Transactional
  public Task atualizar(Task task) {
    Objects.requireNonNull(task.getId());
    validar(task);
    return repository.save(task);
  }

  @Transactional
  public void deletar(Task task) {
    Objects.requireNonNull(task.getId());
    repository.delete(task);
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
  public void validar(Task task) {

    if (task.getCabecalho() == null || task.getCabecalho().trim().equals("")) {
      throw new RegraNegocioException("Informe uma cabeçalho válido.");
    }

    if (task.getConteudo() == null || task.getConteudo().trim().equals("")) {
      throw new RegraNegocioException("Informe um conteúdo válido");
    }
  }

  @Override
  public Optional<Task> obterPorId(Long id) {
    return repository.findById(id);
  }

}
