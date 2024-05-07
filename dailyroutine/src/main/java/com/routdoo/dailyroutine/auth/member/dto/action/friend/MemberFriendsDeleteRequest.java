package com.routdoo.dailyroutine.auth.member.dto.action.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.auth.member.dto.action.friend
 * fileName       : MemberFriendsDeleteRequest
 * author         : rhkdg
 * date           : 2024-05-08
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-08        rhkdg       최초 생성
 */
@Schema(description = "회원 친구 삭제")
@Getter
@Setter
public class MemberFriendsDeleteRequest {
    
    @Schema(description = "회원 친구 일련번호")
    @NotBlank(message = "일련번호는 필수값입니다.")
    private long idx = 0L;
}
