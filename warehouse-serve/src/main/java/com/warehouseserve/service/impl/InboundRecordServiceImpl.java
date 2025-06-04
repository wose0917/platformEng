package com.warehouseserve.service.impl;

import com.warehouseserve.dto.InboundRecordDTO;
import com.warehouseserve.entity.Inbound;
import com.warehouseserve.entity.InboundRecord;
import com.warehouseserve.entity.Warehouse;
import com.warehouseserve.exception.DataOperationException;
import com.warehouseserve.repository.InboundRecordRepository;
import com.warehouseserve.repository.InboundRepository;
import com.warehouseserve.repository.WarehouseRepository;
import com.warehouseserve.service.InboundRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InboundRecordServiceImpl implements InboundRecordService {

    private final InboundRecordRepository inboundRecordRepository;
    private final InboundRepository inboundRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public List<InboundRecordDTO> getAllInboundRecords() throws DataOperationException {
        try {
            return inboundRecordRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get all inbound records", e);
        }
    }

    @Override
    public InboundRecordDTO getInboundRecordById(Long id) throws DataOperationException {
        try {
            return inboundRecordRepository.findById(id)
                    .map(this::convertToDTO)
                    .orElseThrow(() -> new DataOperationException("Inbound record not found with id: " + id));
        } catch (Exception e) {
            throw new DataOperationException("Failed to get inbound record by id: " + id, e);
        }
    }

    @Override
    public List<InboundRecordDTO> getInboundRecordsByInboundId(Long inboundId) throws DataOperationException {
        try {
            return inboundRecordRepository.findByInboundId(inboundId).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get inbound records by inbound id: " + inboundId, e);
        }
    }

    @Override
    public List<InboundRecordDTO> getInboundRecordsByWarehouseId(Long warehouseId) throws DataOperationException {
        try {
            return inboundRecordRepository.findByWarehouseId(warehouseId).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get inbound records by warehouse id: " + warehouseId, e);
        }
    }

    @Override
    public List<InboundRecordDTO> getInboundRecordsByStatus(String status) throws DataOperationException {
        try {
            return inboundRecordRepository.findByStatus(status).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get inbound records by status: " + status, e);
        }
    }

    @Override
    @Transactional
    public InboundRecordDTO createInboundRecord(InboundRecordDTO inboundRecordDTO) throws DataOperationException {
        try {
            InboundRecord inboundRecord = convertToEntity(inboundRecordDTO);
            inboundRecord.setRecordTime(LocalDateTime.now());
            inboundRecord.setStatus("PENDING");
            return convertToDTO(inboundRecordRepository.save(inboundRecord));
        } catch (Exception e) {
            throw new DataOperationException("Failed to create inbound record", e);
        }
    }

    @Override
    @Transactional
    public InboundRecordDTO updateInboundRecord(Long id, InboundRecordDTO inboundRecordDTO) throws DataOperationException {
        try {
            InboundRecord existingRecord = inboundRecordRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Inbound record not found with id: " + id));
            
            if (!"PENDING".equals(existingRecord.getStatus())) {
                throw new DataOperationException("Can only update inbound record in PENDING status");
            }
            
            updateInboundRecordFromDTO(existingRecord, inboundRecordDTO);
            return convertToDTO(inboundRecordRepository.save(existingRecord));
        } catch (Exception e) {
            throw new DataOperationException("Failed to update inbound record with id: " + id, e);
        }
    }

    @Override
    @Transactional
    public void deleteInboundRecord(Long id) throws DataOperationException {
        try {
            InboundRecord record = inboundRecordRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Inbound record not found with id: " + id));
            
            if (!"PENDING".equals(record.getStatus())) {
                throw new DataOperationException("Can only delete inbound record in PENDING status");
            }
            
            inboundRecordRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataOperationException("Failed to delete inbound record with id: " + id, e);
        }
    }

    @Override
    @Transactional
    public void updateInboundRecordStatus(Long id, String status) throws DataOperationException {
        try {
            InboundRecord record = inboundRecordRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Inbound record not found with id: " + id));
            
            if (!isValidStatusTransition(record.getStatus(), status)) {
                throw new DataOperationException("Invalid status transition from " + record.getStatus() + " to " + status);
            }
            
            record.setStatus(status);
            inboundRecordRepository.save(record);
            
            // If the record is completed, update the warehouse's used capacity
            if ("COMPLETED".equals(status)) {
                Warehouse warehouse = record.getWarehouse();
                warehouse.setUsedCapacity(warehouse.getUsedCapacity() + record.getQuantity());
                warehouseRepository.save(warehouse);
            }
        } catch (Exception e) {
            throw new DataOperationException("Failed to update inbound record status", e);
        }
    }

    private InboundRecordDTO convertToDTO(InboundRecord record) {
        InboundRecordDTO dto = new InboundRecordDTO();
        dto.setId(record.getId());
        dto.setInboundId(record.getInbound().getId());
        dto.setWarehouseId(record.getWarehouse().getId());
        dto.setProductName(record.getProductName());
        dto.setProductCode(record.getProductCode());
        dto.setQuantity(record.getQuantity());
        dto.setSupplier(record.getSupplier());
        dto.setRecordTime(record.getRecordTime());
        dto.setOperator(record.getOperator());
        dto.setStatus(record.getStatus());
        dto.setRemarks(record.getRemarks());
        
        // Set related information
        dto.setWarehouseName(record.getWarehouse().getName());
        dto.setInboundNumber(record.getInbound().getInboundNumber());
        
        return dto;
    }

    private InboundRecord convertToEntity(InboundRecordDTO dto) throws DataOperationException {
        InboundRecord record = new InboundRecord();
        
        Inbound inbound = inboundRepository.findById(dto.getInboundId())
                .orElseThrow(() -> new DataOperationException("Inbound not found with id: " + dto.getInboundId()));
        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new DataOperationException("Warehouse not found with id: " + dto.getWarehouseId()));
        
        record.setInbound(inbound);
        record.setWarehouse(warehouse);
        record.setProductName(dto.getProductName());
        record.setProductCode(dto.getProductCode());
        record.setQuantity(dto.getQuantity());
        record.setSupplier(dto.getSupplier());
        record.setOperator(dto.getOperator());
        record.setRemarks(dto.getRemarks());
        
        return record;
    }

    private void updateInboundRecordFromDTO(InboundRecord record, InboundRecordDTO dto) throws DataOperationException {
        if (dto.getWarehouseId() != null) {
            Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                    .orElseThrow(() -> new DataOperationException("Warehouse not found with id: " + dto.getWarehouseId()));
            record.setWarehouse(warehouse);
        }
        
        record.setProductName(dto.getProductName());
        record.setProductCode(dto.getProductCode());
        record.setQuantity(dto.getQuantity());
        record.setSupplier(dto.getSupplier());
        record.setOperator(dto.getOperator());
        record.setRemarks(dto.getRemarks());
    }

    private boolean isValidStatusTransition(String currentStatus, String newStatus) {
        switch (currentStatus) {
            case "PENDING":
                return "COMPLETED".equals(newStatus) || "CANCELLED".equals(newStatus);
            case "COMPLETED":
                return false;
            case "CANCELLED":
                return false;
            default:
                return false;
        }
    }
} 