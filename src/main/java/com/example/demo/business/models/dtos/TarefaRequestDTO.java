package com.example.demo.business.models.dtos;

import jakarta.validation.constraints.NotNull;

public record TarefaRequestDTO(
        @NotNull
        String titulo,
        @NotNull
        String descricao,
        @NotNull
        Long projetoId,
        Boolean concluida
) {}