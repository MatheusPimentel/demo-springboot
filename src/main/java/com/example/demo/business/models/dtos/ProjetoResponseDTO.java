package com.example.demo.business.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Objeto de resposta com os dados do Projeto")
public record ProjetoResponseDTO(
        @Schema(description = "ID do projeto", example = "1")
        Long id,

        @Schema(description = "Nome do projeto", example = "Sistema de Gestão Escolar")
        String nome,

        @Schema(description = "Descrição do projeto", example = "Plataforma para gerenciar alunos e professores")
        String descricao,

        @Schema(description = "Tarefas do Projeto")
        List<TarefaResponseDTO> tarefas
) {}