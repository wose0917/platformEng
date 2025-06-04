package com.warehouseserve.service;

import com.warehouseserve.dto.InboundDTO;
import com.warehouseserve.exception.DataOperationException;
import java.time.LocalDateTime;
import java.util.List;

public interface InboundService {
    List<InboundDTO> getAllInbounds() throws DataOperationException;
    InboundDTO getInboundById(Long id) throws DataOperationException;
    List<InboundDTO> getInboundsByWarehouseId(Long warehouseId) throws DataOperationException;
    List<InboundDTO> getInboundsByStatus(String status) throws DataOperationException;
    List<InboundDTO> getInboundsByApprovalStatus(String approvalStatus) throws DataOperationException;
    List<InboundDTO> getInboundsByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws DataOperationException;
    List<InboundDTO> getInboundsBySupplier(String supplier) throws DataOperationException;
    List<InboundDTO> getInboundsByCreator(String createdBy) throws DataOperationException;
    
    InboundDTO createInbound(InboundDTO inboundDTO) throws DataOperationException;
    InboundDTO updateInbound(Long id, InboundDTO inboundDTO) throws DataOperationException;
    void deleteInbound(Long id) throws DataOperationException;
    
    void updateInboundStatus(Long id, String status) throws DataOperationException;
    void updateApprovalStatus(Long id, String approvalStatus, String approvedBy) throws DataOperationException;
    void submitForApproval(Long id) throws DataOperationException;
    void cancelInbound(Long id) throws DataOperationException;
}
