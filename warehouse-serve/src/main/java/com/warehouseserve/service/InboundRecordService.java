package com.warehouseserve.service;

import com.warehouseserve.dto.InboundRecordDTO;
import com.warehouseserve.exception.DataOperationException;
import java.util.List;

public interface InboundRecordService {
    List<InboundRecordDTO> getAllInboundRecords() throws DataOperationException;
    InboundRecordDTO getInboundRecordById(Long id) throws DataOperationException;
    List<InboundRecordDTO> getInboundRecordsByInboundId(Long inboundId) throws DataOperationException;
    List<InboundRecordDTO> getInboundRecordsByWarehouseId(Long warehouseId) throws DataOperationException;
    List<InboundRecordDTO> getInboundRecordsByStatus(String status) throws DataOperationException;
    InboundRecordDTO createInboundRecord(InboundRecordDTO inboundRecordDTO) throws DataOperationException;
    InboundRecordDTO updateInboundRecord(Long id, InboundRecordDTO inboundRecordDTO) throws DataOperationException;
    void deleteInboundRecord(Long id) throws DataOperationException;
    void updateInboundRecordStatus(Long id, String status) throws DataOperationException;
} 