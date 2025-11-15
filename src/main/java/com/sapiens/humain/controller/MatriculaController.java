package com.sapiens.humain.controller;

import com.sapiens.humain.model.Matricula;
import com.sapiens.humain.service.MatriculaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/humain/usuario/{idUser}/matriculas")
public class MatriculaController {
    private final MatriculaService service;
    public MatriculaController(MatriculaService service) {
        this.service = service;
    }

    @PostMapping("/{idTrilha}")
    public ResponseEntity<Matricula> matricularTrilha(@PathVariable Long idTrilha, @PathVariable Long idUser) {
        Matricula salvo =  service.matricularTrilha(idTrilha, idUser);
        return ResponseEntity.created(URI.create("/humain/usuario/{idUser}/matriculas" + salvo.getId_trilha())).body(salvo);
    }

    @DeleteMapping("/{idTrilha}")
    public ResponseEntity<Matricula> completarTrilha(@PathVariable Long idTrilha, @PathVariable Long idUser) {
        boolean concluido = service.completarTrilha(idTrilha, idUser);
        return concluido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
