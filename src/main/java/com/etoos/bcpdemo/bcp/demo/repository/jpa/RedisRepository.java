package com.etoos.bcpdemo.bcp.demo.repository.jpa;

import com.etoos.bcpdemo.bcp.demo.model.entity.RedisEntity;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<RedisEntity, Long> {
}
