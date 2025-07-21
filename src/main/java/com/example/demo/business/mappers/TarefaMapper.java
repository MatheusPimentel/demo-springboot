package com.example.demo.business.mappers;

import com.example.demo.business.models.Tarefa;
import com.example.demo.business.models.dtos.TarefaRequestDTO;
import com.example.demo.business.models.dtos.TarefaResponseDTO;
import com.example.demo.core.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TarefaMapper extends GenericMapper<Tarefa, TarefaRequestDTO, TarefaResponseDTO> {

    TarefaResponseDTO toDto(Tarefa tarefa);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", ignore = true)
    Tarefa toEntity(TarefaRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projeto", ignore = true)
    void updateTarefaFromRequestDto(TarefaRequestDTO dto, @MappingTarget Tarefa entity);
}