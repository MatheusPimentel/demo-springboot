package com.example.demo.business.controllers;

import com.example.demo.business.models.Projeto;
import com.example.demo.business.models.dtos.ProjetoRequestDTO;
import com.example.demo.business.models.dtos.ProjetoResponseDTO;
import com.example.demo.business.services.ProjetoService;
import com.example.demo.core.controller.AbstractCrudController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Projetos", description = "Gerenciamento de projetos")
@RequestMapping("/projetos")
public class ProjetoController extends AbstractCrudController<Projeto, Long, ProjetoRequestDTO, ProjetoResponseDTO> {

    public ProjetoController(ProjetoService projetoService) {
        super(projetoService);
    }
}