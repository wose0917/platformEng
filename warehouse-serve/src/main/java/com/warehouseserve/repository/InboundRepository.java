package com.warehouseserve.repository;

import com.warehouseserve.entity.Inbound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InboundRepository extends JpaRepository<Inbound, Long> {
    List<Inbound> findByWarehouseId(Long warehouseId);
    List<Inbound> findByStatus(String status);
    List<Inbound> findByApprovalStatus(String approvalStatus);
    List<Inbound> findByInboundDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Inbound> findBySupplier(String supplier);
    List<Inbound> findByCreatedBy(String createdBy);
    Optional<Inbound> findByInboundNumber(String inboundNumber);
    List<Inbound> findByWarehouseIdAndStatus(Long warehouseId, String status);
    List<Inbound> findByWarehouseIdAndApprovalStatus(Long warehouseId, String approvalStatus);
    List<Inbound> findByStatusAndApprovalStatus(String status, String approvalStatus);
    boolean existsByInboundNumber(String inboundNumber);
    boolean existsByInboundNumberAndIdNot(String inboundNumber, Long id);
}
