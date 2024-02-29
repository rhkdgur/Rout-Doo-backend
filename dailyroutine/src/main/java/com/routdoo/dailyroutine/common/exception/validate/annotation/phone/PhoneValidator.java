package com.routdoo.dailyroutine.common.exception.validate.annotation.phone;

import com.routdoo.dailyroutine.common.exception.validate.annotation.date.Date;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * packageName    : com.routdoo.dailyroutine.common.exception.validate.annotation.phone
 * fileName       : PhoneValidater
 * author         : rhkdg
 * date           : 2024-02-29
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-02-29        rhkdg       최초 생성
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile("^\\d{2,3}-\\d{3,4}-\\d{4}$");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}
