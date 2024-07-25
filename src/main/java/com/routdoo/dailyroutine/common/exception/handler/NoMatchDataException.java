package com.routdoo.dailyroutine.common.exception.handler;

import lombok.Getter;

/**
 * packageName    : com.routdoo.dailyroutine.common.exception.handler
 * fileName       : NoMatchDataException
 * author         : GAMJA
 * date           : 2024/05/06
 * description    : 데이터 불일치 예외 처리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/06        GAMJA       최초 생성
 */
@Getter
public class NoMatchDataException extends RuntimeException{

    public NoMatchDataException(String message) {
        super(message);
    }
}
