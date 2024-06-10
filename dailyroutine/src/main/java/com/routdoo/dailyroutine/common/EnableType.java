package com.routdoo.dailyroutine.common;

import lombok.Getter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.service
 * fileName       : RoutineUseStatusType
 * author         : rhkdg
 * date           : 2024-06-03
 * description    : 활성화 비활성화
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-03        rhkdg       최초 생성
 */
@Getter
public enum EnableType {

    ENABLE("활성화"),
    DISABLE("비활성화");

    private String display = "";

    EnableType(String display) {
        this.display = display;
    }

}
