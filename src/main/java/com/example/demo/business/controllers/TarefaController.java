package com.example.demo.business.controllers;

import com.example.demo.business.models.Tarefa;
import com.example.demo.business.models.dtos.TarefaRequestDTO;
import com.example.demo.business.models.dtos.TarefaResponseDTO;
import com.example.demo.business.services.TarefaService;
import com.example.demo.core.controller.AbstractCrudController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Tarefas", description = "Gerenciamento de tarefas do projeto")
@RequestMapping("/tarefas")
public class TarefaController extends AbstractCrudController<Tarefa, Long, TarefaRequestDTO, TarefaResponseDTO> {
    public TarefaController(TarefaService tarefaService) {
        super(tarefaService);
    }
}
