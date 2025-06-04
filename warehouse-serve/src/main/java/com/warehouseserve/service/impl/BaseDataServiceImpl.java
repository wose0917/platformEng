package com.warehouseserve.service.impl;

import com.warehouseserve.dto.BaseDataDTO;
import com.warehouseserve.entity.BaseData;
import com.warehouseserve.exception.DataOperationException;
import com.warehouseserve.repository.BaseDataRepository;
import com.warehouseserve.service.BaseDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaseDataServiceImpl implements BaseDataService {

    private final BaseDataRepository baseDataRepository;

    @Override
    public List<BaseDataDTO> getCategories(String type) throws DataOperationException {
        try {
            List<BaseData> categories = type != null ? 
                    baseDataRepository.findByTypeAndEnabled(type, true) :
                    baseDataRepository.findByTypeAndEnabled("CATEGORY", true);
            return categories.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get categories", e);
        }
    }

    @Override
    @Transactional
    public BaseDataDTO createCategory(BaseDataDTO category) throws DataOperationException {
        validateBaseData(category, "CATEGORY");
        try {
            if (baseDataRepository.existsByCodeAndType(category.getCode(), "CATEGORY")) {
                throw new DataOperationException("Category code already exists: " + category.getCode());
            }

            BaseData baseData = convertToEntity(category);
            baseData.setType("CATEGORY");
            baseData.setCreateTime(LocalDateTime.now());
            baseData.setUpdateTime(LocalDateTime.now());
            
            return convertToDTO(baseDataRepository.save(baseData));
        } catch (DataOperationException e) {
            throw e;
        } catch (Exception e) {
            throw new DataOperationException("Failed to create category", e);
        }
    }

    @Override
    @Transactional
    public BaseDataDTO updateCategory(Long id, BaseDataDTO category) throws DataOperationException {
        validateBaseData(category, "CATEGORY");
        try {
            BaseData existingCategory = baseDataRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Category not found with id: " + id));

            if (!"CATEGORY".equals(existingCategory.getType())) {
                throw new DataOperationException("Invalid category type");
            }

            if (baseDataRepository.existsByCodeAndTypeAndIdNot(category.getCode(), "CATEGORY", id)) {
                throw new DataOperationException("Category code already exists: " + category.getCode());
            }

            updateBaseDataFromDTO(existingCategory, category);
            existingCategory.setUpdateTime(LocalDateTime.now());
            
            return convertToDTO(baseDataRepository.save(existingCategory));
        } catch (DataOperationException e) {
            throw e;
        } catch (Exception e) {
            throw new DataOperationException("Failed to update category", e);
        }
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) throws DataOperationException {
        try {
            BaseData category = baseDataRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Category not found with id: " + id));

            if (!"CATEGORY".equals(category.getType())) {
                throw new DataOperationException("Invalid category type");
            }

            category.setEnabled(false);
            category.setUpdateTime(LocalDateTime.now());
            baseDataRepository.save(category);
        } catch (DataOperationException e) {
            throw e;
        } catch (Exception e) {
            throw new DataOperationException("Failed to delete category", e);
        }
    }

    @Override
    public List<BaseDataDTO> getUnits(String type) throws DataOperationException {
        try {
            List<BaseData> units = type != null ? 
                    baseDataRepository.findByTypeAndEnabled(type, true) :
                    baseDataRepository.findByTypeAndEnabled("UNIT", true);
            return units.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get units", e);
        }
    }

    @Override
    @Transactional
    public BaseDataDTO createUnit(BaseDataDTO unit) throws DataOperationException {
        validateBaseData(unit, "UNIT");
        try {
            if (baseDataRepository.existsByCodeAndType(unit.getCode(), "UNIT")) {
                throw new DataOperationException("Unit code already exists: " + unit.getCode());
            }

            BaseData baseData = convertToEntity(unit);
            baseData.setType("UNIT");
            baseData.setCreateTime(LocalDateTime.now());
            baseData.setUpdateTime(LocalDateTime.now());
            
            return convertToDTO(baseDataRepository.save(baseData));
        } catch (DataOperationException e) {
            throw e;
        } catch (Exception e) {
            throw new DataOperationException("Failed to create unit", e);
        }
    }

    @Override
    @Transactional
    public BaseDataDTO updateUnit(Long id, BaseDataDTO unit) throws DataOperationException {
        validateBaseData(unit, "UNIT");
        try {
            BaseData existingUnit = baseDataRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Unit not found with id: " + id));

            if (!"UNIT".equals(existingUnit.getType())) {
                throw new DataOperationException("Invalid unit type");
            }

            if (baseDataRepository.existsByCodeAndTypeAndIdNot(unit.getCode(), "UNIT", id)) {
                throw new DataOperationException("Unit code already exists: " + unit.getCode());
            }

            updateBaseDataFromDTO(existingUnit, unit);
            existingUnit.setUpdateTime(LocalDateTime.now());
            
            return convertToDTO(baseDataRepository.save(existingUnit));
        } catch (DataOperationException e) {
            throw e;
        } catch (Exception e) {
            throw new DataOperationException("Failed to update unit", e);
        }
    }

    @Override
    @Transactional
    public void deleteUnit(Long id) throws DataOperationException {
        try {
            BaseData unit = baseDataRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Unit not found with id: " + id));

            if (!"UNIT".equals(unit.getType())) {
                throw new DataOperationException("Invalid unit type");
            }

            unit.setEnabled(false);
            unit.setUpdateTime(LocalDateTime.now());
            baseDataRepository.save(unit);
        } catch (DataOperationException e) {
            throw e;
        } catch (Exception e) {
            throw new DataOperationException("Failed to delete unit", e);
        }
    }

    @Override
    public List<BaseDataDTO> getStatus(String type) throws DataOperationException {
        try {
            List<BaseData> statuses = type != null ? 
                    baseDataRepository.findByTypeAndEnabled(type, true) :
                    baseDataRepository.findByTypeAndEnabled("STATUS", true);
            return statuses.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataOperationException("Failed to get statuses", e);
        }
    }

    @Override
    @Transactional
    public BaseDataDTO createStatus(BaseDataDTO status) throws DataOperationException {
        validateBaseData(status, "STATUS");
        try {
            if (baseDataRepository.existsByCodeAndType(status.getCode(), "STATUS")) {
                throw new DataOperationException("Status code already exists: " + status.getCode());
            }

            BaseData baseData = convertToEntity(status);
            baseData.setType("STATUS");
            baseData.setCreateTime(LocalDateTime.now());
            baseData.setUpdateTime(LocalDateTime.now());
            
            return convertToDTO(baseDataRepository.save(baseData));
        } catch (DataOperationException e) {
            throw e;
        } catch (Exception e) {
            throw new DataOperationException("Failed to create status", e);
        }
    }

    @Override
    @Transactional
    public BaseDataDTO updateStatus(Long id, BaseDataDTO status) throws DataOperationException {
        validateBaseData(status, "STATUS");
        try {
            BaseData existingStatus = baseDataRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Status not found with id: " + id));

            if (!"STATUS".equals(existingStatus.getType())) {
                throw new DataOperationException("Invalid status type");
            }

            if (baseDataRepository.existsByCodeAndTypeAndIdNot(status.getCode(), "STATUS", id)) {
                throw new DataOperationException("Status code already exists: " + status.getCode());
            }

            updateBaseDataFromDTO(existingStatus, status);
            existingStatus.setUpdateTime(LocalDateTime.now());
            
            return convertToDTO(baseDataRepository.save(existingStatus));
        } catch (DataOperationException e) {
            throw e;
        } catch (Exception e) {
            throw new DataOperationException("Failed to update status", e);
        }
    }

    @Override
    @Transactional
    public void deleteStatus(Long id) throws DataOperationException {
        try {
            BaseData status = baseDataRepository.findById(id)
                    .orElseThrow(() -> new DataOperationException("Status not found with id: " + id));

            if (!"STATUS".equals(status.getType())) {
                throw new DataOperationException("Invalid status type");
            }

            status.setEnabled(false);
            status.setUpdateTime(LocalDateTime.now());
            baseDataRepository.save(status);
        } catch (DataOperationException e) {
            throw e;
        } catch (Exception e) {
            throw new DataOperationException("Failed to delete status", e);
        }
    }

    private void validateBaseData(BaseDataDTO dto, String type) throws DataOperationException {
        if (dto == null) {
            throw new DataOperationException("Data cannot be null");
        }
        if (!StringUtils.hasText(dto.getCode())) {
            throw new DataOperationException("Code cannot be empty");
        }
        if (!StringUtils.hasText(dto.getName())) {
            throw new DataOperationException("Name cannot be empty");
        }
        if (dto.getCode().length() > 50) {
            throw new DataOperationException("Code length cannot exceed 50 characters");
        }
        if (dto.getName().length() > 100) {
            throw new DataOperationException("Name length cannot exceed 100 characters");
        }
        if (dto.getDescription() != null && dto.getDescription().length() > 500) {
            throw new DataOperationException("Description length cannot exceed 500 characters");
        }
    }

    private BaseDataDTO convertToDTO(BaseData baseData) {
        BaseDataDTO dto = new BaseDataDTO();
        dto.setId(baseData.getId());
        dto.setCode(baseData.getCode());
        dto.setName(baseData.getName());
        dto.setType(baseData.getType());
        dto.setDescription(baseData.getDescription());
        dto.setEnabled(baseData.getEnabled());
        dto.setSortOrder(baseData.getSortOrder());
        dto.setCreatedBy(baseData.getCreatedBy());
        dto.setUpdatedBy(baseData.getUpdatedBy());
        return dto;
    }

    private BaseData convertToEntity(BaseDataDTO dto) {
        BaseData baseData = new BaseData();
        baseData.setCode(dto.getCode());
        baseData.setName(dto.getName());
        baseData.setDescription(dto.getDescription());
        baseData.setEnabled(dto.getEnabled() != null ? dto.getEnabled() : true);
        baseData.setSortOrder(dto.getSortOrder());
        baseData.setCreatedBy(dto.getCreatedBy());
        baseData.setUpdatedBy(dto.getUpdatedBy());
        return baseData;
    }

    private void updateBaseDataFromDTO(BaseData baseData, BaseDataDTO dto) {
        baseData.setCode(dto.getCode());
        baseData.setName(dto.getName());
        baseData.setDescription(dto.getDescription());
        baseData.setEnabled(dto.getEnabled() != null ? dto.getEnabled() : baseData.getEnabled());
        baseData.setSortOrder(dto.getSortOrder());
        baseData.setUpdatedBy(dto.getUpdatedBy());
    }
} 