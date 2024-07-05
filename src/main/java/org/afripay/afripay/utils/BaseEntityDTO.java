package org.afripay.afripay.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class BaseEntityDTO {

    private String createdDate;

    private String updatedDate;

    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime updatedAt;

    @JsonIgnore
    private static final DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        if (this.createdAt != null) {
            this.createdDate = this.createdAt.format(customFormat);
        }
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        if (this.updatedAt != null) {
            this.updatedDate = this.updatedAt.format(customFormat);
        }
    }
}