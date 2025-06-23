package com.example.demo.business.models;

import com.example.demo.business.models.dtos.TarefaRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;

    private boolean concluida = false;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;

    public Tarefa(TarefaRequestDTO tarefaRequestDTO) {
        this.titulo = tarefaRequestDTO.titulo();
        this.descricao = tarefaRequestDTO.descricao();
        this.concluida = Objects.requireNonNullElse(tarefaRequestDTO.concluida(), false);
    }
}