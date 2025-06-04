package com.warehouseserve.repository;

import com.warehouseserve.entity.InboundRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InboundRecordRepository extends JpaRepository<InboundRecord, Long> {
    List<InboundRecord> findByInboundId(Long inboundId);
    List<InboundRecord> findByWarehouseId(Long warehouseId);
    List<InboundRecord> findByStatus(String status);
} 