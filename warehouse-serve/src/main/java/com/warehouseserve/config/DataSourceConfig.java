package com.warehouseserve.config;

import com.warehouseserve.dto.WarehouseDTO;
import com.warehouseserve.service.WarehouseService;
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
public class DataSourceConfig {

    @Autowired
    private DatabaseDataSource databaseDataSource;

    @Autowired
    private ExcelDataSource excelDataSource;

    @Bean
    @Primary
    public WarehouseService warehouseService() {
        return new WarehouseService() {
            @Override
            public List<WarehouseDTO> getAllWarehouses() {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.getAllWarehouses();
                    } else {
                        return databaseDataSource.getAllWarehouses();
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching warehouses: " + e.getMessage());
                    return getTestWarehouses();
                }
            }

            @Override
            public WarehouseDTO getWarehouseById(Long id) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.getWarehouseById(id);
                    } else {
                        return databaseDataSource.getWarehouseById(id);
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching warehouse by id: " + e.getMessage());
                    return getTestWarehouses().stream()
                            .filter(w -> w.getId().equals(id))
                            .findFirst()
                            .orElse(null);
                }
            }

            @Override
            public WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.createWarehouse(warehouseDTO);
                    } else {
                        return databaseDataSource.createWarehouse(warehouseDTO);
                    }
                } catch (Exception e) {
                    System.err.println("Error creating warehouse: " + e.getMessage());
                    return warehouseDTO;
                }
            }

            @Override
            public void deleteWarehouse(Long id) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        excelDataSource.deleteWarehouse(id);
                    } else {
                        databaseDataSource.deleteWarehouse(id);
                    }
                } catch (Exception e) {
                    System.err.println("Error deleting warehouse: " + e.getMessage());
                }
            }

            @Override
            public List<WarehouseDTO> getWarehousesByStatus(String status) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.getWarehousesByStatus(status);
                    } else {
                        return databaseDataSource.getWarehousesByStatus(status);
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching warehouses by status: " + e.getMessage());
                    return getTestWarehouses();
                }
            }

            @Override
            public void updateWarehouseContact(Long id, String contact, String phone) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        excelDataSource.updateWarehouseContact(id, contact, phone);
                    } else {
                        databaseDataSource.updateWarehouseContact(id, contact, phone);
                    }
                } catch (Exception e) {
                    System.err.println("Error updating warehouse contact: " + e.getMessage());
                }
            }

            @Override
            public List<WarehouseDTO> getWarehousesByManager(String manager) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.getWarehousesByManager(manager);
                    } else {
                        return databaseDataSource.getWarehousesByManager(manager);
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching warehouses by manager: " + e.getMessage());
                    return getTestWarehouses();
                }
            }

            @Override
            public void increaseUsedCapacity(Long id, Integer amount) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        excelDataSource.increaseUsedCapacity(id, amount);
                    } else {
                        databaseDataSource.increaseUsedCapacity(id, amount);
                    }
                } catch (Exception e) {
                    System.err.println("Error increasing used capacity: " + e.getMessage());
                }
            }

            @Override
            public void decreaseUsedCapacity(Long id, Integer amount) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        excelDataSource.decreaseUsedCapacity(id, amount);
                    } else {
                        databaseDataSource.decreaseUsedCapacity(id, amount);
                    }
                } catch (Exception e) {
                    System.err.println("Error decreasing used capacity: " + e.getMessage());
                }
            }

            @Override
            public void updateWarehouseCapacity(Long id, Integer capacity) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        excelDataSource.updateWarehouseCapacity(id, capacity);
                    } else {
                        databaseDataSource.updateWarehouseCapacity(id, capacity);
                    }
                } catch (Exception e) {
                    System.err.println("Error updating warehouse capacity: " + e.getMessage());
                }
            }

            @Override
            public WarehouseDTO updateWarehouse(Long id, WarehouseDTO warehouseDTO) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.updateWarehouse(id, warehouseDTO);
                    } else {
                        return databaseDataSource.updateWarehouse(id, warehouseDTO);
                    }
                } catch (Exception e) {
                    System.err.println("Error updating warehouse: " + e.getMessage());
                    return warehouseDTO;
                }
            }

            @Override
            public List<WarehouseDTO> getWarehousesByCapacity(Integer capacity) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.getWarehousesByCapacity(capacity);
                    } else {
                        return databaseDataSource.getWarehousesByCapacity(capacity);
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching warehouses by capacity: " + e.getMessage());
                    return getTestWarehouses();
                }
            }

            @Override
            public boolean checkAvailableSpace(Long id, Integer requiredSpace) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.checkAvailableSpace(id, requiredSpace);
                    } else {
                        return databaseDataSource.checkAvailableSpace(id, requiredSpace);
                    }
                } catch (Exception e) {
                    System.err.println("Error checking available space: " + e.getMessage());
                    return true;
                }
            }

            @Override
            public List<WarehouseDTO> getWarehousesByAvailableSpace(Integer requiredSpace) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.getWarehousesByAvailableSpace(requiredSpace);
                    } else {
                        return databaseDataSource.getWarehousesByAvailableSpace(requiredSpace);
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching warehouses by available space: " + e.getMessage());
                    return getTestWarehouses();
                }
            }

            @Override
            public void updateWarehouseStatus(Long id, String status) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        excelDataSource.updateWarehouseStatus(id, status);
                    } else {
                        databaseDataSource.updateWarehouseStatus(id, status);
                    }
                } catch (Exception e) {
                    System.err.println("Error updating warehouse status: " + e.getMessage());
                }
            }

            @Override
            public List<WarehouseDTO> getWarehousesByLocation(String location) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        return excelDataSource.getWarehousesByLocation(location);
                    } else {
                        return databaseDataSource.getWarehousesByLocation(location);
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching warehouses by location: " + e.getMessage());
                    return getTestWarehouses();
                }
            }

            @Override
            public void updateWarehouseManager(Long id, String manager) {
                try {
                    String source = getRequestSource();
                    if ("EXCEL".equalsIgnoreCase(source)) {
                        excelDataSource.updateWarehouseManager(id, manager);
                    } else {
                        databaseDataSource.updateWarehouseManager(id, manager);
                    }
                } catch (Exception e) {
                    System.err.println("Error updating warehouse manager: " + e.getMessage());
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

    private List<WarehouseDTO> getTestWarehouses() {
        List<WarehouseDTO> testWarehouses = new ArrayList<>();
        
        // 数据库测试数据
        WarehouseDTO warehouse1 = new WarehouseDTO();
        warehouse1.setId(1L);
        warehouse1.setName("数据库仓库1");
        warehouse1.setLocation("北京市海淀区");
        warehouse1.setCapacity(1500);
        warehouse1.setUsedCapacity(800);
        warehouse1.setManager("王五");
        warehouse1.setStatus("正常");
        warehouse1.setContact("王五");
        warehouse1.setPhone("13800138001");
        warehouse1.setContactPhone("13800138001");
        warehouse1.setContactEmail("wangwu@example.com");
        warehouse1.setCreatedBy("admin");
        warehouse1.setUpdatedBy("admin");
        warehouse1.setCreateTime(LocalDateTime.now());
        warehouse1.setUpdateTime(LocalDateTime.now());
        warehouse1.setRemarks("数据库测试数据1");
        testWarehouses.add(warehouse1);

        WarehouseDTO warehouse2 = new WarehouseDTO();
        warehouse2.setId(2L);
        warehouse2.setName("数据库仓库2");
        warehouse2.setLocation("上海市徐汇区");
        warehouse2.setCapacity(2500);
        warehouse2.setUsedCapacity(1200);
        warehouse2.setManager("赵六");
        warehouse2.setStatus("正常");
        warehouse2.setContact("赵六");
        warehouse2.setPhone("13800138002");
        warehouse2.setContactPhone("13800138002");
        warehouse2.setContactEmail("zhaoliu@example.com");
        warehouse2.setCreatedBy("admin");
        warehouse2.setUpdatedBy("admin");
        warehouse2.setCreateTime(LocalDateTime.now());
        warehouse2.setUpdateTime(LocalDateTime.now());
        warehouse2.setRemarks("数据库测试数据2");
        testWarehouses.add(warehouse2);

        return testWarehouses;
    }
}