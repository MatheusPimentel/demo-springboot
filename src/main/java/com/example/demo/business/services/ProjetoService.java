package com.example.demo.business.services;

import com.example.demo.business.mappers.ProjetoMapper;
import com.example.demo.business.models.Projeto;
import com.example.demo.business.models.dtos.ProjetoRequestDTO;
import com.example.demo.business.models.dtos.ProjetoResponseDTO;
import com.example.demo.business.repositories.ProjetoRepository;
import com.example.demo.business.repositories.TarefaRepository;
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
public class ProjetoService {
    private final ProjetoRepository projetoRepository;
    private final TarefaRepository tarefaRepository; // Mantido para futuras implementações ou se houver lógica em outro lugar
    private final ProjetoMapper projetoMapper;

    @Transactional
    public ProjetoResponseDTO saveProjeto(@NonNull ProjetoRequestDTO projetoDto) {
        Projeto projeto = new Projeto(projetoDto);
        return projetoMapper.toDto(projetoRepository.save(projeto));
    }

    @Transactional
    public ProjetoResponseDTO updateProjeto(@NonNull Long id, @NonNull ProjetoRequestDTO projetoDto) {
        Projeto projeto = this.getProjetoById(id);
        projetoMapper.updateProjetoFromRequestDto(projetoDto, projeto);
        return projetoMapper.toDto(projetoRepository.save(projeto));
    }

    public ProjetoResponseDTO findProjetoById(@NonNull Long id) {
        return projetoMapper.toDto(this.getProjetoById(id));
    }

    public Page<ProjetoResponseDTO> findProjetos(Pageable pageable) {
        return projetoRepository.findAll(pageable).map(projetoMapper::toDto);
    }

    @Transactional
    public void deleteProjeto(@NonNull Long id) {
        if (!projetoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado");
        }
        projetoRepository.deleteById(id);
    }

    private Projeto getProjetoById(@NonNull Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado"));
    }
}