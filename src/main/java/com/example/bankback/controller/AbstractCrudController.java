package com.example.bankback.controller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;

/**
 *
 * @param <E> entity
 * @param <W> write DTO
 * @param <R> read DTO
 * @param <I> id type
 * @param <T> repository
 */
public abstract class AbstractCrudController<E, W, R, I, T extends CrudRepository<E, I>> {

    protected final T repository;
    protected final Function<E, W> toDtoConverter;
    protected final Function<W, E> toEntityConverter;

    protected final Function<E, R> toReadDtoConverter;

    protected AbstractCrudController(T repository,
                                     Function<E, W> toDtoConverter,
                                     Function<E, R> toReadDtoConverter,
                                     Function<W, E> toEntityConverter) {
        this.repository = repository;
        this.toDtoConverter = toDtoConverter;
        this.toEntityConverter = toEntityConverter;
        this.toReadDtoConverter = toReadDtoConverter;
    }

    @GetMapping
    public List<R> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(toReadDtoConverter)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<R> getOne(@PathVariable I id) {
        return repository.findById(id)
                .map(toReadDtoConverter)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public abstract ResponseEntity<W> create(@RequestBody W dto);

    @PutMapping("/{id}")
    public abstract ResponseEntity<W> update(@RequestBody W dto, @PathVariable I id);

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable I id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

