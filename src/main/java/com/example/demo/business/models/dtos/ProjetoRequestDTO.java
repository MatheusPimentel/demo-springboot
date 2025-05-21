package com.example.demo.business.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Objeto para criação e atualização de Projeto")
public record ProjetoRequestDTO(
        @Schema(description = "Nome do projeto", example = "Sistema de Gestão Escolar")
        @NotNull
        @NotBlank
        String nome,

        @Schema(description = "Descrição do projeto", example = "Plataforma para gerenciar alunos e professores")
        @NotNull
        @NotBlank
        String descricao
) {}