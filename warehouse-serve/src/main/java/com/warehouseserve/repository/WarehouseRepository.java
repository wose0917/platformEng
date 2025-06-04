package com.warehouseserve.repository;

import com.warehouseserve.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    List<Warehouse> findByStatus(String status);
    List<Warehouse> findByLocation(String location);
    List<Warehouse> findByManager(String manager);
    List<Warehouse> findByCapacityGreaterThanEqual(Integer capacity);
    List<Warehouse> findByUsedCapacityLessThanEqual(Integer usedCapacity);

    @Query("SELECT w FROM Warehouse w WHERE (w.capacity - w.usedCapacity) >= :requiredSpace")
    List<Warehouse> findByAvailableSpaceGreaterThanEqual(@Param("requiredSpace") Integer requiredSpace);
}