package com.etoos.bcp.sample.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.etoos.common.constants.CrudInterface;
import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.CreditCardNumber;

import com.etoos.bcp.common.model.CommonVo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Alias("sampleVo")
public class SampleVo extends CommonVo {

    private long id;
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    // 정규식으로 이메일의 유효성을 판단한다.
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;

    // @Email annotation 으로 이메일의 유효성을 판단한다.
    @Email()
    private String email1;

    // @Min, @Max annotation 으로 최소, 최대값을 지정한다.
    @Min(value = 18, groups = CrudInterface.Create.class)
    @Max(value = 30, groups = CrudInterface.Create.class)
    private int age;

    // 신용카드번호의 유효성을 판단한다.
    @CreditCardNumber
    private String creditCardNumber;

}
