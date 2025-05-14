package com.gec.ecommerce.bases;



public abstract class BaseMapper<Entity, EntityFilter, EntityShallowDto, EntityRequest, EntityResponse> {

    public abstract Entity filterToEntity(EntityFilter filter);

    public abstract EntityFilter entityToFilter(Entity entity);

    public abstract EntityFilter shallowDtoToFilter(EntityShallowDto entityShallowDto);

    public abstract Entity shallowDtoToEntity(EntityShallowDto entityShallowDto);

    public abstract EntityShallowDto entityToShallowDto(Entity entity);

    public abstract Entity requestToEntity(EntityRequest request);

    public abstract EntityRequest entityToRequest(Entity entity);

    public abstract Entity responseToEntity(EntityResponse response);

    public abstract EntityResponse entityToResponse(Entity entity);
}