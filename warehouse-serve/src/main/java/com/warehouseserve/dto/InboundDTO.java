package com.warehouseserve.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InboundDTO {
    private Long id;
    private Long warehouseId;
    private String inboundNumber;
    private String supplier;
    private LocalDateTime inboundDate;
    private String approvalStatus;
    private LocalDateTime approvalDate;
    private String approvedBy;
    private String status;
    private String remarks;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createdBy;
    private String updatedBy;
    
    // 关联对象的名称，用于前端显示
    private String warehouseName;
    private String warehouseLocation;
}
