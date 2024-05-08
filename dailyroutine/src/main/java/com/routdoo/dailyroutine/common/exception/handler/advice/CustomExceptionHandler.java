package com.routdoo.dailyroutine.common.exception.handler.advice;

import com.routdoo.dailyroutine.cms.file.exception.ClassTypeMissMatchException;
import com.routdoo.dailyroutine.cms.file.exception.DoNotMatchExtException;
import com.routdoo.dailyroutine.common.exception.handler.NoMatchDataException;
import com.routdoo.dailyroutine.common.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * packageName    : com.routdoo.dailyroutine.common.exception.handler
 * fileName       : CustomExceptionHandler
 * author         : GAMJA
 * date           : 2024/05/06
 * description    : exception handler entity 처리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/06        GAMJA       최초 생성
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 데이터 불일치 예외처리 커스텀 적용
     * @param ex
     * @return
     */
    @ExceptionHandler(NoMatchDataException.class)
    public final ResponseEntity<Object> handleNoMatchDataException(NoMatchDataException ex) {
        ExceptionResponse exceptionResponse= new ExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 파일 확장명 사용 불가능
     * @param ex
     * @return
     */
    @ExceptionHandler(DoNotMatchExtException.class)
    public final ResponseEntity<Object> handelDoNotMatchExtException(DoNotMatchExtException ex){
        ExceptionResponse exceptionResponse= new ExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 클래스 타입 불일치
     * @param ex
     * @return
     */
    @ExceptionHandler(ClassTypeMissMatchException.class)
    public final ResponseEntity<Object> handelClassTypeMissMatchException(ClassTypeMissMatchException ex){
        ExceptionResponse exceptionResponse= new ExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
