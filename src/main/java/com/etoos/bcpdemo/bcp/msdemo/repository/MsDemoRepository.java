package com.etoos.bcpdemo.bcp.msdemo.repository;

import com.etoos.bcpdemo.bcp.msdemo.entity.MsDemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MsDemoRepository extends JpaRepository<MsDemoEntity, Long> {
}
