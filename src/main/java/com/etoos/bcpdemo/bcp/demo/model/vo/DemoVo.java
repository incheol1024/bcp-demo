package com.etoos.bcpdemo.bcp.demo.model.vo;

import com.etoos.bcpdemo.bcp.demo.model.entity.DemoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.etoos.bcpdemo.common.constant.CrudInterface.Create;

@Data
@Alias("demo")
@AllArgsConstructor
@NoArgsConstructor
public class DemoVo{

    @Min(value = 0, groups = Create.class)
    private long id;

    @NotNull(groups = Create.class)
    private String name;

    public static DemoVo createDemoVo(DemoEntity demoEntity) {
        DemoVo demoVo = new DemoVo(demoEntity.getId(), demoEntity.getName());
        return demoVo;
    }

}
