package com.example.demo.business.services;

import com.example.demo.business.models.Tarefa;
import com.example.demo.business.repositories.TarefaRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {
    private final TarefaRepository tarefaRepository;

    public ResponseEntity<Tarefa> saveTarefa(@NonNull Tarefa tarefa) {
        return ResponseEntity.ok(tarefaRepository.save(tarefa));
    }

    public ResponseEntity<Tarefa> findTarefaById(@NonNull Long id) {
        return tarefaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<Tarefa>> getTarefas() {
        return ResponseEntity.ok(tarefaRepository.findAll());
    }

    public ResponseEntity deleteTarefa(@NonNull Long id) {
        if (!tarefaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tarefaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
