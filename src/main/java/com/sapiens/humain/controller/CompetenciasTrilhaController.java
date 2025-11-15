package com.sapiens.humain.controller;

import com.sapiens.humain.model.CompetenciasTrilha;
import com.sapiens.humain.service.CompetenciasTrilhaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/humain/trilha/{idTrilha}/competencias")
public class CompetenciasTrilhaController {
    private final CompetenciasTrilhaService service;
    public CompetenciasTrilhaController(CompetenciasTrilhaService service) {
        this.service = service;
    }

    @GetMapping
    public List<CompetenciasTrilha> listarCompetenciasTrilha(@PathVariable Long idTrilha) {
        return service.listarCompetenciasTrilhas(idTrilha);
    }

    @PostMapping
    public ResponseEntity<CompetenciasTrilha> adicionarCompetenciasNaTrilha(@PathVariable Long idTrilha, @RequestBody Long idCompetencia) {
        CompetenciasTrilha salvo = service.adicionarCompetenciaNaTrilha(idTrilha, idCompetencia);
        return ResponseEntity.created((URI.create("/humain/trilha/{idTrilha}/competencias" + salvo.getId_competencia()))).body(salvo);
    }

    @GetMapping("/{idCompetencia}")
    public ResponseEntity<CompetenciasTrilha> buscarCompetenciasPorTrilha(@PathVariable Long idCompetencia) {
        return service.buscarCompetenciaPorTrilha(idCompetencia)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idCompetencia}")
    public ResponseEntity<CompetenciasTrilha> excluirCompetenciasPorTrilha(@PathVariable Long idCompetencia,  @PathVariable Long idTrilha) {
        boolean removido = service.excluirCompetenciaPorTrilha(idTrilha, idCompetencia);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
