package ru.vsu.app.webapp.component;

public interface EntityMapper<E, D> {
    D mapFromEntity(E entity);
    E mapFromDto(D dto);
    E update(E entity, D dto);
}
