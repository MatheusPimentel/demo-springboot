package com.example.demo.business.models;

import com.example.demo.business.models.dtos.TarefaRequestDTO;
import com.example.demo.business.models.dtos.TarefaResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        this.buildTarefaDto(tarefaRequestDTO);
    }

    public void buildTarefaDto(TarefaRequestDTO tarefaRequestDTO) {
        this.titulo = tarefaRequestDTO.titulo();
        this.descricao = tarefaRequestDTO.descricao();
        this.concluida = tarefaRequestDTO.concluida();
    }

    public TarefaResponseDTO getTarefaResponseDTO() {
        return new TarefaResponseDTO(id, titulo, descricao, concluida);
    }
}
