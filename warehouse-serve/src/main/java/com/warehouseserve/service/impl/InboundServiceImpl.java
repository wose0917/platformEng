package com.warehouseserve.service.impl;

import com.warehouseserve.dto.InboundDTO;
import com.warehouseserve.entity.Inbound;
import com.warehouseserve.entity.Warehouse;
import com.warehouseserve.exception.DataOperationException;
import com.warehouseserve.repository.InboundRepository;
import com.warehouseserve.repository.WarehouseRepository;
import com.warehouseserve.service.InboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InboundServiceImpl implements InboundService {

    private final InboundRepository inboundRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public List<InboundDTO> getAllInbounds() throws DataOperationException {
        try {
            return inboundRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get all inbounds", e);
        }
    }

    @Override
    public InboundDTO getInboundById(Long id) throws DataOperationException {
        try {
            return inboundRepository.findById(id)
                    .map(this::convertToDTO)
                    .orElseThrow(() -> new DataOperationException("Inbound not found with id: " + id));
        } catch (Exception e) {
            throw new DataOperationException("Failed to get inbound by id: " + id, e);
        }
    }

    @Override
    public List<InboundDTO> getInboundsByWarehouseId(Long warehouseId) throws DataOperationException {
        try {
            return inboundRepository.findByWarehouseId(warehouseId).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get inbounds by warehouse id: " + warehouseId, e);
        }
    }

    @Override
    public List<InboundDTO> getInboundsByStatus(String status) throws DataOperationException {
        try {
            return inboundRepository.findByStatus(status).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get inbounds by status: " + status, e);
        }
    }

    @Override
    public List<InboundDTO> getInboundsByApprovalStatus(String approvalStatus) throws DataOperationException {
        try {
            return inboundRepository.findByApprovalStatus(approvalStatus).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get inbounds by approval status: " + approvalStatus, e);
        }
    }

    @Override
    public List<InboundDTO> getInboundsByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws DataOperationException {
        try {
            return inboundRepository.findByInboundDateBetween(startDate, endDate).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get inbounds by date range", e);
        }
    }

    @Override
    public List<InboundDTO> getInboundsBySupplier(String supplier) throws DataOperationException {
        try {
            return inboundRepository.findBySupplier(supplier).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get inbounds by supplier: " + supplier, e);
        }
    }

    @Override
    public List<InboundDTO> getInboundsByCreator(String createdBy) throws DataOperationException {
        try {
            return inboundRepository.findByCreatedBy(createdBy).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get inbounds by creator: " + createdBy, e);
        }
    }

    @Override
    @Transactional
    public InboundDTO createInbound(InboundDTO inboundDTO) throws DataOperationException {
        try {
            Inbound inbound = convertToEntity(inboundDTO);
            inbound.setCreateTime(LocalDateTime.now());
            inbound.setUpdateTime(LocalDateTime.now());
            inbound.setStatus("DRAFT");
            inbound.setApprovalStatus("PENDING");
            return convertToDTO(inboundRepository.save(inbound));
        } catch (Exception e) {
            throw new DataOperationException("Failed to create inbound", e);
        }
    }

    @Override
    @Transactional
    public InboundDTO updateInbound(Long id, InboundDTO inboundDTO) throws DataOperationException {
        try {
            Inbound existingInbound = inboundRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Inbound not found with id: " + id));
            
            if (!"DRAFT".equals(existingInbound.getStatus())) {
                throw new DataOperationException("Can only update inbound in DRAFT status");
            }
            
            updateInboundFromDTO(existingInbound, inboundDTO);
            existingInbound.setUpdateTime(LocalDateTime.now());
            
            return convertToDTO(inboundRepository.save(existingInbound));
        } catch (Exception e) {
            throw new DataOperationException("Failed to update inbound with id: " + id, e);
        }
    }

    @Override
    @Transactional
    public void deleteInbound(Long id) throws DataOperationException {
        try {
            Inbound inbound = inboundRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Inbound not found with id: " + id));
            
            if (!"DRAFT".equals(inbound.getStatus())) {
                throw new DataOperationException("Can only delete inbound in DRAFT status");
            }
            
            inboundRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataOperationException("Failed to delete inbound with id: " + id, e);
        }
    }

    @Override
    @Transactional
    public void updateInboundStatus(Long id, String status) throws DataOperationException {
        try {
            Inbound inbound = inboundRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Inbound not found with id: " + id));
            
            if (!isValidStatusTransition(inbound.getStatus(), status)) {
                throw new DataOperationException("Invalid status transition from " + inbound.getStatus() + " to " + status);
            }
            
            inbound.setStatus(status);
            inbound.setUpdateTime(LocalDateTime.now());
            inboundRepository.save(inbound);
        } catch (Exception e) {
            throw new DataOperationException("Failed to update inbound status", e);
        }
    }

    @Override
    @Transactional
    public void updateApprovalStatus(Long id, String approvalStatus, String approvedBy) throws DataOperationException {
        try {
            Inbound inbound = inboundRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Inbound not found with id: " + id));
            
            if (!"SUBMITTED".equals(inbound.getStatus())) {
                throw new DataOperationException("Can only approve/reject submitted inbounds");
            }
            
            inbound.setApprovalStatus(approvalStatus);
            inbound.setApprovalDate(LocalDateTime.now());
            inbound.setApprovedBy(approvedBy);
            inbound.setUpdateTime(LocalDateTime.now());
            
            if ("APPROVED".equals(approvalStatus)) {
                inbound.setStatus("PROCESSING");
            } else if ("REJECTED".equals(approvalStatus)) {
                inbound.setStatus("CANCELLED");
            }
            
            inboundRepository.save(inbound);
        } catch (Exception e) {
            throw new DataOperationException("Failed to update approval status", e);
        }
    }

    @Override
    @Transactional
    public void submitForApproval(Long id) throws DataOperationException {
        try {
            Inbound inbound = inboundRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Inbound not found with id: " + id));
            
            if (!"DRAFT".equals(inbound.getStatus())) {
                throw new DataOperationException("Can only submit DRAFT inbounds for approval");
            }
            
            inbound.setStatus("SUBMITTED");
            inbound.setApprovalStatus("PENDING");
            inbound.setUpdateTime(LocalDateTime.now());
            inboundRepository.save(inbound);
        } catch (Exception e) {
            throw new DataOperationException("Failed to submit inbound for approval", e);
        }
    }

    @Override
    @Transactional
    public void cancelInbound(Long id) throws DataOperationException {
        try {
            Inbound inbound = inboundRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Inbound not found with id: " + id));
            
            if ("COMPLETED".equals(inbound.getStatus())) {
                throw new DataOperationException("Cannot cancel completed inbound");
            }
            
            inbound.setStatus("CANCELLED");
            inbound.setUpdateTime(LocalDateTime.now());
            inboundRepository.save(inbound);
        } catch (Exception e) {
            throw new DataOperationException("Failed to cancel inbound", e);
        }
    }

    private InboundDTO convertToDTO(Inbound inbound) {
        InboundDTO dto = new InboundDTO();
        dto.setId(inbound.getId());
        dto.setWarehouseId(inbound.getWarehouse().getId());
        dto.setInboundNumber(inbound.getInboundNumber());
        dto.setSupplier(inbound.getSupplier());
        dto.setInboundDate(inbound.getInboundDate());
        dto.setApprovalStatus(inbound.getApprovalStatus());
        dto.setApprovalDate(inbound.getApprovalDate());
        dto.setApprovedBy(inbound.getApprovedBy());
        dto.setStatus(inbound.getStatus());
        dto.setRemarks(inbound.getRemarks());
        dto.setCreateTime(inbound.getCreateTime());
        dto.setUpdateTime(inbound.getUpdateTime());
        dto.setCreatedBy(inbound.getCreatedBy());
        dto.setUpdatedBy(inbound.getUpdatedBy());
        
        // Set warehouse information
        dto.setWarehouseName(inbound.getWarehouse().getName());
        dto.setWarehouseLocation(inbound.getWarehouse().getLocation());
        
        return dto;
    }

    private Inbound convertToEntity(InboundDTO dto) throws DataOperationException {
        Inbound inbound = new Inbound();
        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new DataOperationException("Warehouse not found with id: " + dto.getWarehouseId()));
        inbound.setWarehouse(warehouse);
        inbound.setInboundNumber(dto.getInboundNumber());
        inbound.setSupplier(dto.getSupplier());
        inbound.setInboundDate(dto.getInboundDate());
        inbound.setCreatedBy(dto.getCreatedBy());
        inbound.setUpdatedBy(dto.getUpdatedBy());
        inbound.setRemarks(dto.getRemarks());
        return inbound;
    }

    private void updateInboundFromDTO(Inbound inbound, InboundDTO dto) throws DataOperationException {
        if (dto.getWarehouseId() != null) {
            Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                    .orElseThrow(() -> new DataOperationException("Warehouse not found with id: " + dto.getWarehouseId()));
            inbound.setWarehouse(warehouse);
        }
        inbound.setInboundNumber(dto.getInboundNumber());
        inbound.setSupplier(dto.getSupplier());
        inbound.setInboundDate(dto.getInboundDate());
        inbound.setUpdatedBy(dto.getUpdatedBy());
        inbound.setRemarks(dto.getRemarks());
    }

    private boolean isValidStatusTransition(String currentStatus, String newStatus) {
        switch (currentStatus) {
            case "DRAFT":
                return "SUBMITTED".equals(newStatus) || "CANCELLED".equals(newStatus);
            case "SUBMITTED":
                return "PROCESSING".equals(newStatus) || "CANCELLED".equals(newStatus);
            case "PROCESSING":
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