package com.example.demo.business.services;

import com.example.demo.business.models.Projeto;
import com.example.demo.business.models.dtos.ProjetoRequestDTO;
import com.example.demo.business.models.dtos.ProjetoResponseDTO;
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
public class ProjetoService {
    private final ProjetoRepository projetoRepository;
    private final TarefaRepository tarefaRepository;

    public ProjetoResponseDTO saveProjeto(@NonNull ProjetoRequestDTO projetoDto) {
        Projeto projeto = new Projeto(projetoDto);
        return projetoRepository.save(projeto).getProjetoResponseDto();
    }

    public ProjetoResponseDTO updateProjeto(@NonNull Long id, @NonNull ProjetoRequestDTO projetoDto) {
        Projeto projeto = this.getProjetoById(id);
        projeto.buildProjetoDto(projetoDto);
        return projetoRepository.save(projeto).getProjetoResponseDto();
    }

    public ProjetoResponseDTO findProjetoById(@NonNull Long id) {
        return this.getProjetoById(id).getProjetoResponseDto();
    }

    public List<ProjetoResponseDTO> findProjetos() {
        return projetoRepository.findAll().stream().map(Projeto::getProjetoResponseDto).toList();
    }

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

    private void loadDependencies(@NonNull Projeto projeto) {
        if (projeto.getId() != null) projeto.setTarefas(tarefaRepository.findAllByProjetoId(projeto.getId()));
    }
}
