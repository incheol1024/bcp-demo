package com.etoos.bcpdemo.bcp.demo.repository.jpa;

import com.etoos.bcpdemo.bcp.demo.model.entity.DemoMsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoMsRepository extends JpaRepository<DemoMsEntity, Long> {

}
