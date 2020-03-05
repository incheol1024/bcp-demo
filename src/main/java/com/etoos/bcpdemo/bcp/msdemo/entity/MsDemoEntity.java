package com.etoos.bcpdemo.bcp.msdemo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "DemoMsEntity")
@Data
public class MsDemoEntity {

    @Id
    private long id;

    private String name;


}
