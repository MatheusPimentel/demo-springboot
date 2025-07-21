package com.example.demo.core.mapper;
import org.mapstruct.MappingTarget;

public interface GenericMapper<E, Req, Res> {

    Res toDto(E entity);

    E toEntity(Req requestDto);

    void updateFromDto(Req requestDto, @MappingTarget E entity);
}