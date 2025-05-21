package com.example.demo.business.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Objeto para criação e atualização de Tarefa")
public record TarefaRequestDTO(
        @Schema(description = "Título da tarefa", example = "Implementar autenticação", required = true)
        @NotNull
        @NotBlank
        String titulo,

        @Schema(description = "Descrição detalhada da tarefa", example = "A tarefa consiste em configurar o Spring Security")
        @NotNull
        @NotBlank
        String descricao,

        @Schema(description = "ID do projeto relacionado", example = "1")
        @NotNull
        Long projetoId,

        @Schema(description = "Status de conclusão da tarefa", example = "false")
        Boolean concluida
) {}