package com.sapiens.humain.controller;

import com.sapiens.humain.model.Competencias;
import com.sapiens.humain.service.CompetenciasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/humain/competencias")
public class CompetenciasController {

    private final CompetenciasService service;
    public CompetenciasController(CompetenciasService service) {
        this.service = service;
    }

    @GetMapping
    public List<Competencias> listarCompetencias() {
        return service.getCompetencias();
    }

    @PostMapping
    public ResponseEntity<Competencias> adicionarCompetencia(@RequestBody Competencias competencias) {
        Competencias salvo = service.adicionarCompetencia(competencias);
        return ResponseEntity.created(URI.create("/humain/competencias" + salvo.getId())).body(salvo);
    }

    @PutMapping("/{idCompetencias}")
    public ResponseEntity<Competencias> atualizarCompetencia(@PathVariable Long idCompetencias, @RequestBody Competencias dados) {
        return service.atualizarCompetencia(idCompetencias, dados)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{idCompetencias}")
    public ResponseEntity<Competencias> buscarCompetencia(@PathVariable Long idCompetencias) {
        return service.buscarCompetenciaPeloId(idCompetencias)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idCompetencias}")
    public ResponseEntity<Competencias> excluirCompetencia(@PathVariable Long idCompetencias)    {
        boolean removido = service.excluirCompetencia(idCompetencias);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }


}
