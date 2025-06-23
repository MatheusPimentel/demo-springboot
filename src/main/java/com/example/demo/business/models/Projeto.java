package com.example.demo.business.models;

import com.example.demo.business.models.dtos.ProjetoRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarefa> tarefas = new ArrayList<>();

    public Projeto(ProjetoRequestDTO projetoRequestDTO) {
        this.nome = projetoRequestDTO.nome();
        this.descricao = projetoRequestDTO.descricao();
    }
}