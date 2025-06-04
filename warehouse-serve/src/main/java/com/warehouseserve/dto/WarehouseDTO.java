package com.warehouseserve.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WarehouseDTO {
    private Long id;
    private String name;
    private String location;
    private Integer capacity;
    private Integer usedCapacity;
    private Integer availableCapacity;
    private String status;
    private String manager;
    private String contactPhone;
    private String contactEmail;
    private String contact;
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createdBy;
    private String updatedBy;
    private String remarks;
    
    // 计算属性
    public Integer getAvailableCapacity() {
        return capacity - usedCapacity;
    }
}
