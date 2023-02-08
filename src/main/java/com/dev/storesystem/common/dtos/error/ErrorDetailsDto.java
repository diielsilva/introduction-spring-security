package com.dev.storesystem.common.dtos.error;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ErrorDetailsDto {
    private OffsetDateTime timestamps;
    private Integer status;
    private String message;
    private String path;
    private final List<String> details = new ArrayList<>();

    public OffsetDateTime getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(OffsetDateTime timestamps) {
        this.timestamps = timestamps;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getDetails() {
        return details;
    }
}
