package com.routdoo.dailyroutine.module.routine.dto.action.invite;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto.action.invite
 * fileName       : DailyRoutineInviteDeleteRequest
 * author         : rhkdg
 * date           : 2024-05-08
 * description    : 일상 초대 삭제 request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-08        rhkdg       최초 생성
 */
@Schema(description = "일상 초대 삭제 request ")
@Getter
@Setter
public class DailyRoutineInviteDeleteRequest {
    @NotNull
    private long idx = 0L;
}
