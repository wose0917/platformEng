package com.warehouseserve.service.datasource;

import com.warehouseserve.dto.InboundDTO;
import com.warehouseserve.dto.WarehouseDTO;
import com.warehouseserve.entity.Inbound;
import com.warehouseserve.entity.Warehouse;
import com.warehouseserve.service.InboundService;
import com.warehouseserve.service.WarehouseService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelDataSource implements WarehouseService, InboundService {

    private static final String EXCEL_FILE_PATH = "warehouse_data.xlsx";

    @Override
    public List<WarehouseDTO> getAllWarehouses() {
        List<WarehouseDTO> warehouses = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet("仓库信息");
            if (sheet != null) {
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        WarehouseDTO warehouse = new WarehouseDTO();
                        warehouse.setId((long) row.getCell(0).getNumericCellValue());
                        warehouse.setName(row.getCell(1).getStringCellValue());
                        warehouse.setLocation(row.getCell(2).getStringCellValue());
                        warehouse.setCapacity((int) row.getCell(3).getNumericCellValue());
                        warehouse.setUsedCapacity((int) row.getCell(4).getNumericCellValue());
                        warehouse.setManager(row.getCell(5).getStringCellValue());
                        warehouse.setStatus(row.getCell(6).getStringCellValue());
                        warehouse.setContact(row.getCell(7).getStringCellValue());
                        warehouse.setPhone(row.getCell(8).getStringCellValue());
                        warehouse.setContactPhone(row.getCell(9).getStringCellValue());
                        warehouse.setContactEmail(row.getCell(10).getStringCellValue());
                        warehouse.setRemarks(row.getCell(11).getStringCellValue());
                        warehouses.add(warehouse);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return warehouses;
    }

    @Override
    public WarehouseDTO getWarehouseById(Long id) {
        return getAllWarehouses().stream()
                .filter(w -> w.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO) {
        // Excel 数据源不支持创建操作
        return null;
    }

    @Override
    public void deleteWarehouse(Long id) {
        // Excel 数据源不支持删除操作
    }

    @Override
    public List<InboundDTO> getAllInbounds() {
        List<InboundDTO> inbounds = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet("入库记录");
            if (sheet != null) {
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        InboundDTO inbound = new InboundDTO();
                        inbound.setId((long) row.getCell(0).getNumericCellValue());
                        inbound.setWarehouseId((long) row.getCell(1).getNumericCellValue());
                        inbound.setInboundNumber(row.getCell(2).getStringCellValue());
                        inbound.setSupplier(row.getCell(3).getStringCellValue());
                        inbound.setInboundDate(row.getCell(4).getLocalDateTimeCellValue());
                        inbound.setStatus(row.getCell(5).getStringCellValue());
                        inbound.setApprovalStatus(row.getCell(6).getStringCellValue());
                        inbound.setCreatedBy(row.getCell(7).getStringCellValue());
                        inbound.setCreateTime(row.getCell(8).getLocalDateTimeCellValue());
                        inbounds.add(inbound);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inbounds;
    }

    // 实现其他必要的方法，但都返回空值或默认值，因为 Excel 数据源是只读的
    @Override
    public InboundDTO getInboundById(Long id) {
        return null;
    }

    @Override
    public InboundDTO createInbound(InboundDTO inboundDTO) {
        return null;
    }

    @Override
    public void deleteInbound(Long id) {
    }

    @Override
    public void updateApprovalStatus(Long id, String status, String approver) {
    }

    @Override
    public List<InboundDTO> getInboundsByStatus(String status) {
        return new ArrayList<>();
    }

    @Override
    public List<InboundDTO> getInboundsByApprovalStatus(String status) {
        return new ArrayList<>();
    }

    @Override
    public List<InboundDTO> getInboundsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return new ArrayList<>();
    }

    @Override
    public void updateInboundStatus(Long id, String status) {
    }

    @Override
    public List<InboundDTO> getInboundsBySupplier(String supplier) {
        return new ArrayList<>();
    }

    @Override
    public void submitForApproval(Long id) {
    }

    @Override
    public void cancelInbound(Long id) {
    }

    @Override
    public List<InboundDTO> getInboundsByWarehouseId(Long warehouseId) {
        return new ArrayList<>();
    }

    @Override
    public List<InboundDTO> getInboundsByCreator(String creator) {
        return new ArrayList<>();
    }

    @Override
    public InboundDTO updateInbound(Long id, InboundDTO inboundDTO) {
        return null;
    }

    @Override
    public List<WarehouseDTO> getWarehousesByStatus(String status) {
        return new ArrayList<>();
    }

    @Override
    public void updateWarehouseContact(Long id, String contact, String phone) {
    }

    @Override
    public List<WarehouseDTO> getWarehousesByManager(String manager) {
        return new ArrayList<>();
    }

    @Override
    public void increaseUsedCapacity(Long id, Integer amount) {
    }

    @Override
    public void decreaseUsedCapacity(Long id, Integer amount) {
    }

    @Override
    public void updateWarehouseCapacity(Long id, Integer capacity) {
    }

    @Override
    public WarehouseDTO updateWarehouse(Long id, WarehouseDTO warehouseDTO) {
        return null;
    }

    @Override
    public List<WarehouseDTO> getWarehousesByCapacity(Integer capacity) {
        return new ArrayList<>();
    }

    @Override
    public boolean checkAvailableSpace(Long id, Integer requiredSpace) {
        return false;
    }

    @Override
    public List<WarehouseDTO> getWarehousesByAvailableSpace(Integer requiredSpace) {
        return new ArrayList<>();
    }

    @Override
    public void updateWarehouseStatus(Long id, String status) {
    }

    @Override
    public List<WarehouseDTO> getWarehousesByLocation(String location) {
        return new ArrayList<>();
    }

    @Override
    public void updateWarehouseManager(Long id, String manager) {
    }

    private List<WarehouseDTO> getTestWarehouses() {
        List<WarehouseDTO> testWarehouses = new ArrayList<>();
        
        // Excel测试数据
        WarehouseDTO warehouse1 = new WarehouseDTO();
        warehouse1.setId(1L);
        warehouse1.setName("Excel仓库1");
        warehouse1.setLocation("北京市朝阳区");
        warehouse1.setCapacity(1000);
        warehouse1.setUsedCapacity(500);
        warehouse1.setManager("张三");
        warehouse1.setStatus("正常");
        warehouse1.setContact("张三");
        warehouse1.setPhone("13900139001");
        warehouse1.setContactPhone("13900139001");
        warehouse1.setContactEmail("zhangsan@example.com");
        warehouse1.setCreatedBy("admin");
        warehouse1.setUpdatedBy("admin");
        warehouse1.setCreateTime(LocalDateTime.now());
        warehouse1.setUpdateTime(LocalDateTime.now());
        warehouse1.setRemarks("Excel测试数据1");
        testWarehouses.add(warehouse1);

        WarehouseDTO warehouse2 = new WarehouseDTO();
        warehouse2.setId(2L);
        warehouse2.setName("Excel仓库2");
        warehouse2.setLocation("上海市浦东新区");
        warehouse2.setCapacity(2000);
        warehouse2.setUsedCapacity(1000);
        warehouse2.setManager("李四");
        warehouse2.setStatus("正常");
        warehouse2.setContact("李四");
        warehouse2.setPhone("13900139002");
        warehouse2.setContactPhone("13900139002");
        warehouse2.setContactEmail("lisi@example.com");
        warehouse2.setCreatedBy("admin");
        warehouse2.setUpdatedBy("admin");
        warehouse2.setCreateTime(LocalDateTime.now());
        warehouse2.setUpdateTime(LocalDateTime.now());
        warehouse2.setRemarks("Excel测试数据2");
        testWarehouses.add(warehouse2);

        return testWarehouses;
    }

    private List<InboundDTO> getTestInbounds() {
        List<InboundDTO> testInbounds = new ArrayList<>();
        
        // Excel测试数据
        InboundDTO inbound1 = new InboundDTO();
        inbound1.setId(1L);
        inbound1.setWarehouseId(1L);
        inbound1.setInboundNumber("EX001");
        inbound1.setSupplier("Excel供应商1");
        inbound1.setInboundDate(LocalDateTime.now());
        inbound1.setStatus("待入库");
        inbound1.setApprovalStatus("待审批");
        inbound1.setCreatedBy("admin");
        inbound1.setCreateTime(LocalDateTime.now());
        inbound1.setRemarks("Excel测试数据1");
        testInbounds.add(inbound1);

        InboundDTO inbound2 = new InboundDTO();
        inbound2.setId(2L);
        inbound2.setWarehouseId(2L);
        inbound2.setInboundNumber("EX002");
        inbound2.setSupplier("Excel供应商2");
        inbound2.setInboundDate(LocalDateTime.now());
        inbound2.setStatus("已入库");
        inbound2.setApprovalStatus("已审批");
        inbound2.setCreatedBy("admin");
        inbound2.setCreateTime(LocalDateTime.now());
        inbound2.setRemarks("Excel测试数据2");
        testInbounds.add(inbound2);

        return testInbounds;
    }
}