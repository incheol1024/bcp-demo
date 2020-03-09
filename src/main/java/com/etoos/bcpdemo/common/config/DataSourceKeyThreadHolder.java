package com.etoos.bcpdemo.common.config;

import com.etoos.bcpdemo.common.constant.DataSourceDirection;

public class DataSourceKeyThreadHolder {

    private static final ThreadLocal<DataSourceDirection> DATA_SOURCE_DIRECTION_THREAD_LOCAL = new ThreadLocal<>();

    public static void setDataSourceKey(DataSourceDirection dataSourceDirection) {
        DATA_SOURCE_DIRECTION_THREAD_LOCAL.set(dataSourceDirection);
    }

    public static DataSourceDirection getDataSourceKey() {
        return DATA_SOURCE_DIRECTION_THREAD_LOCAL.get();
    }

    public static void clearDataSourceKey() {
        DATA_SOURCE_DIRECTION_THREAD_LOCAL.remove();
    }
}
