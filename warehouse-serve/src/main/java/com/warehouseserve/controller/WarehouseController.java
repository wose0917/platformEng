package com.warehouseserve.controller;

import com.warehouseserve.dto.WarehouseDTO;
import com.warehouseserve.exception.DataOperationException;
import com.warehouseserve.service.WarehouseService;
import com.warehouseserve.service.datasource.DataSourceType;
import com.warehouseserve.util.DataSourceContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<?> getWarehouses(
            @RequestParam(required = false) DataSourceType source,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String manager,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer minAvailableSpace) {
        try {
            if (source != null) {
                DataSourceContextHolder.setDataSourceType(source);
            }
            
            if (status != null) {
                return ResponseEntity.ok(warehouseService.getWarehousesByStatus(status));
            } else if (location != null) {
                return ResponseEntity.ok(warehouseService.getWarehousesByLocation(location));
            } else if (manager != null) {
                return ResponseEntity.ok(warehouseService.getWarehousesByManager(manager));
            } else if (minCapacity != null) {
                return ResponseEntity.ok(warehouseService.getWarehousesByCapacity(minCapacity));
            } else if (minAvailableSpace != null) {
                return ResponseEntity.ok(warehouseService.getWarehousesByAvailableSpace(minAvailableSpace));
            }
            
            return ResponseEntity.ok(warehouseService.getAllWarehouses());
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWarehouseById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(warehouseService.getWarehouseById(id));
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createWarehouse(@RequestBody WarehouseDTO warehouseDTO) {
        try {
            return ResponseEntity.ok(warehouseService.createWarehouse(warehouseDTO));
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWarehouse(@PathVariable Long id, @RequestBody WarehouseDTO warehouseDTO) {
        try {
            return ResponseEntity.ok(warehouseService.updateWarehouse(id, warehouseDTO));
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable Long id) {
        try {
            warehouseService.deleteWarehouse(id);
            return ResponseEntity.ok().build();
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateWarehouseStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        try {
            warehouseService.updateWarehouseStatus(id, status);
            return ResponseEntity.ok().build();
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/capacity")
    public ResponseEntity<?> updateWarehouseCapacity(
            @PathVariable Long id,
            @RequestParam Integer capacity) {
        try {
            warehouseService.updateWarehouseCapacity(id, capacity);
            return ResponseEntity.ok().build();
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/manager")
    public ResponseEntity<?> updateWarehouseManager(
            @PathVariable Long id,
            @RequestParam String manager) {
        try {
            warehouseService.updateWarehouseManager(id, manager);
            return ResponseEntity.ok().build();
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/contact")
    public ResponseEntity<?> updateWarehouseContact(
            @PathVariable Long id,
            @RequestParam String phone,
            @RequestParam String email) {
        try {
            warehouseService.updateWarehouseContact(id, phone, email);
            return ResponseEntity.ok().build();
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/check-space")
    public ResponseEntity<?> checkAvailableSpace(
            @PathVariable Long id,
            @RequestParam Integer requiredSpace) {
        try {
            boolean hasSpace = warehouseService.checkAvailableSpace(id, requiredSpace);
            return ResponseEntity.ok(hasSpace);
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}