package com.warehouseserve.controller;

import com.warehouseserve.dto.BaseDataDTO;
import com.warehouseserve.exception.DataOperationException;
import com.warehouseserve.service.BaseDataService;
import com.warehouseserve.service.datasource.DataSourceType;
import com.warehouseserve.util.DataSourceContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/base-data")
@RequiredArgsConstructor
public class BaseDataController {

    private final BaseDataService baseDataService;

    @PostMapping("/data-source/switch")
    public ResponseEntity<String> switchDataSource(@RequestParam DataSourceType type) {
        DataSourceContextHolder.setDataSourceType(type);
        return ResponseEntity.ok("数据源已切换至: " + type.name());
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories(
            @RequestParam(required = false) DataSourceType source,
            @RequestParam(required = false) String type) {
        try {
            if (source != null) {
                DataSourceContextHolder.setDataSourceType(source);
            }
            return ResponseEntity.ok(baseDataService.getCategories(type));
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@RequestBody BaseDataDTO category) {
        try {
            return ResponseEntity.ok(baseDataService.createCategory(category));
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @RequestBody BaseDataDTO category) {
        try {
            return ResponseEntity.ok(baseDataService.updateCategory(id, category));
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            baseDataService.deleteCategory(id);
            return ResponseEntity.ok().build();
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/units")
    public ResponseEntity<?> getUnits(
            @RequestParam(required = false) DataSourceType source,
            @RequestParam(required = false) String type) {
        try {
            if (source != null) {
                DataSourceContextHolder.setDataSourceType(source);
            }
            return ResponseEntity.ok(baseDataService.getUnits(type));
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/units")
    public ResponseEntity<?> createUnit(@RequestBody BaseDataDTO unit) {
        try {
            return ResponseEntity.ok(baseDataService.createUnit(unit));
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/units/{id}")
    public ResponseEntity<?> updateUnit(
            @PathVariable Long id,
            @RequestBody BaseDataDTO unit) {
        try {
            return ResponseEntity.ok(baseDataService.updateUnit(id, unit));
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/units/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long id) {
        try {
            baseDataService.deleteUnit(id);
            return ResponseEntity.ok().build();
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus(
            @RequestParam(required = false) DataSourceType source,
            @RequestParam(required = false) String type) {
        try {
            if (source != null) {
                DataSourceContextHolder.setDataSourceType(source);
            }
            return ResponseEntity.ok(baseDataService.getStatus(type));
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/status")
    public ResponseEntity<?> createStatus(@RequestBody BaseDataDTO status) {
        try {
            return ResponseEntity.ok(baseDataService.createStatus(status));
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody BaseDataDTO status) {
        try {
            return ResponseEntity.ok(baseDataService.updateStatus(id, status));
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/status/{id}")
    public ResponseEntity<?> deleteStatus(@PathVariable Long id) {
        try {
            baseDataService.deleteStatus(id);
            return ResponseEntity.ok().build();
        } catch (DataOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
