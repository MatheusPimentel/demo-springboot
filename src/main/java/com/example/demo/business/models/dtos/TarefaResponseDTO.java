package com.example.demo.business.models.dtos;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto de resposta com os dados da Tarefa")
public record TarefaResponseDTO(
        @Schema(description = "ID da tarefa", example = "10")
        Long id,

        @Schema(description = "Título da tarefa", example = "Implementar tela de login")
        String titulo,

        @Schema(description = "Descrição da tarefa", example = "Criar uma interface responsiva de login usando Vue.js")
        String descricao,

        @Schema(description = "Tarefa concluída", example = "false")
        boolean concluida
) {}