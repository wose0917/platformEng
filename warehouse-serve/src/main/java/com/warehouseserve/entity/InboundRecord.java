package com.warehouseserve.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "inbound_records")
public class InboundRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inbound_id")
    private Inbound inbound;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    private String productName;
    private String productCode;
    private Integer quantity;
    private String supplier;
    private LocalDateTime recordTime;
    private String operator;
    private String status; // PENDING, COMPLETED, CANCELLED
    private String remarks;
}
