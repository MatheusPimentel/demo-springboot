package com.example.demo.business.controllers;

import com.example.demo.business.models.Tarefa;
import com.example.demo.business.services.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefaController {
    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<Tarefa> saveTarefa(@RequestBody Tarefa tarefa) {
        return tarefaService.saveTarefa(tarefa);
    }

    @PutMapping
    public ResponseEntity<Tarefa> updateTarefa(@RequestBody Tarefa tarefa) {
        return tarefaService.saveTarefa(tarefa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> getTarefaById(@PathVariable("id") Long id) {
        return tarefaService.findTarefaById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefaById(@PathVariable("id") Long id) {
        return tarefaService.deleteTarefa(id);
    }
}
