package com.example.demo.core.controller;
import com.example.demo.core.service.AbstractCrudService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.Serializable;
import java.net.URI;

public abstract class AbstractCrudController<
        E,
        ID extends Serializable,
        Req,
        Res extends Record
        > {

    private final AbstractCrudService<E, ID, Req, Res> service;

    protected AbstractCrudController(AbstractCrudService<E, ID, Req, Res> service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<Res>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Res> findById(@PathVariable ID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Res> save(@Valid @RequestBody Req requestDto) {
        Res responseDto = service.save(requestDto);

        Object newId;
        try {
            newId = responseDto.getClass().getMethod("id").invoke(responseDto);
        } catch (Exception e) {
            throw new RuntimeException("O DTO de Resposta deve conter um método 'id()'", e);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();
        return ResponseEntity.created(location).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Res> update(@PathVariable ID id, @Valid @RequestBody Req requestDto) {
        return ResponseEntity.ok(service.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}