package com.example.demo.business.mappers;

import com.example.demo.business.models.Tarefa;
import com.example.demo.business.models.dtos.TarefaRequestDTO;
import com.example.demo.business.models.dtos.TarefaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TarefaMapper {
    TarefaMapper INSTANCE = Mappers.getMapper(TarefaMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "titulo", source = "titulo")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "concluida", source = "concluida")
    TarefaResponseDTO toDto(Tarefa tarefa);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "projetoId", ignore = true)
    void updateTarefaFromRequestDto(TarefaRequestDTO dto, @MappingTarget Tarefa entity);
}