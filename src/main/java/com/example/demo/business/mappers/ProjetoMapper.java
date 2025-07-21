package com.example.demo.business.mappers;

import com.example.demo.business.models.Projeto;
import com.example.demo.business.models.dtos.ProjetoRequestDTO;
import com.example.demo.business.models.dtos.ProjetoResponseDTO;
import com.example.demo.core.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { TarefaMapper.class })
public interface ProjetoMapper extends GenericMapper<Projeto, ProjetoRequestDTO, ProjetoResponseDTO> {

    ProjetoResponseDTO toDto(Projeto projeto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tarefas", ignore = true)
    Projeto toEntity(ProjetoRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateProjetoFromRequestDto(ProjetoRequestDTO dto, @MappingTarget Projeto entity);
}