package com.warehouseserve.service;

import com.warehouseserve.dto.WarehouseDTO;
import com.warehouseserve.exception.DataOperationException;

import java.util.List;

public interface WarehouseService {
    List<WarehouseDTO> getAllWarehouses() throws DataOperationException;
    WarehouseDTO getWarehouseById(Long id) throws DataOperationException;
    List<WarehouseDTO> getWarehousesByStatus(String status) throws DataOperationException;
    List<WarehouseDTO> getWarehousesByLocation(String location) throws DataOperationException;
    List<WarehouseDTO> getWarehousesByManager(String manager) throws DataOperationException;
    List<WarehouseDTO> getWarehousesByCapacity(Integer minCapacity) throws DataOperationException;
    List<WarehouseDTO> getWarehousesByAvailableSpace(Integer minAvailableSpace) throws DataOperationException;
    List<WarehouseDTO> getWarehousesByName(String name) throws DataOperationException;
    
    WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO) throws DataOperationException;
    WarehouseDTO updateWarehouse(Long id, WarehouseDTO warehouseDTO) throws DataOperationException;
    void deleteWarehouse(Long id) throws DataOperationException;
    
    void updateWarehouseStatus(Long id, String status) throws DataOperationException;
    void updateWarehouseCapacity(Long id, Integer capacity) throws DataOperationException;
    void updateWarehouseManager(Long id, String manager) throws DataOperationException;
    void updateWarehouseContact(Long id, String phone, String email) throws DataOperationException;
    
    // 库存管理相关方法
    void increaseUsedCapacity(Long id, Integer amount) throws DataOperationException;
    void decreaseUsedCapacity(Long id, Integer amount) throws DataOperationException;
    boolean checkAvailableSpace(Long id, Integer requiredSpace) throws DataOperationException;
}