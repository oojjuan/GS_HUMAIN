package com.sapiens.humain.controller;

import com.sapiens.humain.model.FuncionariosEmpresa;
import com.sapiens.humain.service.FuncionariosEmpresasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/humain/empresa/{idEmpresa}/funcionarios")
public class FuncionariosEmpresaController {
    private final FuncionariosEmpresasService service;
    public FuncionariosEmpresaController(FuncionariosEmpresasService service) {
        this.service = service;
    }

    @GetMapping
    public List<FuncionariosEmpresa> listarFuncionariosEmpresas(@PathVariable Long idEmpresa){
        return service.listarFuncionariosEmpresa(idEmpresa);
    }

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<FuncionariosEmpresa> buscarFuncionario(@PathVariable Long idFuncionario, @PathVariable Long idEmpresa){
        return service.buscarFuncionario(idEmpresa, idFuncionario)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FuncionariosEmpresa> adicionarFuncionario(@PathVariable Long idEmpresa , @RequestBody FuncionariosEmpresa novoFuncionario){
        FuncionariosEmpresa salvo = service.cadastrarFuncionariosEmpresa(novoFuncionario);
        return ResponseEntity.created(URI.create("/humain/empresa/{idEmpresa}/funcionarios" + salvo.getId_usuario())).body(salvo);
    }

    @PutMapping("/{idFuncionario}")
    public ResponseEntity<FuncionariosEmpresa> atualizarCargoFuncionario(@PathVariable Long idFuncionario, @PathVariable Long idEmpresa , @RequestBody String novoCargo){
        FuncionariosEmpresa atualizado = new FuncionariosEmpresa(idEmpresa, idFuncionario, novoCargo);
        return service.atualizarCargoFuncionario(atualizado)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idFuncionario}")
    public ResponseEntity<FuncionariosEmpresa> excluirFuncionario(@PathVariable Long idFuncionario, @PathVariable Long idEmpresa){
        boolean removido = service.excluirFuncionario(idEmpresa, idFuncionario);
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
