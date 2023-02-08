package com.dev.storesystem.domain.helpers;

import com.dev.storesystem.domain.entities.AbstractEntity;

public interface DateHelper {
    void setSaveTime(AbstractEntity entity);

    void setUpdateTime(AbstractEntity entity);

    void setDeleteTime(AbstractEntity entity);
}
