package com.warehouseserve.config;

import com.warehouseserve.dto.InboundDTO;
import com.warehouseserve.service.InboundService;
import com.warehouseserve.service.datasource.DatabaseDataSource;
import com.warehouseserve.service.datasource.ExcelDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class InboundServiceConfig {

    @Autowired
    private DatabaseDataSource databaseDataSource;

    @Autowired
    private ExcelDataSource excelDataSource;

    @Bean
    @Primary
    public InboundService inboundService() {
        return new InboundService() {
            @Override
            public List<InboundDTO> getAllInbounds() {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.getAllInbounds();
                    } else {
                        return databaseDataSource.getAllInbounds();
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching inbounds: " + e.getMessage());
                    return getTestInbounds();
                }
            }

            @Override
            public InboundDTO getInboundById(Long id) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.getInboundById(id);
                    } else {
                        return databaseDataSource.getInboundById(id);
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching inbound by id: " + e.getMessage());
                    return getTestInbounds().stream()
                            .filter(i -> i.getId().equals(id))
                            .findFirst()
                            .orElse(null);
                }
            }

            @Override
            public InboundDTO createInbound(InboundDTO inboundDTO) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.createInbound(inboundDTO);
                    } else {
                        return databaseDataSource.createInbound(inboundDTO);
                    }
                } catch (Exception e) {
                    System.err.println("Error creating inbound: " + e.getMessage());
                    return inboundDTO;
                }
            }

            @Override
            public void deleteInbound(Long id) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        excelDataSource.deleteInbound(id);
                    } else {
                        databaseDataSource.deleteInbound(id);
                    }
                } catch (Exception e) {
                    System.err.println("Error deleting inbound: " + e.getMessage());
                }
            }

            @Override
            public void updateApprovalStatus(Long id, String status, String approver) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        excelDataSource.updateApprovalStatus(id, status, approver);
                    } else {
                        databaseDataSource.updateApprovalStatus(id, status, approver);
                    }
                } catch (Exception e) {
                    System.err.println("Error updating approval status: " + e.getMessage());
                }
            }

            @Override
            public List<InboundDTO> getInboundsByStatus(String status) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.getInboundsByStatus(status);
                    } else {
                        return databaseDataSource.getInboundsByStatus(status);
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching inbounds by status: " + e.getMessage());
                    return getTestInbounds();
                }
            }

            @Override
            public List<InboundDTO> getInboundsByApprovalStatus(String status) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.getInboundsByApprovalStatus(status);
                    } else {
                        return databaseDataSource.getInboundsByApprovalStatus(status);
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching inbounds by approval status: " + e.getMessage());
                    return getTestInbounds();
                }
            }

            @Override
            public List<InboundDTO> getInboundsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.getInboundsByDateRange(startDate, endDate);
                    } else {
                        return databaseDataSource.getInboundsByDateRange(startDate, endDate);
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching inbounds by date range: " + e.getMessage());
                    return getTestInbounds();
                }
            }

            @Override
            public void updateInboundStatus(Long id, String status) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        excelDataSource.updateInboundStatus(id, status);
                    } else {
                        databaseDataSource.updateInboundStatus(id, status);
                    }
                } catch (Exception e) {
                    System.err.println("Error updating inbound status: " + e.getMessage());
                }
            }

            @Override
            public List<InboundDTO> getInboundsBySupplier(String supplier) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.getInboundsBySupplier(supplier);
                    } else {
                        return databaseDataSource.getInboundsBySupplier(supplier);
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching inbounds by supplier: " + e.getMessage());
                    return getTestInbounds();
                }
            }

            @Override
            public void submitForApproval(Long id) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        excelDataSource.submitForApproval(id);
                    } else {
                        databaseDataSource.submitForApproval(id);
                    }
                } catch (Exception e) {
                    System.err.println("Error submitting for approval: " + e.getMessage());
                }
            }

            @Override
            public void cancelInbound(Long id) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        excelDataSource.cancelInbound(id);
                    } else {
                        databaseDataSource.cancelInbound(id);
                    }
                } catch (Exception e) {
                    System.err.println("Error canceling inbound: " + e.getMessage());
                }
            }

            @Override
            public List<InboundDTO> getInboundsByWarehouseId(Long warehouseId) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.getInboundsByWarehouseId(warehouseId);
                    } else {
                        return databaseDataSource.getInboundsByWarehouseId(warehouseId);
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching inbounds by warehouse id: " + e.getMessage());
                    return getTestInbounds();
                }
            }

            @Override
            public List<InboundDTO> getInboundsByCreator(String creator) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.getInboundsByCreator(creator);
                    } else {
                        return databaseDataSource.getInboundsByCreator(creator);
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching inbounds by creator: " + e.getMessage());
                    return getTestInbounds();
                }
            }

            @Override
            public InboundDTO updateInbound(Long id, InboundDTO inboundDTO) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.updateInbound(id, inboundDTO);
                    } else {
                        return databaseDataSource.updateInbound(id, inboundDTO);
                    }
                } catch (Exception e) {
                    System.err.println("Error updating inbound: " + e.getMessage());
                    return inboundDTO;
                }
            }
        };
    }

    private String getRequestSource() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String source = request.getParameter("source");
            return source != null ? source : "DATABASE";
        }
        return "DATABASE";
    }

    private List<InboundDTO> getTestInbounds() {
        List<InboundDTO> testInbounds = new ArrayList<>();
        
        // 数据库测试数据
        InboundDTO inbound1 = new InboundDTO();
        inbound1.setId(1L);
        inbound1.setWarehouseId(1L);
        inbound1.setInboundNumber("DB001");
        inbound1.setSupplier("数据库供应商1");
        inbound1.setInboundDate(LocalDateTime.now());
        inbound1.setStatus("待入库");
        inbound1.setApprovalStatus("待审批");
        inbound1.setCreatedBy("admin");
        inbound1.setCreateTime(LocalDateTime.now());
        inbound1.setRemarks("数据库测试数据1");
        testInbounds.add(inbound1);

        InboundDTO inbound2 = new InboundDTO();
        inbound2.setId(2L);
        inbound2.setWarehouseId(2L);
        inbound2.setInboundNumber("DB002");
        inbound2.setSupplier("数据库供应商2");
        inbound2.setInboundDate(LocalDateTime.now());
        inbound2.setStatus("已入库");
        inbound2.setApprovalStatus("已审批");
        inbound2.setCreatedBy("admin");
        inbound2.setCreateTime(LocalDateTime.now());
        inbound2.setRemarks("数据库测试数据2");
        testInbounds.add(inbound2);

        return testInbounds;
    }
} 