package com.etoos.bcpdemo.bcp.demo.model.vo;

import com.etoos.bcpdemo.bcp.demo.model.entity.DemoEntity;
import com.etoos.bcpdemo.common.model.CommonModel;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import static com.etoos.bcpdemo.common.constant.CrudInterface.Create;

@Data
@Alias("demo")
public class DemoVo implements Serializable{

    @Min(value = 0, groups = Create.class)
    private long id;

    @NotNull(groups = Create.class)
    private String name;


    public DemoVo valueOf(DemoEntity demoEntity) {
        this.id = demoEntity.getId();
        this.name = demoEntity.getName();
        return this;
    }

}
