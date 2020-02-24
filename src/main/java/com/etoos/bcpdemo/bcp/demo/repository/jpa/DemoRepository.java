package com.etoos.bcpdemo.bcp.demo.repository.jpa;

import com.etoos.bcpdemo.bcp.demo.model.entity.DemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoRepository extends JpaRepository<DemoEntity, Long> {
}
