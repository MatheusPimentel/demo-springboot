package com.example.demo.business.services;

import com.example.demo.business.mappers.ProjetoMapper;
import com.example.demo.business.models.Projeto;
import com.example.demo.business.models.dtos.ProjetoRequestDTO;
import com.example.demo.business.models.dtos.ProjetoResponseDTO;
import com.example.demo.business.repositories.ProjetoRepository;
import com.example.demo.core.mapper.GenericMapper;
import com.example.demo.core.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    @Cacheable("projetos")
    public Page<ProjetoResponseDTO> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    @Override
    @CacheEvict(value = "projetos", allEntries = true)
    public ProjetoResponseDTO save(ProjetoRequestDTO requestDto) {
        return super.save(requestDto);
    }

    @Override
    @CacheEvict(value = "projetos", allEntries = true)
    public ProjetoResponseDTO update(Long aLong, ProjetoRequestDTO requestDto) {
        return super.update(aLong, requestDto);
    }

    @Override
    @CacheEvict(value = "projetos", allEntries = true)
    public void delete(Long aLong) {
        super.delete(aLong);
    }
}