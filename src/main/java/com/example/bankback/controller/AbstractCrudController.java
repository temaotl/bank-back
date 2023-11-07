package com.example.bankback.controller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class AbstractCrudController<E, D, ID, R extends CrudRepository<E, ID>> {

    protected final R repository;
    protected final Function<E, D> toDtoConverter;
    protected final Function<D, E> toEntityConverter;

    protected AbstractCrudController(R repository, Function<E, D> toDtoConverter, Function<D, E> toEntityConverter) {
        this.repository = repository;
        this.toDtoConverter = toDtoConverter;
        this.toEntityConverter = toEntityConverter;
    }

    @GetMapping
    public List<D> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(toDtoConverter)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> getOne(@PathVariable ID id) {
        return repository.findById(id)
                .map(toDtoConverter)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public abstract ResponseEntity<D> create(@RequestBody D dto);

    @PutMapping("/{id}")
    public abstract ResponseEntity<D> update(@RequestBody D dto, @PathVariable ID id);

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

