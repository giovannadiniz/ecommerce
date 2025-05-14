package com.gec.ecommerce.bases;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController<Entity, Filter, ShallowDto, EntityRequest, EntityResponse, EntityService extends BaseService<Entity, Filter>> {

    protected abstract EntityService getEntityService();

//    @PostMapping(params = {"page", "size"})
//    abstract public ResponseEntity<BasePaginatedResponse<ShallowDto>> listAll(
//            @RequestParam(defaultValue = "0", required = false, name = "page") Integer page,
//            @RequestParam(defaultValue = "10", required = false, name = "size") Integer size,
//            @RequestBody Filter filter,
//            HttpServletRequest request
//    );

    @GetMapping(value = "/{id}")
    abstract public ResponseEntity<EntityResponse> findEntityById(@PathVariable("id") Long id);

    @PostMapping(value = "/new")
    abstract public ResponseEntity<EntityResponse> createNew(@RequestBody EntityRequest request) throws ServiceException;

    @PutMapping(value = "/{id}")
    abstract public ResponseEntity<EntityResponse> update(@RequestBody EntityRequest request, @PathVariable("id") Long id) throws ServiceException;

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        getEntityService().delete(id);
        return ResponseEntity.ok().build();
    }

//    public String getCurrentUser() {
//        return SecurityContextHolder.getContext().getAuthentication().getName();
//}
}