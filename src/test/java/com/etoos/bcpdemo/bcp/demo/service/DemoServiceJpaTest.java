package com.etoos.bcpdemo.bcp.demo.service;

import com.etoos.bcpdemo.bcp.demo.repository.jpa.DemoRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
class DemoServiceJpaTest {

    @Autowired
    private DemoServiceJpa demoServiceJpa;

    @MockBean
    private DemoRepository demoRepository;





}