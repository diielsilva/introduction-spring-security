package com.dev.storesystem.domain.providers;

import com.dev.storesystem.domain.entities.AbstractEntity;
import com.dev.storesystem.domain.exceptions.EntityNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AbstractProvider<T extends AbstractEntity> {
    void save(T entity);

    Page<T> findAllActive(Pageable pageable);

    Page<T> findAllInactive(Pageable pageable);

    T findActiveById(Long id) throws EntityNotFound;

    T findInactiveById(Long id) throws EntityNotFound;
}
