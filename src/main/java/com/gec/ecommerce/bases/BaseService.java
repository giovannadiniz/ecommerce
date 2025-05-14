package com.gec.ecommerce.bases;


import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<Entity, EntityFilter> {

    protected final Log logger = LogFactory.getLog(getClass());

    public abstract JpaRepository<Entity, Long> getRepository();

    public void save(Entity entity) {
        getRepository().save(entity);
    }

    public Entity saveWithReturn(Entity entity) {
        return getRepository().save(entity);
    }

    public void delete(Long id) {
        Optional<Entity> entity = getRepository().findById(id);
        entity.ifPresentOrElse(
                getRepository()::delete,
                () -> {
                    throw new RuntimeException("Couldn't find ID parameter: " + id);
                }
        );
    }

    public abstract Page<Entity> findAll(int page, int size, EntityFilter filter);

    public Optional<Entity> findById(Long id) {
        return getRepository().findById(id);
}
}