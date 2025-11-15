package com.sapiens.humain.controller;

import com.sapiens.humain.model.Usuario;
import com.sapiens.humain.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/humain/usuario")
public class UsuarioController {
    private final UsuarioService service;
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUser(@RequestBody Usuario dados) {
        Usuario salvo = service.cadastrarUser(dados);
        return ResponseEntity.created(URI.create("/humain/usuario" + salvo.getId())).body(salvo);
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<Usuario> buscarUser(@PathVariable Long idUser) {
        return service.buscarPorId(idUser)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<Usuario> atualizarUser(@PathVariable Long idUser, @RequestBody Usuario dados) {
        return service.atualizarUser(idUser, dados)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity<Usuario> deletarUser(@PathVariable Long idUser) {
        boolean removido = service.deletarUser(idUser);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
