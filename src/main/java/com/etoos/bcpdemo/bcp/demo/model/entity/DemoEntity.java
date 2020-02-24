package com.etoos.bcpdemo.bcp.demo.model.entity;

import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;

import javax.persistence.*;

@Entity
public class DemoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DemoUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public DemoEntity setValueOfDemoVo(DemoVo demoVo) {
        this.id = demoVo.getId();
        this.name = demoVo.getName();
        return this;
    }
}
