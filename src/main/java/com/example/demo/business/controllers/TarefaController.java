package com.example.demo.business.controllers;

import com.example.demo.business.models.Tarefa;
import com.example.demo.business.models.dtos.TarefaRequestDTO;
import com.example.demo.business.models.dtos.TarefaResponseDTO;
import com.example.demo.business.services.TarefaService;
import com.example.demo.core.controller.AbstractCrudController;
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
@Tag(name = "Tarefas", description = "Gerenciamento de tarefas do projeto")
@RequestMapping("/tarefas")
public class TarefaController extends AbstractCrudController<Tarefa, Long, TarefaRequestDTO, TarefaResponseDTO> {
    public TarefaController(TarefaService tarefaService) {
        super(tarefaService);
    }
}
