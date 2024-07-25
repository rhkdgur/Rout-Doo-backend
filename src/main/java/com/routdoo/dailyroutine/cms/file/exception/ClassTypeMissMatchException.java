package com.routdoo.dailyroutine.cms.file.exception;

/**
 * packageName    : com.routdoo.dailyroutine.cms.file.exception
 * fileName       : ClassTypeMissMatchException
 * author         : GAMJA
 * date           : 2023/12/10
 * description    : 파일 클래스 타입 일치 예외처리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/10        GAMJA       최초 생성
 */
public class ClassTypeMissMatchException extends Exception{

    public ClassTypeMissMatchException(String message){
        super(message);
    }

}
