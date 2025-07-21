package com.example.demo.core.service;
import com.example.demo.core.mapper.GenericMapper;
import com.example.demo.exceptions.domain.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

public abstract class AbstractCrudService<
        E,
        ID extends Serializable,
        Req,
        Res
        > {

    protected abstract JpaRepository<E, ID> getRepository();
    protected abstract GenericMapper<E, Req, Res> getMapper();

    public Page<Res> findAll(Pageable pageable) {
        return getRepository().findAll(pageable).map(getMapper()::toDto);
    }

    public Res findById(ID id) {
        return getMapper().toDto(getById(id));
    }

    @Transactional
    public Res save(Req requestDto) {
        E entity = getMapper().toEntity(requestDto);
        E savedEntity = getRepository().save(entity);
        return getMapper().toDto(savedEntity);
    }

    @Transactional
    public Res update(ID id, Req requestDto) {
        E entity = getById(id);
        getMapper().updateFromDto(requestDto, entity);
        E updatedEntity = getRepository().save(entity);
        return getMapper().toDto(updatedEntity);
    }

    @Transactional
    public void delete(ID id) {
        getById(id);
        getRepository().deleteById(id);
    }

    protected E getById(ID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso com ID " + id + " não encontrado."));
    }
}