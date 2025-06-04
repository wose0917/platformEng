package com.warehouseserve.controller;

import com.warehouseserve.dto.InboundDTO;
import com.warehouseserve.exception.DataOperationException;
import com.warehouseserve.service.InboundService;
import com.warehouseserve.service.datasource.DataSourceType;
import com.warehouseserve.util.DataSourceContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/inbounds")
@RequiredArgsConstructor
public class InboundController {

    private final InboundService inboundService;

    @GetMapping
    public ResponseEntity<?> getInbounds(
            @RequestParam(required = false) DataSourceType source,
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String approvalStatus,
            @RequestParam(required = false) String supplier,
            @RequestParam(required = false) String createdBy,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            if (source != null) {
                DataSourceContextHolder.setDataSourceType(source);
            }
            
            if (warehouseId != null) {
                return ResponseEntity.ok(inboundService.getInboundsByWarehouseId(warehouseId));
            } else if (status != null) {
                return ResponseEntity.ok(inboundService.getInboundsByStatus(status));
            } else if (approvalStatus != null) {
                return ResponseEntity.ok(inboundService.getInboundsByApprovalStatus(approvalStatus));
            } else if (supplier != null) {
                return ResponseEntity.ok(inboundService.getInboundsBySupplier(supplier));
            } else if (createdBy != null) {
                return ResponseEntity.ok(inboundService.getInboundsByCreator(createdBy));
            } else if (startDate != null && endDate != null) {
                return ResponseEntity.ok(inboundService.getInboundsByDateRange(startDate, endDate));
            }
            
            return ResponseEntity.ok(inboundService.getAllInbounds());
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInboundById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(inboundService.getInboundById(id));
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createInbound(@RequestBody InboundDTO inboundDTO) {
        try {
            return ResponseEntity.ok(inboundService.createInbound(inboundDTO));
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInbound(@PathVariable Long id, @RequestBody InboundDTO inboundDTO) {
        try {
            return ResponseEntity.ok(inboundService.updateInbound(id, inboundDTO));
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInbound(@PathVariable Long id) {
        try {
            inboundService.deleteInbound(id);
            return ResponseEntity.ok().build();
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateInboundStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        try {
            inboundService.updateInboundStatus(id, status);
            return ResponseEntity.ok().build();
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/approval")
    public ResponseEntity<?> updateApprovalStatus(
            @PathVariable Long id,
            @RequestParam String approvalStatus,
            @RequestParam String approvedBy) {
        try {
            inboundService.updateApprovalStatus(id, approvalStatus, approvedBy);
            return ResponseEntity.ok().build();
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<?> submitForApproval(@PathVariable Long id) {
        try {
            inboundService.submitForApproval(id);
            return ResponseEntity.ok().build();
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelInbound(@PathVariable Long id) {
        try {
            inboundService.cancelInbound(id);
            return ResponseEntity.ok().build();
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
