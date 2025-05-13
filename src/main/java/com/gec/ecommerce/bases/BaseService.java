package com.gec.ecommerce.bases;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T, ID> {

    public abstract List<T> findAll();

    public abstract Optional<T> findById(ID id);

    public abstract T save(T entity);

    public abstract T update(ID id, T entity);

    public abstract void delete(ID id) throws EntityNotFoundException;
}