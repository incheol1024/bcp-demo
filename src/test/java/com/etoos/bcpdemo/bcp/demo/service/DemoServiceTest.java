package com.etoos.bcpdemo.bcp.demo.service;

import com.etoos.bcpdemo.bcp.demo.model.entity.DemoEntity;
import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import com.etoos.bcpdemo.bcp.demo.repository.jpa.DemoRepository;
import com.etoos.bcpdemo.common.model.CommonModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
class DemoServiceTest {

    @Autowired
    private DemoService demoService;

    @MockBean
    private DemoRepository demoRepository;

    private String demoEntityNameOnField;


    @BeforeEach
    public void setUpForEach() {
        DemoEntity demoEntity = new DemoEntity();

        // given
        demoEntityNameOnField = "incheol";
        given(demoRepository.findById(anyLong()))
                .will(invocation -> {
                    long argument = invocation.getArgument(0);
                    demoEntity.setId(argument);
                    demoEntity.setName(demoEntityNameOnField);
                    return Optional.of(demoEntity);
                });
    }

    @Test
    void findNameById() {
        CommonModel actual = demoService.findDemo(1);
        Assertions.assertThat(actual).isEqualTo(demoEntityNameOnField);
    }

    @Test
    void findEntity() {
        //when
        long id = 5;
//        DemoVo entity = demoService.findEntity(id);

        //then & verify
//        Assertions.assertThat(entity).matches(demoEntity ->
//                        demoEntity.getId() == id && demoEntity.getName().equals(demoEntityNameOnField)
//                , "DemoEntity Fields")
//                .satisfies(System.out::println);

        verify(demoRepository, new Times(1)).findById(anyLong());
    }

    @Test
    void createEntity() {
    }

    @Test
    void updateEntity() {
    }

    @Test
    void deleteEntity() {
    }

    @Test
    void testInsertMapperDemo() {
    }

}