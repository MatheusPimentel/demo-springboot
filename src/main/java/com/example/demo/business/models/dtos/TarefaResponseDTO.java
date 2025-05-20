package com.example.demo.business.models.dtos;

public record TarefaResponseDTO(
        Long id,
        String titulo,
        String descricao,
        boolean concluida
) {}