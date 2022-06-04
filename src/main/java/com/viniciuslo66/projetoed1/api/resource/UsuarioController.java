package com.viniciuslo66.projetoed1.api.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.viniciuslo66.projetoed1.Util.MyList;
import com.viniciuslo66.projetoed1.api.dto.UsuarioDTO;
import com.viniciuslo66.projetoed1.error.ErroAutenticacao;
import com.viniciuslo66.projetoed1.error.RegraNegocioException;
import com.viniciuslo66.projetoed1.model.entity.Usuario;
import com.viniciuslo66.projetoed1.service.UsuarioService;

import java.util.List;

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
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

  private final UsuarioService service;

  @GetMapping
  public ResponseEntity<MyList<Usuario>> listar() throws JsonProcessingException {
    MyList<Usuario> uList = service.listar();
    return ResponseEntity.ok().body(uList);
  }

  @GetMapping("/buscar")
  public ResponseEntity<?> buscar(
      @RequestParam(value = "email") String email) {

    Usuario usuarioFiltro = new Usuario();
    usuarioFiltro.setEmail(email);

    List<Usuario> task = service.buscar(usuarioFiltro);
    return ResponseEntity.ok(task);
  }

  @PostMapping("/autenticar")
  public ResponseEntity<?> autenticar(@RequestBody UsuarioDTO dto) {
    try {
      Usuario usuarioAutenticar = service.autenticar(dto.getEmail(), dto.getSenha());
      return ResponseEntity.ok(usuarioAutenticar);
    } catch (ErroAutenticacao e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody UsuarioDTO dto) {
    return service.obterPorId(id).map(entity -> {
      try {
        Usuario usuario = converter(dto);
        usuario.setId(entity.getId());
        service.atualizar(usuario);
        return ResponseEntity.ok(usuario);
      } catch (RegraNegocioException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }).orElseGet(() -> new ResponseEntity("Usuário não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
    return service.obterPorId(id).map(entidade -> {
      service.deletar(entidade);
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    }).orElseGet(() -> new ResponseEntity("Tarefa não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
  }

  @PostMapping
  public ResponseEntity<?> salvar(@RequestBody UsuarioDTO dto) {

    Usuario usuario = Usuario.builder()
        .nome(dto.getNome())
        .email(dto.getEmail())
        .senha(dto.getSenha()).build();

    try {
      Usuario usuarioSalvo = service.salvarUsuario(usuario);
      return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
    } catch (RegraNegocioException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  private Usuario converter(UsuarioDTO dto) {
    Usuario usuario = new Usuario();
    usuario.setId(dto.getId());
    usuario.setEmail(dto.getEmail());
    usuario.setNome(dto.getNome());
    usuario.setSenha(dto.getSenha());
    return usuario;
  }
}
