package com.dev.storesystem.domain.helpers.impl;

import com.dev.storesystem.domain.entities.AbstractEntity;
import com.dev.storesystem.domain.helpers.DateHelper;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class DateHelperImpl implements DateHelper {
    @Override
    public void setSaveTime(AbstractEntity entity) {
        entity.setCreatedAt(OffsetDateTime.now());
        entity.setUpdatedAt(OffsetDateTime.now());
    }

    @Override
    public void setUpdateTime(AbstractEntity entity) {
        entity.setUpdatedAt(OffsetDateTime.now());
    }

    @Override
    public void setDeleteTime(AbstractEntity entity) {
        entity.setDeletedAt(OffsetDateTime.now());
    }
}
