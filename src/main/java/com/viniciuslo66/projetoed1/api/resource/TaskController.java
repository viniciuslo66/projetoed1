package com.viniciuslo66.projetoed1.api.resource;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.viniciuslo66.projetoed1.Util.MyList;
import com.viniciuslo66.projetoed1.api.dto.TaskDTO;
import com.viniciuslo66.projetoed1.error.RegraNegocioException;
import com.viniciuslo66.projetoed1.model.entity.Task;
import com.viniciuslo66.projetoed1.model.entity.Usuario;
import com.viniciuslo66.projetoed1.service.TaskService;
import com.viniciuslo66.projetoed1.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService service;
  private final UsuarioService usuarioService;

  @GetMapping
  public ResponseEntity<MyList<Task>> listar() throws JsonProcessingException {
    MyList<Task> tList = service.listar();
    return ResponseEntity.ok().body(tList);
  }

  @GetMapping("/buscar")
  public ResponseEntity<?> buscar(
      @RequestParam(value = "cabecalho", required = false) String cabecalho) {

    Task taskFiltro = new Task();
    taskFiltro.setCabecalho(cabecalho);

    List<Task> task = service.buscar(taskFiltro);
    return ResponseEntity.ok(task);
  }

  @PostMapping
  public ResponseEntity<?> salvar(@RequestBody TaskDTO dto) {
    try {
      Task entidade = converter(dto);
      entidade = service.salvar(entidade);
      return new ResponseEntity(entidade, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody TaskDTO dto) {
    return service.obterPorId(id).map(entity -> {
      try {
        Task task = converter(dto);
        task.setId(entity.getId());
        service.atualizar(task);
        return ResponseEntity.ok(task);
      } catch (RegraNegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }).orElseGet(() -> new ResponseEntity("Tarefa não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
    return service.obterPorId(id).map(entidade -> {
      service.deletar(entidade);
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    }).orElseGet(() -> new ResponseEntity("Tarefa não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
  }

  private Task converter(TaskDTO dto) {
    Task task = new Task();
    task.setId(dto.getId());
    task.setCabecalho(dto.getCabecalho());
    task.setConteudo(dto.getConteudo());

    Usuario usuario = usuarioService
        .obterPorId(dto.getUsuario())
        .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado para o Id informado."));

    task.setUsuario(usuario);

    return task;
  }
}
