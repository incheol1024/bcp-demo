package com.etoos.common.constants;

public enum TransactionManagerName {

    POSTGRES_JPA_TX_MANAGER(""),
    MS_SQL_JPA_TX_MANAGER(""),
    POSTGRES_MYBATIS_TX_MANAGER(""),
    MS_SQL_MYBATIS_TX_MANAGER("");

    private String name;

    TransactionManagerName(String name) {
        this.name = name;
    }
}
