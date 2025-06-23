package com.example.demo.business.mappers;

import com.example.demo.business.models.Projeto;
import com.example.demo.business.models.dtos.ProjetoRequestDTO;
import com.example.demo.business.models.dtos.ProjetoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { TarefaMapper.class })
public interface ProjetoMapper {
    ProjetoMapper INSTANCE = Mappers.getMapper(ProjetoMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "tarefas", source = "tarefas")
    ProjetoResponseDTO toDto(Projeto projeto);

    @Mapping(target = "id", ignore = true)
    void updateProjetoFromRequestDto(ProjetoRequestDTO dto, @MappingTarget Projeto entity);
}