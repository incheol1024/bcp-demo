package com.etoos.bcpdemo.bcp.demo.service;

import com.etoos.bcpdemo.bcp.demo.model.entity.DemoEntity;
import com.etoos.bcpdemo.bcp.demo.model.vo.DemoVo;
import com.etoos.bcpdemo.bcp.demo.repository.jpa.DemoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DemoServiceSliceTest {

    @Mock
    private DemoRepository demoRepository;


    @Test
    void findEntity() {
        long id = 5;
        String name = "incheol";

        // given
        BDDMockito.given(demoRepository.findById(anyLong()))
                .willAnswer(invocation -> {
                    long argument = invocation.getArgument(0);
                    DemoEntity demoEntity = new DemoEntity();
                    demoEntity.setId(argument);
                    demoEntity.setName(name);
                    return Optional.of(demoEntity);
                });

        // when
        DemoService demoService = new DemoService(demoRepository);
//        DemoVo entity = demoService.findEntity(id);

        // then & verify
//        Assertions.assertThat(entity).matches(demoEntity ->
//                        demoEntity.getId() == id && demoEntity.getName().equals(name)
//                , "DemoEntity Fields")
//                .satisfies(System.out::println);

        verify(demoRepository, new Times(1)).findById(anyLong());
    }
}
