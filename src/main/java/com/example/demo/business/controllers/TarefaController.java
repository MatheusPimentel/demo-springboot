package com.example.demo.business.controllers;

import com.example.demo.business.models.dtos.TarefaRequestDTO;
import com.example.demo.business.models.dtos.TarefaResponseDTO;
import com.example.demo.business.services.TarefaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tarefas")
public class TarefaController {
    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefaResponseDTO> save(@Valid @RequestBody TarefaRequestDTO tarefaDto) {
        return ResponseEntity.ok(tarefaService.saveTarefa(tarefaDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> update(@PathVariable("id") Long id,
                                                          @Valid @RequestBody TarefaRequestDTO tarefaDto) {
        return ResponseEntity.ok(tarefaService.updateTarefa(id, tarefaDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tarefaService.findTarefaById(id));
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> findAll() {
        return ResponseEntity.ok(tarefaService.findTarefas());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        tarefaService.deleteTarefa(id);
        return ResponseEntity.noContent().build();
    }
}
