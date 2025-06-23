package com.example.demo.business.services;

import com.example.demo.business.mappers.TarefaMapper;
import com.example.demo.business.models.Tarefa;
import com.example.demo.business.models.dtos.TarefaRequestDTO;
import com.example.demo.business.models.dtos.TarefaResponseDTO;
import com.example.demo.business.repositories.ProjetoRepository;
import com.example.demo.business.repositories.TarefaRepository;
import com.example.demo.exceptions.domain.ResourceNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final ProjetoRepository projetoRepository;
    private final TarefaMapper tarefaMapper;

    @Transactional
    public TarefaResponseDTO saveTarefa(@NonNull TarefaRequestDTO tarefaDto) {
        Tarefa tarefa = tarefaMapper.toEntity(tarefaDto);
        this.loadDependencies(tarefa, tarefaDto);
        return tarefaMapper.toDto(tarefaRepository.save(tarefa));
    }

    @Transactional
    public TarefaResponseDTO updateTarefa(@NonNull Long id, @NonNull TarefaRequestDTO tarefaDto) {
        Tarefa tarefa = this.getTarefaById(id);
        tarefaMapper.updateTarefaFromRequestDto(tarefaDto, tarefa);
        this.loadDependencies(tarefa, tarefaDto);
        return tarefaMapper.toDto(tarefaRepository.save(tarefa));
    }

    public TarefaResponseDTO findTarefaById(@NonNull Long id) {
        return tarefaMapper.toDto(this.getTarefaById(id));
    }

    public Page<TarefaResponseDTO> findTarefas(Pageable pageable) {
        return tarefaRepository.findAll(pageable).map(tarefaMapper::toDto);
    }

    @Transactional
    public void deleteTarefa(@NonNull Long id) {
        getTarefaById(id); // Verifica se a tarefa existe, lança ResourceNotFoundException se não existir
        tarefaRepository.deleteById(id);
    }

    public Tarefa getTarefaById(@NonNull Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa com ID " + id + " não encontrada"));
    }

    private void loadDependencies(@NonNull Tarefa tarefa, TarefaRequestDTO tarefaDto) {
        if (tarefaDto.projetoId() != null) {
            projetoRepository.findById(tarefaDto.projetoId()).ifPresentOrElse(
                    tarefa::setProjeto,
                    () -> {
                        throw new ResourceNotFoundException("Projeto com ID " + tarefaDto.projetoId() + " não encontrado.");
                    }
            );
        }
    }
}
