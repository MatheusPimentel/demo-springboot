package com.example.demo.business.services;

import com.example.demo.business.mappers.TarefaMapper;
import com.example.demo.business.models.Tarefa;
import com.example.demo.business.models.dtos.TarefaRequestDTO;
import com.example.demo.business.models.dtos.TarefaResponseDTO;
import com.example.demo.business.repositories.ProjetoRepository;
import com.example.demo.business.repositories.TarefaRepository;
import com.example.demo.core.mapper.GenericMapper;
import com.example.demo.core.service.AbstractCrudService;
import com.example.demo.exceptions.domain.ResourceNotFoundException;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TarefaService extends AbstractCrudService<Tarefa, Long, TarefaRequestDTO, TarefaResponseDTO> {
    private final TarefaRepository tarefaRepository;
    private final ProjetoRepository projetoRepository;
    private final TarefaMapper tarefaMapper;

    public TarefaService(TarefaRepository tarefaRepository, ProjetoRepository projetoRepository, TarefaMapper tarefaMapper) {
        this.tarefaRepository = tarefaRepository;
        this.projetoRepository = projetoRepository;
        this.tarefaMapper = tarefaMapper;
    }

    @Override
    protected JpaRepository<Tarefa, Long> getRepository() {
        return tarefaRepository;
    }

    @Override
    protected GenericMapper<Tarefa, TarefaRequestDTO, TarefaResponseDTO> getMapper() {
        return tarefaMapper;
    }

    @Override
    @Transactional
    public TarefaResponseDTO save(TarefaRequestDTO tarefaDto) {
        Tarefa tarefa = getMapper().toEntity(tarefaDto);
        this.loadDependencies(tarefa, tarefaDto);
        return getMapper().toDto(getRepository().save(tarefa));
    }

    @Override
    @Transactional
    public TarefaResponseDTO update(Long id, TarefaRequestDTO tarefaDto) {
        Tarefa tarefa = getById(id);
        getMapper().updateFromDto(tarefaDto, tarefa);
        this.loadDependencies(tarefa, tarefaDto);
        return getMapper().toDto(getRepository().save(tarefa));
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
