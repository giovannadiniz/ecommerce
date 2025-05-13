package com.gec.ecommerce.bases;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController<T, ID> {
    protected final BaseService<T, ID> service;

    public BaseController(BaseService<T, ID> service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<T>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable ID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{new}")
    public ResponseEntity<T> create(@RequestBody T newEntity) {
        return ResponseEntity.ok(service.save(newEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable ID id, @RequestBody T entity) {
        return ResponseEntity.ok(service.update(id, entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}