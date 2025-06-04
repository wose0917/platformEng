package com.warehouseserve.dto;

import lombok.Data;

@Data
public class BaseDataDTO {
    private Long id;
    private String code;
    private String name;
    private String type;
    private String description;
    private Boolean enabled;
    private Integer sortOrder;
    private String createdBy;
    private String updatedBy;
} 