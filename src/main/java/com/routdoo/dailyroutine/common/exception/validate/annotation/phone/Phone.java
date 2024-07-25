package com.routdoo.dailyroutine.common.exception.validate.annotation.phone;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * packageName    : com.routdoo.dailyroutine.common.exception.validate.annotation.phone
 * fileName       : Phone
 * author         : rhkdg
 * date           : 2024-02-29
 * description    : 휴대전화 Validator custom Annotation
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-02-29        rhkdg       최초 생성
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PhoneValidator.class})
@Documented
public @interface Phone {
    String message() default "휴대전화(000-0000-0000)형식이 올바르지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
