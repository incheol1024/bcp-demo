package com.etoos.common.database;

public enum DataSourceType {
    /**
     * DEFAULT : 배치표 데이터베이스 연결 Read/Write
     * BCPREAD : 배치표 데이터베이스 연결 Read
     * MEMBER : 회원정보 데이터베이스연결
     */
    DEFAULT, BCPREAD, MEMBER
}
