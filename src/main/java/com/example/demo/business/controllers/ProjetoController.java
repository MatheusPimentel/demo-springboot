package com.example.demo.business.controllers;

import com.example.demo.business.models.dtos.ProjetoRequestDTO;
import com.example.demo.business.models.dtos.ProjetoResponseDTO;
import com.example.demo.business.services.ProjetoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projetos")
public class ProjetoController {
    private final ProjetoService projetoService;

    @PostMapping
    public ResponseEntity<ProjetoResponseDTO> save(@Valid @RequestBody ProjetoRequestDTO projetoDto) {
        return ResponseEntity.ok(projetoService.saveProjeto(projetoDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> update(@PathVariable("id") Long id,
                                                          @Valid @RequestBody ProjetoRequestDTO projetoDto) {
        return ResponseEntity.ok(projetoService.updateProjeto(id, projetoDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(projetoService.findProjetoById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProjetoResponseDTO>> findAll() {
        return ResponseEntity.ok(projetoService.findProjetos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        projetoService.deleteProjeto(id);
        return ResponseEntity.noContent().build();
    }
}
