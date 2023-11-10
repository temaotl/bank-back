package com.example.bankback.business;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.StreamSupport;


@Transactional(readOnly = true)
public abstract class AbstractCrudService<D, K, E, R extends CrudRepository<E, K>> {

    protected final R repository;
    protected final Function<D, E> toEntityConverter;
    protected final Function<E, D> toDtoConverter;

    protected AbstractCrudService(R repository, Function<D, E> toEntityConverter, Function<E, D> toDtoConverter) {
        this.repository = repository;
        this.toEntityConverter = toEntityConverter;
        this.toDtoConverter = toDtoConverter;
    }

    @Transactional
    public D create(D dto) {
        E entity = toEntityConverter.apply(dto);
        E savedEntity = repository.save(entity);
        return toDtoConverter.apply(savedEntity);
    }

    public Optional<D> readById(K id) {
        return repository.findById(id).map(toDtoConverter);
    }

    public List<D> readAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(toDtoConverter)
                .toList();
    }

    @Transactional
    public abstract void update(D dto, K id);

    @Transactional
    public void deleteById(K id) {
        repository.deleteById(id);
    }
}

