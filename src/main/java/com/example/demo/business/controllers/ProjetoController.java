package com.example.demo.business.controllers;

import com.example.demo.business.models.dtos.ProjetoRequestDTO;
import com.example.demo.business.models.dtos.ProjetoResponseDTO;
import com.example.demo.business.services.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Projetos", description = "Gerenciamento de projetos")
@RequestMapping("/projetos")
public class ProjetoController {
    private final ProjetoService projetoService;

    @PostMapping
    @Operation(summary = "Cria um novo projeto", description = "Cria e persiste um novo projeto no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projeto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ProjetoResponseDTO> save(@Valid @RequestBody ProjetoRequestDTO projetoDto) {
        return ResponseEntity.ok(projetoService.saveProjeto(projetoDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um projeto existente", description = "Atualiza os dados de um projeto com base no ID.")
    public ResponseEntity<ProjetoResponseDTO> update(@PathVariable("id") Long id,
                                                          @Valid @RequestBody ProjetoRequestDTO projetoDto) {
        return ResponseEntity.ok(projetoService.updateProjeto(id, projetoDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista todos os projetos", description = "Retorna a lista de todos os projetos cadastrados.")
    public ResponseEntity<ProjetoResponseDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(projetoService.findProjetoById(id));
    }

    @GetMapping
    @Operation(summary = "Lista todos os projetos", description = "Retorna a lista de todos os projetos cadastrados.")
    public ResponseEntity<List<ProjetoResponseDTO>> findAll() {
        return ResponseEntity.ok(projetoService.findProjetos());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um projeto", description = "Remove um projeto com base no ID.")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        projetoService.deleteProjeto(id);
        return ResponseEntity.noContent().build();
    }
}
