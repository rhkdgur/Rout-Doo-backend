package com.routdoo.dailyroutine.common.exception.validate.annotation.date;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * packageName    : com.routdoo.dailyroutine.common.exception.validate.annotation
 * fileName       : Date
 * author         : rhkdg
 * date           : 2024-02-29
 * description    : Date validater
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-02-29        rhkdg       최초 생성
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { DateValidator.class})
@Documented
public @interface Date {

    String message() default "날짜(yyyy-MM-dd) 형식이 올바르지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
