package com.etoos.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.validation.Errors;

import com.etoos.common.model.FieldErrorVo;

import java.util.List;
import java.util.stream.Collectors;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationResult {

    private List<FieldErrorVo> errors;

    public static ValidationResult create(Errors errors) {
        List<FieldErrorVo> errorDetails =
                errors.getFieldErrors()
                        .stream()
                        .map(error -> FieldErrorVo.create(error))
                        .collect(Collectors.toList());

        return new ValidationResult(errorDetails);
    }

}
