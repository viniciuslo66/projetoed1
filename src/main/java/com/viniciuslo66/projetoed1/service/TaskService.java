package com.viniciuslo66.projetoed1.service;

import java.util.List;
import java.util.Optional;

import com.viniciuslo66.projetoed1.model.entity.Task;

public interface TaskService {
  Task salvar(Task Task);
	
	Task atualizar(Task Task);
	
	void deletar(Task Task);
	
	List<Task> buscar( Task TaskFiltro );

  void validar (Task Task);

	Optional<Task> obterPorId(Long id);
}
