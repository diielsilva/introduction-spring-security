package com.dev.storesystem.domain.entities;

import java.time.OffsetDateTime;

public interface AbstractEntity {
    void setId(Long id);

    Long getId();

    void setCreatedAt(OffsetDateTime createdAt);

    OffsetDateTime getCreatedAt();

    void setUpdatedAt(OffsetDateTime updateAt);

    OffsetDateTime getUpdatedAt();

    void setDeletedAt(OffsetDateTime deletedAt);

    OffsetDateTime getDeletedAt();
}
