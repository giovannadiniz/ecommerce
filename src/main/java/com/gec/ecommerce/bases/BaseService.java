package com.gec.ecommerce.bases;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T, ID, R, S> {
    public abstract List<R> findAll();

    public abstract Optional<R> findById(ID id);

    public abstract R save(S entity);

    public abstract R update(ID id, S entity);

    public abstract void delete(ID id) throws EntityNotFoundException;
}