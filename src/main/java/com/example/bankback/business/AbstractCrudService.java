package com.example.bankback.business;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional(readOnly = true)
public abstract class AbstractCrudService<D, K, E, R extends CrudRepository<E, K>> {
    protected final R repository;
    protected final Function<E, D> toDtoConverter;

    protected AbstractCrudService(R repository, Function<E, D> toDtoConverter) {
        this.repository = repository;
        this.toDtoConverter = toDtoConverter;
    }

    @Transactional
    public E create(E entity) {
        return repository.save(entity);
    }

    public Optional<D> readById(K id) {
        return repository.findById(id).map(toDtoConverter);
    }

    public List<D> readAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(toDtoConverter)
                .collect(Collectors.toList());
    }

    @Transactional
    public abstract void update(E entity, K id);

    @Transactional
    public void deleteById(K id) {
        repository.deleteById(id);
    }
}
