package com.routdoo.dailyroutine.module.routine.dto.action.invite;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto
 * fileName       : DailyRoutineInviteActionRequest
 * author         : GAMJA
 * date           : 2024/05/06
 * description    : 친구 초대 Request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/05/06        GAMJA       최초 생성
 */
@Getter
@Setter
public class DailyRoutineInviteCreateRequest {

    @NotBlank(message = "일상 일련번호는 필수값입니다.")
    private long dailyIdx = 0L;

    @NotBlank(message = "회원 아이디는 필수값입니다.")
    private String memberId = "";

}
