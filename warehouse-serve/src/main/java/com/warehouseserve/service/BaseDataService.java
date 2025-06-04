package com.warehouseserve.service;

import com.warehouseserve.dto.BaseDataDTO;
import com.warehouseserve.exception.DataOperationException;

import java.util.List;

public interface BaseDataService {
    // 分类管理
    List<BaseDataDTO> getCategories(String type) throws DataOperationException;
    BaseDataDTO createCategory(BaseDataDTO category) throws DataOperationException;
    BaseDataDTO updateCategory(Long id, BaseDataDTO category) throws DataOperationException;
    void deleteCategory(Long id) throws DataOperationException;

    // 单位管理
    List<BaseDataDTO> getUnits(String type) throws DataOperationException;
    BaseDataDTO createUnit(BaseDataDTO unit) throws DataOperationException;
    BaseDataDTO updateUnit(Long id, BaseDataDTO unit) throws DataOperationException;
    void deleteUnit(Long id) throws DataOperationException;

    // 状态管理
    List<BaseDataDTO> getStatus(String type) throws DataOperationException;
    BaseDataDTO createStatus(BaseDataDTO status) throws DataOperationException;
    BaseDataDTO updateStatus(Long id, BaseDataDTO status) throws DataOperationException;
    void deleteStatus(Long id) throws DataOperationException;
} 