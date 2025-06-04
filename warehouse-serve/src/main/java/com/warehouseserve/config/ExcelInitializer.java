package com.warehouseserve.config;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class ExcelInitializer implements CommandLineRunner {

    @Value("${excel.file.path:warehouse_data.xlsx}")
    private String excelFilePath;

    @Override
    public void run(String... args) throws Exception {
        File file = new File(excelFilePath);
        if (!file.exists()) {
            createExcelFile();
        }
    }

    private void createExcelFile() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            // 创建仓库信息表
            Sheet warehouseSheet = workbook.createSheet("仓库信息");
            createWarehouseSheet(warehouseSheet);

            // 创建入库记录表
            Sheet inboundSheet = workbook.createSheet("入库记录");
            createInboundSheet(inboundSheet);

            // 保存文件
            try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
                workbook.write(fileOut);
            }
        }
    }

    private void createWarehouseSheet(Sheet sheet) {
        // 创建表头
        Row headerRow = sheet.createRow(0);
        String[] headers = {
            "ID", "名称", "位置", "容量", "已用容量", "管理员", "状态",
            "联系人", "电话", "联系电话", "联系邮箱", "备注"
        };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 添加测试数据
        Object[][] data = {
            {1L, "主仓库", "北京市朝阳区", 1000, 300, "张三", "正常", "张三", "010-12345678", "13800138000", "zhangsan@example.com", "主仓库"},
            {2L, "分仓库1", "上海市浦东新区", 800, 200, "李四", "正常", "李四", "021-87654321", "13900139000", "lisi@example.com", "分仓库1"},
            {3L, "分仓库2", "广州市天河区", 600, 150, "王五", "正常", "王五", "020-12345678", "13700137000", "wangwu@example.com", "分仓库2"}
        };

        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < data[i].length; j++) {
                Cell cell = row.createCell(j);
                if (data[i][j] instanceof Long) {
                    cell.setCellValue((Long) data[i][j]);
                } else if (data[i][j] instanceof Integer) {
                    cell.setCellValue((Integer) data[i][j]);
                } else {
                    cell.setCellValue(data[i][j].toString());
                }
            }
        }
    }

    private void createInboundSheet(Sheet sheet) {
        // 创建表头
        Row headerRow = sheet.createRow(0);
        String[] headers = {
            "ID", "仓库ID", "入库单号", "供应商", "入库日期", "状态", "审批状态", "创建人", "创建时间"
        };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // 添加测试数据
        Object[][] data = {
            {1L, 1L, "IN20240101001", "供应商A", LocalDateTime.now(), "已完成", "已审批", "admin", LocalDateTime.now()},
            {2L, 2L, "IN20240101002", "供应商B", LocalDateTime.now(), "进行中", "待审批", "admin", LocalDateTime.now()},
            {3L, 3L, "IN20240101003", "供应商C", LocalDateTime.now(), "已完成", "已审批", "admin", LocalDateTime.now()}
        };

        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < data[i].length; j++) {
                Cell cell = row.createCell(j);
                if (data[i][j] instanceof Long) {
                    cell.setCellValue((Long) data[i][j]);
                } else if (data[i][j] instanceof LocalDateTime) {
                    cell.setCellValue(((LocalDateTime) data[i][j]).toString());
                } else {
                    cell.setCellValue(data[i][j].toString());
                }
            }
        }
    }
} 