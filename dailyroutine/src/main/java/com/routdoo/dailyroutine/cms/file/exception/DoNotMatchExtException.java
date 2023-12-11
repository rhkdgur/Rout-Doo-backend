package com.routdoo.dailyroutine.cms.file.exception;

/**
 * packageName    : com.routdoo.dailyroutine.cms.file.exception
 * fileName       : DonotMatchException
 * author         : GAMJA
 * date           : 2023/12/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/11        GAMJA       최초 생성
 */
public class DoNotMatchExtException extends Exception{

    public DoNotMatchExtException(String message) {
        super(message);
    }
    public DoNotMatchExtException() {
        super();
    }
}
