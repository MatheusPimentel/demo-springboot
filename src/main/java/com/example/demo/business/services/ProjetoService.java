package com.example.demo.business.services;

import com.example.demo.business.mappers.ProjetoMapper;
import com.example.demo.business.models.Projeto;
import com.example.demo.business.models.dtos.ProjetoRequestDTO;
import com.example.demo.business.models.dtos.ProjetoResponseDTO;
import com.example.demo.business.repositories.ProjetoRepository;
import com.example.demo.core.mapper.GenericMapper;
import com.example.demo.core.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjetoService extends AbstractCrudService<Projeto, Long, ProjetoRequestDTO, ProjetoResponseDTO> {
    private final ProjetoRepository projetoRepository;
    private final ProjetoMapper projetoMapper;

    @Override
    protected JpaRepository<Projeto, Long> getRepository() {
        return projetoRepository;
    }

    @Override
    protected GenericMapper<Projeto, ProjetoRequestDTO, ProjetoResponseDTO> getMapper() {
        return projetoMapper;
    }
}