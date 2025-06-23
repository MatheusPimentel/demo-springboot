package com.example.demo.business.controllers;

import com.example.demo.business.models.dtos.TarefaRequestDTO;
import com.example.demo.business.models.dtos.TarefaResponseDTO;
import com.example.demo.business.services.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Gerenciamento de tarefas do projeto")
@RequestMapping("/tarefas")
public class TarefaController {
    private final TarefaService tarefaService;

    @PostMapping
    @Operation(summary = "Cria uma nova tarefa", description = "Cria uma nova tarefa associada a um projeto existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    public ResponseEntity<TarefaResponseDTO> save(@Valid @RequestBody TarefaRequestDTO tarefaDto) {
        TarefaResponseDTO savedTarefa = tarefaService.saveTarefa(tarefaDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedTarefa.id()).toUri();
        return ResponseEntity.created(location).body(savedTarefa);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma tarefa existente", description = "Atualiza os dados de uma tarefa existente pelo ID.")
    public ResponseEntity<TarefaResponseDTO> update(@PathVariable("id") Long id,
                                                          @Valid @RequestBody TarefaRequestDTO tarefaDto) {
        return ResponseEntity.ok(tarefaService.updateTarefa(id, tarefaDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma tarefa por ID", description = "Retorna os dados da tarefa correspondente ao ID.")
    public ResponseEntity<TarefaResponseDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tarefaService.findTarefaById(id));
    }

    @GetMapping
    @Operation(summary = "Busca de todas as tarefas", description = "Retorna todas as tarefas do banco.")
    public ResponseEntity<Page<TarefaResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(tarefaService.findTarefas(pageable));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma tarefa", description = "Remove uma tarefa existente com base no ID.")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        tarefaService.deleteTarefa(id);
        return ResponseEntity.noContent().build();
    }
}
