package com.routdoo.dailyroutine.common.exception.validate;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.routdoo.dailyroutine.common.exception.validate
 * fileName       : BaseControllerValidAdvice
 * author         : rhkdg
 * date           : 2024-02-29
 * description    : @Valid Common Exception Handling
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-02-29        rhkdg       최초 생성
 */
@RestControllerAdvice
public class BaseControllerValidAdvice {

    //@Valid Exception Handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
