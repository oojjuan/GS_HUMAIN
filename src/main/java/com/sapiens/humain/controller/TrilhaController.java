package com.sapiens.humain.controller;

import com.sapiens.humain.model.Trilha;
import com.sapiens.humain.service.TrilhaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/humain/trilha")
public class TrilhaController {
    private final TrilhaService service;
    public TrilhaController(TrilhaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Trilha> listarTrilhas() {
        return service.listarTrilhas();
    }

    @PostMapping
    public ResponseEntity<Trilha> inserirTrilha(@RequestBody Trilha dados) {
        Trilha salvo = service.adicionarTrilha(dados);
        return ResponseEntity.created(URI.create("/humain/trilha" + salvo.getId())).body(salvo);
    }

    @GetMapping("/{idTrilha}")
    public ResponseEntity<Trilha> buscarTrilha(@PathVariable Long idTrilha) {
        return service.buscarTrilha(idTrilha)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{idTrilha}")
    public ResponseEntity<Trilha> atualizarTrilha(@PathVariable Long idTrilha, @RequestBody Trilha dados) {
        return service.atualizarTrilha(idTrilha, dados)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idTrilha}")
    public ResponseEntity<Trilha> deletarTrilha(@PathVariable Long idTrilha) {
        boolean removido = service.deletarTrilha(idTrilha);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


}
