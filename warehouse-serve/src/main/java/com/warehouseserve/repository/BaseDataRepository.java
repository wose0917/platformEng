package com.warehouseserve.repository;

import com.warehouseserve.entity.BaseData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseDataRepository extends JpaRepository<BaseData, Long> {
    List<BaseData> findByType(String type);
    List<BaseData> findByTypeAndEnabled(String type, Boolean enabled);
    boolean existsByCodeAndType(String code, String type);
    boolean existsByCodeAndTypeAndIdNot(String code, String type, Long id);
} 