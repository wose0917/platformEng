package com.warehouseserve.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "inbounds")
public class Inbound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
    
    private String inboundNumber;
    private String supplier;
    private LocalDateTime inboundDate;
    private String approvalStatus; // PENDING, APPROVED, REJECTED
    private LocalDateTime approvalDate;
    private String approvedBy;
    private String status; // DRAFT, SUBMITTED, PROCESSING, COMPLETED, CANCELLED
    private String remarks;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createdBy;
    private String updatedBy;
} 