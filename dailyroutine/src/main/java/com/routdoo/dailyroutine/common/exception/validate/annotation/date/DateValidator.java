package com.routdoo.dailyroutine.common.exception.validate.annotation.date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * packageName    : com.routdoo.dailyroutine.common.exception.validate.annotation.date
 * fileName       : DateValidator
 * author         : rhkdg
 * date           : 2024-02-29
 * description    : Date Validater custom Override
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-02-29        rhkdg       최초 생성
 */
public class DateValidator implements ConstraintValidator<Date, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile("^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}
