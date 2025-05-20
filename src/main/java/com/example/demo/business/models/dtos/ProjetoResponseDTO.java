package com.example.demo.business.models.dtos;

import java.util.List;

public record ProjetoResponseDTO(
        Long id,
        String nome,
        String descricao,
        List<TarefaResponseDTO> tarefas
) {}