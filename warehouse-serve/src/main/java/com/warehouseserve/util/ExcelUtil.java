package com.warehouseserve.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;

import java.io.*;
import java.util.List;

public class ExcelUtil {
    public static <T> List<T> readExcel(String filePath, Class<T> clazz) {
        try (InputStream is = new FileInputStream(filePath)) {
            ExcelReaderBuilder readerBuilder = EasyExcel.read(is, clazz, null);
            return readerBuilder.sheet().doReadSync();
        } catch (IOException e) {
            throw new RuntimeException("读取Excel失败", e);
        }
    }

    public static <T> void writeExcel(String filePath, List<T> data, Class<T> clazz) {
        try (OutputStream os = new FileOutputStream(filePath)) {
            ExcelWriterBuilder writerBuilder = EasyExcel.write(os, clazz);
            writerBuilder.sheet("Sheet1").doWrite(data);
        } catch (IOException e) {
            throw new RuntimeException("写入Excel失败", e);
        }
    }
}