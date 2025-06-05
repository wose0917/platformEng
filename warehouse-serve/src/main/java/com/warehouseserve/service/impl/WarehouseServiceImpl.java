package com.warehouseserve.service.impl;

import com.warehouseserve.dto.WarehouseDTO;
import com.warehouseserve.entity.Warehouse;
import com.warehouseserve.exception.DataOperationException;
import com.warehouseserve.repository.WarehouseRepository;
import com.warehouseserve.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Override
    public List<WarehouseDTO> getAllWarehouses() throws DataOperationException {
        try {
            return warehouseRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get all warehouses", e);
        }
    }

    @Override
    public WarehouseDTO getWarehouseById(Long id) throws DataOperationException {
        try {
            return warehouseRepository.findById(id)
                    .map(this::convertToDTO)
                    .orElseThrow(() -> new DataOperationException("Warehouse not found with id: " + id));
        } catch (Exception e) {
            throw new DataOperationException("Failed to get warehouse by id: " + id, e);
        }
    }

    @Override
    public List<WarehouseDTO> getWarehousesByStatus(String status) throws DataOperationException {
        try {
            return warehouseRepository.findByStatus(status).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get warehouses by status: " + status, e);
        }
    }

    @Override
    public List<WarehouseDTO> getWarehousesByLocation(String location) throws DataOperationException {
        try {
            return warehouseRepository.findByLocation(location).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get warehouses by location: " + location, e);
        }
    }

    @Override
    public List<WarehouseDTO> getWarehousesByManager(String manager) throws DataOperationException {
        try {
            return warehouseRepository.findByManager(manager).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get warehouses by manager: " + manager, e);
        }
    }

    @Override
    public List<WarehouseDTO> getWarehousesByCapacity(Integer minCapacity) throws DataOperationException {
        try {
            return warehouseRepository.findByCapacityGreaterThanEqual(minCapacity).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get warehouses by capacity: " + minCapacity, e);
        }
    }

    @Override
    public List<WarehouseDTO> getWarehousesByAvailableSpace(Integer minAvailableSpace) throws DataOperationException {
        try {
            return warehouseRepository.findByUsedCapacityLessThanEqual(minAvailableSpace).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get warehouses by available space: " + minAvailableSpace, e);
        }
    }

    @Override
    public List<WarehouseDTO> getWarehousesByName(String name) throws DataOperationException {
        try {
            return warehouseRepository.findByNameContaining(name).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get warehouses by name: " + name, e);
        }
    }

    @Override
    @Transactional
    public WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO) throws DataOperationException {
        try {
            Warehouse warehouse = convertToEntity(warehouseDTO);
            warehouse.setCreateTime(LocalDateTime.now());
            warehouse.setUpdateTime(LocalDateTime.now());
            warehouse.setUsedCapacity(0);
            return convertToDTO(warehouseRepository.save(warehouse));
        } catch (Exception e) {
            throw new DataOperationException("Failed to create warehouse", e);
        }
    }

    @Override
    @Transactional
    public WarehouseDTO updateWarehouse(Long id, WarehouseDTO warehouseDTO) throws DataOperationException {
        try {
            Warehouse existingWarehouse = warehouseRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Warehouse not found with id: " + id));
            
            updateWarehouseFromDTO(existingWarehouse, warehouseDTO);
            existingWarehouse.setUpdateTime(LocalDateTime.now());
            
            return convertToDTO(warehouseRepository.save(existingWarehouse));
        } catch (Exception e) {
            throw new DataOperationException("Failed to update warehouse with id: " + id, e);
        }
    }

    @Override
    @Transactional
    public void deleteWarehouse(Long id) throws DataOperationException {
        try {
            if (!warehouseRepository.existsById(id)) {
                throw new DataOperationException("Warehouse not found with id: " + id);
            }
            warehouseRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataOperationException("Failed to delete warehouse with id: " + id, e);
        }
    }

    @Override
    @Transactional
    public void updateWarehouseStatus(Long id, String status) throws DataOperationException {
        try {
            Warehouse warehouse = warehouseRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Warehouse not found with id: " + id));
            warehouse.setStatus(status);
            warehouse.setUpdateTime(LocalDateTime.now());
            warehouseRepository.save(warehouse);
        } catch (Exception e) {
            throw new DataOperationException("Failed to update warehouse status", e);
        }
    }

    @Override
    @Transactional
    public void updateWarehouseCapacity(Long id, Integer capacity) throws DataOperationException {
        try {
            Warehouse warehouse = warehouseRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Warehouse not found with id: " + id));
            if (capacity < warehouse.getUsedCapacity()) {
                throw new DataOperationException("New capacity cannot be less than used capacity");
            }
            warehouse.setCapacity(capacity);
            warehouse.setUpdateTime(LocalDateTime.now());
            warehouseRepository.save(warehouse);
        } catch (Exception e) {
            throw new DataOperationException("Failed to update warehouse capacity", e);
        }
    }

    @Override
    @Transactional
    public void updateWarehouseManager(Long id, String manager) throws DataOperationException {
        try {
            Warehouse warehouse = warehouseRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Warehouse not found with id: " + id));
            warehouse.setManager(manager);
            warehouse.setUpdateTime(LocalDateTime.now());
            warehouseRepository.save(warehouse);
        } catch (Exception e) {
            throw new DataOperationException("Failed to update warehouse manager", e);
        }
    }

    @Override
    @Transactional
    public void updateWarehouseContact(Long id, String phone, String email) throws DataOperationException {
        try {
            Warehouse warehouse = warehouseRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Warehouse not found with id: " + id));
            warehouse.setContactPhone(phone);
            warehouse.setContactEmail(email);
            warehouse.setUpdateTime(LocalDateTime.now());
            warehouseRepository.save(warehouse);
        } catch (Exception e) {
            throw new DataOperationException("Failed to update warehouse contact", e);
        }
    }

    @Override
    @Transactional
    public void increaseUsedCapacity(Long id, Integer amount) throws DataOperationException {
        try {
            Warehouse warehouse = warehouseRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Warehouse not found with id: " + id));
            int newUsedCapacity = warehouse.getUsedCapacity() + amount;
            if (newUsedCapacity > warehouse.getCapacity()) {
                throw new DataOperationException("Insufficient warehouse capacity");
            }
            warehouse.setUsedCapacity(newUsedCapacity);
            warehouse.setUpdateTime(LocalDateTime.now());
            warehouseRepository.save(warehouse);
        } catch (Exception e) {
            throw new DataOperationException("Failed to increase used capacity", e);
        }
    }

    @Override
    @Transactional
    public void decreaseUsedCapacity(Long id, Integer amount) throws DataOperationException {
        try {
            Warehouse warehouse = warehouseRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Warehouse not found with id: " + id));
            int newUsedCapacity = warehouse.getUsedCapacity() - amount;
            if (newUsedCapacity < 0) {
                throw new DataOperationException("Used capacity cannot be negative");
            }
            warehouse.setUsedCapacity(newUsedCapacity);
            warehouse.setUpdateTime(LocalDateTime.now());
            warehouseRepository.save(warehouse);
        } catch (Exception e) {
            throw new DataOperationException("Failed to decrease used capacity", e);
        }
    }

    @Override
    public boolean checkAvailableSpace(Long id, Integer requiredSpace) throws DataOperationException {
        try {
            Warehouse warehouse = warehouseRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Warehouse not found with id: " + id));
            return (warehouse.getCapacity() - warehouse.getUsedCapacity()) >= requiredSpace;
        } catch (Exception e) {
            throw new DataOperationException("Failed to check available space", e);
        }
    }

    private WarehouseDTO convertToDTO(Warehouse warehouse) {
        WarehouseDTO dto = new WarehouseDTO();
        dto.setId(warehouse.getId());
        dto.setName(warehouse.getName());
        dto.setLocation(warehouse.getLocation());
        dto.setCapacity(warehouse.getCapacity());
        dto.setUsedCapacity(warehouse.getUsedCapacity());
        dto.setStatus(warehouse.getStatus());
        dto.setManager(warehouse.getManager());
        dto.setContactPhone(warehouse.getContactPhone());
        dto.setContactEmail(warehouse.getContactEmail());
        dto.setCreateTime(warehouse.getCreateTime());
        dto.setUpdateTime(warehouse.getUpdateTime());
        dto.setCreatedBy(warehouse.getCreatedBy());
        dto.setUpdatedBy(warehouse.getUpdatedBy());
        dto.setRemarks(warehouse.getRemarks());
        return dto;
    }

    private Warehouse convertToEntity(WarehouseDTO dto) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(dto.getName());
        warehouse.setLocation(dto.getLocation());
        warehouse.setCapacity(dto.getCapacity());
        warehouse.setStatus(dto.getStatus() != null ? dto.getStatus() : "ACTIVE");
        warehouse.setManager(dto.getManager());
        warehouse.setContact(dto.getContact());
        warehouse.setPhone(dto.getPhone());
        warehouse.setContactPhone(dto.getContactPhone());
        warehouse.setContactEmail(dto.getContactEmail());
        warehouse.setCreatedBy(dto.getCreatedBy() != null ? dto.getCreatedBy() : "admin");
        warehouse.setUpdatedBy(dto.getUpdatedBy() != null ? dto.getUpdatedBy() : "admin");
        warehouse.setRemarks(dto.getRemarks());
        warehouse.setUsedCapacity(0);
        return warehouse;
    }

    private void updateWarehouseFromDTO(Warehouse warehouse, WarehouseDTO dto) {
        warehouse.setName(dto.getName());
        warehouse.setLocation(dto.getLocation());
        warehouse.setCapacity(dto.getCapacity());
        warehouse.setStatus(dto.getStatus());
        warehouse.setManager(dto.getManager());
        warehouse.setContact(dto.getContact());
        warehouse.setPhone(dto.getPhone());
        warehouse.setContactPhone(dto.getContactPhone());
        warehouse.setContactEmail(dto.getContactEmail());
        warehouse.setUpdatedBy(dto.getUpdatedBy() != null ? dto.getUpdatedBy() : "admin");
        warehouse.setRemarks(dto.getRemarks());
    }
}