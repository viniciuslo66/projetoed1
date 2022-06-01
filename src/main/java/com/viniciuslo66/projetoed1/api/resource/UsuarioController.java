package com.viniciuslo66.projetoed1.api.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.viniciuslo66.projetoed1.Util.MyList;
import com.viniciuslo66.projetoed1.api.dto.UsuarioDTO;
import com.viniciuslo66.projetoed1.error.ErroAutenticacao;
import com.viniciuslo66.projetoed1.error.RegraNegocioException;
import com.viniciuslo66.projetoed1.model.entity.Usuario;
import com.viniciuslo66.projetoed1.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

  @PostMapping("/autenticar")
  public ResponseEntity<?> autenticar(@RequestBody UsuarioDTO dto) {
    try {
      Usuario usuarioAutenticar = service.autenticar(dto.getEmail(), dto.getSenha());
      return ResponseEntity.ok(usuarioAutenticar);
    } catch (ErroAutenticacao e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
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
}
