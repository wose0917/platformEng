package com.warehouseserve.service.datasource;

import com.warehouseserve.dto.InboundDTO;
import com.warehouseserve.dto.WarehouseDTO;
import com.warehouseserve.entity.Inbound;
import com.warehouseserve.entity.Warehouse;
import com.warehouseserve.repository.InboundRepository;
import com.warehouseserve.repository.WarehouseRepository;
import com.warehouseserve.service.InboundService;
import com.warehouseserve.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabaseDataSource implements WarehouseService, InboundService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private InboundRepository inboundRepository;

    @Override
    public List<WarehouseDTO> getAllWarehouses() {
        return warehouseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WarehouseDTO getWarehouseById(Long id) {
        return warehouseRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO) {
        return convertToDTO(warehouseRepository.save(convertToEntity(warehouseDTO)));
    }

    @Override
    public void deleteWarehouse(Long id) {
        warehouseRepository.deleteById(id);
    }

    private WarehouseDTO convertToDTO(Warehouse warehouse) {
        if (warehouse == null) return null;
        WarehouseDTO dto = new WarehouseDTO();
        dto.setId(warehouse.getId());
        dto.setName(warehouse.getName());
        dto.setLocation(warehouse.getLocation());
        dto.setCapacity(warehouse.getCapacity());
        dto.setUsedCapacity(warehouse.getUsedCapacity());
        dto.setManager(warehouse.getManager());
        dto.setStatus(warehouse.getStatus());
        dto.setContact(warehouse.getContact());
        dto.setPhone(warehouse.getPhone());
        dto.setContactPhone(warehouse.getContactPhone());
        dto.setContactEmail(warehouse.getContactEmail());
        dto.setCreatedBy(warehouse.getCreatedBy());
        dto.setUpdatedBy(warehouse.getUpdatedBy());
        dto.setCreateTime(warehouse.getCreateTime());
        dto.setUpdateTime(warehouse.getUpdateTime());
        dto.setRemarks(warehouse.getRemarks());
        return dto;
    }

    @Override
    public List<InboundDTO> getAllInbounds() {
        return inboundRepository.findAll().stream()
                .map(this::convertToInboundDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InboundDTO getInboundById(Long id) {
        return inboundRepository.findById(id)
                .map(this::convertToInboundDTO)
                .orElse(null);
    }

    @Override
    public InboundDTO createInbound(InboundDTO inboundDTO) {
        return convertToInboundDTO(inboundRepository.save(convertToEntity(inboundDTO)));
    }

    @Override
    public void deleteInbound(Long id) {
        inboundRepository.deleteById(id);
    }

    @Override
    public void updateApprovalStatus(Long id, String status, String approver) {
        inboundRepository.findById(id).ifPresent(inbound -> {
            inbound.setApprovalStatus(status);
            inbound.setApprovalDate(LocalDateTime.now());
            inbound.setApprovedBy(approver);
            inboundRepository.save(inbound);
        });
    }

    @Override
    public List<InboundDTO> getInboundsByStatus(String status) {
        return inboundRepository.findByStatus(status).stream()
                .map(this::convertToInboundDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InboundDTO> getInboundsByApprovalStatus(String status) {
        return inboundRepository.findByApprovalStatus(status).stream()
                .map(this::convertToInboundDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InboundDTO> getInboundsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return inboundRepository.findByInboundDateBetween(startDate, endDate).stream()
                .map(this::convertToInboundDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateInboundStatus(Long id, String status) {
        inboundRepository.findById(id).ifPresent(inbound -> {
            inbound.setStatus(status);
            inboundRepository.save(inbound);
        });
    }

    @Override
    public List<InboundDTO> getInboundsBySupplier(String supplier) {
        return inboundRepository.findBySupplier(supplier).stream()
                .map(this::convertToInboundDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void submitForApproval(Long id) {
        inboundRepository.findById(id).ifPresent(inbound -> {
            inbound.setApprovalStatus("待审批");
            inboundRepository.save(inbound);
        });
    }

    @Override
    public void cancelInbound(Long id) {
        inboundRepository.findById(id).ifPresent(inbound -> {
            inbound.setStatus("已取消");
            inboundRepository.save(inbound);
        });
    }

    @Override
    public List<InboundDTO> getInboundsByWarehouseId(Long warehouseId) {
        return inboundRepository.findByWarehouseId(warehouseId).stream()
                .map(this::convertToInboundDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InboundDTO> getInboundsByCreator(String creator) {
        return inboundRepository.findByCreatedBy(creator).stream()
                .map(this::convertToInboundDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InboundDTO updateInbound(Long id, InboundDTO inboundDTO) {
        return inboundRepository.findById(id)
                .map(inbound -> {
                    // Update inbound fields
                    inbound.setSupplier(inboundDTO.getSupplier());
                    inbound.setInboundDate(inboundDTO.getInboundDate());
                    inbound.setStatus(inboundDTO.getStatus());
                    inbound.setApprovalStatus(inboundDTO.getApprovalStatus());
                    return convertToInboundDTO(inboundRepository.save(inbound));
                })
                .orElse(null);
    }

    private InboundDTO convertToInboundDTO(Inbound inbound) {
        if (inbound == null) return null;
        InboundDTO dto = new InboundDTO();
        dto.setId(inbound.getId());
        dto.setWarehouseId(inbound.getWarehouse().getId());
        dto.setInboundNumber(inbound.getInboundNumber());
        dto.setSupplier(inbound.getSupplier());
        dto.setInboundDate(inbound.getInboundDate());
        dto.setStatus(inbound.getStatus());
        dto.setApprovalStatus(inbound.getApprovalStatus());
        dto.setCreatedBy(inbound.getCreatedBy());
        dto.setCreateTime(inbound.getCreateTime());
        return dto;
    }

    private Inbound convertToEntity(InboundDTO dto) {
        if (dto == null) return null;
        Inbound inbound = new Inbound();
        inbound.setId(dto.getId());
        inbound.setWarehouse(warehouseRepository.findById(dto.getWarehouseId()).orElse(null));
        inbound.setInboundNumber(dto.getInboundNumber());
        inbound.setSupplier(dto.getSupplier());
        inbound.setInboundDate(dto.getInboundDate());
        inbound.setStatus(dto.getStatus());
        inbound.setApprovalStatus(dto.getApprovalStatus());
        inbound.setCreatedBy(dto.getCreatedBy());
        inbound.setCreateTime(dto.getCreateTime());
        return inbound;
    }

    @Override
    public List<WarehouseDTO> getWarehousesByStatus(String status) {
        return warehouseRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private Warehouse convertToEntity(WarehouseDTO dto) {
        if (dto == null) return null;
        Warehouse warehouse = new Warehouse();
        warehouse.setId(dto.getId());
        warehouse.setName(dto.getName());
        warehouse.setLocation(dto.getLocation());
        warehouse.setCapacity(dto.getCapacity());
        warehouse.setUsedCapacity(dto.getUsedCapacity() != null ? dto.getUsedCapacity() : 0);
        warehouse.setManager(dto.getManager());
        warehouse.setStatus(dto.getStatus() != null ? dto.getStatus() : "正常");
        warehouse.setContact(dto.getManager());
        warehouse.setPhone(dto.getContactPhone());
        warehouse.setContactPhone(dto.getContactPhone());
        warehouse.setContactEmail(dto.getContactEmail());
        warehouse.setCreatedBy("admin");
        warehouse.setUpdatedBy("admin");
        warehouse.setCreateTime(LocalDateTime.now());
        warehouse.setUpdateTime(LocalDateTime.now());
        warehouse.setRemarks(dto.getRemarks());
        return warehouse;
    }

    @Override
    public void updateWarehouseContact(Long id, String contact, String phone) {
        warehouseRepository.findById(id).ifPresent(warehouse -> {
            warehouse.setContact(contact);
            warehouse.setPhone(phone);
            warehouseRepository.save(warehouse);
        });
    }

    @Override
    public List<WarehouseDTO> getWarehousesByManager(String manager) {
        return warehouseRepository.findByManager(manager).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void increaseUsedCapacity(Long id, Integer amount) {
        warehouseRepository.findById(id).ifPresent(warehouse -> {
            warehouse.setUsedCapacity(warehouse.getUsedCapacity() + amount);
            warehouseRepository.save(warehouse);
        });
    }

    @Override
    public void decreaseUsedCapacity(Long id, Integer amount) {
        warehouseRepository.findById(id).ifPresent(warehouse -> {
            warehouse.setUsedCapacity(warehouse.getUsedCapacity() - amount);
            warehouseRepository.save(warehouse);
        });
    }

    @Override
    public void updateWarehouseCapacity(Long id, Integer capacity) {
        warehouseRepository.findById(id).ifPresent(warehouse -> {
            warehouse.setCapacity(capacity);
            warehouseRepository.save(warehouse);
        });
    }

    @Override
    public WarehouseDTO updateWarehouse(Long id, WarehouseDTO warehouseDTO) {
        return warehouseRepository.findById(id)
                .map(warehouse -> {
                    // Update warehouse fields
                    warehouse.setName(warehouseDTO.getName());
                    warehouse.setLocation(warehouseDTO.getLocation());
                    warehouse.setCapacity(warehouseDTO.getCapacity());
                    warehouse.setManager(warehouseDTO.getManager());
                    warehouse.setContact(warehouseDTO.getContact());
                    warehouse.setPhone(warehouseDTO.getPhone());
                    warehouse.setStatus(warehouseDTO.getStatus());
                    return convertToDTO(warehouseRepository.save(warehouse));
                })
                .orElse(null);
    }

    @Override
    public List<WarehouseDTO> getWarehousesByCapacity(Integer capacity) {
        return warehouseRepository.findByCapacityGreaterThanEqual(capacity).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkAvailableSpace(Long id, Integer requiredSpace) {
        return warehouseRepository.findById(id)
                .map(warehouse -> warehouse.getCapacity() - warehouse.getUsedCapacity() >= requiredSpace)
                .orElse(false);
    }

    @Override
    public List<WarehouseDTO> getWarehousesByAvailableSpace(Integer requiredSpace) {
        return warehouseRepository.findByAvailableSpaceGreaterThanEqual(requiredSpace).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateWarehouseStatus(Long id, String status) {
        warehouseRepository.findById(id).ifPresent(warehouse -> {
            warehouse.setStatus(status);
            warehouseRepository.save(warehouse);
        });
    }

    @Override
    public List<WarehouseDTO> getWarehousesByLocation(String location) {
        return warehouseRepository.findByLocation(location).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateWarehouseManager(Long id, String manager) {
        warehouseRepository.findById(id).ifPresent(warehouse -> {
            warehouse.setManager(manager);
            warehouseRepository.save(warehouse);
        });
    }

    // 其他方法实现...
}