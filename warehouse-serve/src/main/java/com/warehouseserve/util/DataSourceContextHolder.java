package com.warehouseserve.util;

import com.warehouseserve.service.datasource.DataSourceType;

public class DataSourceContextHolder {
    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();

    public static void setDataSourceType(DataSourceType type) {
        contextHolder.set(type);
    }

    public static DataSourceType getDataSourceType() {
        return contextHolder.get() == null ? DataSourceType.DATABASE : contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}