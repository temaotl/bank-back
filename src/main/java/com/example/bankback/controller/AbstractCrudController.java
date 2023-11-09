package com.example.bankback.controller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 *
 * @param <E> entity
 * @param <WD> write DTO
 * @param <RD> read DTO
 * @param <ID> id type
 * @param <R> repository
 */
public abstract class AbstractCrudController<E, WD,RD, ID, R extends CrudRepository<E, ID>> {

    protected final R repository;
    protected final Function<E, WD> toDtoConverter;
    protected final Function<WD, E> toEntityConverter;

    protected final Function<E,RD> toReadDtoConverter;

    protected AbstractCrudController(R repository,
                                     Function<E, WD> toDtoConverter,
                                     Function<E,RD> toReadDtoConverter,
                                     Function<WD, E> toEntityConverter) {
        this.repository = repository;
        this.toDtoConverter = toDtoConverter;
        this.toEntityConverter = toEntityConverter;
        this.toReadDtoConverter = toReadDtoConverter;
    }

    @GetMapping
    public List<RD> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(toReadDtoConverter)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RD> getOne(@PathVariable ID id) {
        return repository.findById(id)
                .map(toReadDtoConverter)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public abstract ResponseEntity<WD> create(@RequestBody WD dto);

    @PutMapping("/{id}")
    public abstract ResponseEntity<WD> update(@RequestBody WD dto, @PathVariable ID id);

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

