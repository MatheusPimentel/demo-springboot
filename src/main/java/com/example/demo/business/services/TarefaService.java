package com.example.demo.business.services;

import com.example.demo.business.models.Tarefa;
import com.example.demo.business.models.dtos.TarefaRequestDTO;
import com.example.demo.business.models.dtos.TarefaResponseDTO;
import com.example.demo.business.repositories.ProjetoRepository;
import com.example.demo.business.repositories.TarefaRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final ProjetoRepository projetoRepository;

    public TarefaResponseDTO saveTarefa(@NonNull TarefaRequestDTO tarefaDto) {
        Tarefa tarefa = new Tarefa(tarefaDto);
        this.loadDependencies(tarefa, tarefaDto);
        return tarefaRepository.save(tarefa).getTarefaResponseDTO();
    }

    public TarefaResponseDTO updateTarefa(@NonNull Long id, @NonNull TarefaRequestDTO tarefaDto) {
        Tarefa tarefa = this.getTarefaById(id);
        tarefa.buildTarefaDto(tarefaDto);
        this.loadDependencies(tarefa, tarefaDto);
        return tarefaRepository.save(tarefa).getTarefaResponseDTO();
    }

    public TarefaResponseDTO findTarefaById(@NonNull Long id) {
        return this.getTarefaById(id).getTarefaResponseDTO();
    }

    public List<TarefaResponseDTO> findTarefas() {
        return tarefaRepository.findAll().stream().map(Tarefa::getTarefaResponseDTO).toList();
    }

    public void deleteTarefa(@NonNull Long id) {
        if (!tarefaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada");
        }
        tarefaRepository.deleteById(id);
    }

    public Tarefa getTarefaById(@NonNull Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));
    }

    private void loadDependencies(@NonNull Tarefa tarefa, TarefaRequestDTO tarefaDto) {
        if (tarefaDto.projetoId() != null) {
            projetoRepository.findById(tarefaDto.projetoId()).ifPresentOrElse(
                    tarefa::setProjeto,
                    () -> {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado");
                    }
            );
        }
    }
}
