package com.warehouseserve.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InboundRecordDTO {
    private Long id;
    private Long inboundId;
    private Long warehouseId;
    private String productName;
    private String productCode;
    private Integer quantity;
    private String supplier;
    private LocalDateTime recordTime;
    private String operator;
    private String status;
    private String remarks;
    
    // 关联对象的名称，用于前端显示
    private String warehouseName;
    private String inboundNumber;
} 