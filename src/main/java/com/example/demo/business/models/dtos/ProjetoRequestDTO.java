package com.example.demo.business.models.dtos;

import jakarta.validation.constraints.NotNull;

public record ProjetoRequestDTO(
        @NotNull
        String nome,
        @NotNull
        String descricao
) {}