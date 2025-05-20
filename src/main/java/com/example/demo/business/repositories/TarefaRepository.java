package com.example.demo.business.repositories;

import com.example.demo.business.models.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query("SELECT t FROM Tarefa t WHERE t.projeto.id = ?1")
    public List<Tarefa> findAllByProjetoId(Long id);
}
