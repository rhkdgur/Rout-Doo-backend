package com.routdoo.dailyroutine.auth.member.dto.action.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.auth.member.dto.action.friend
 * fileName       : MemberFriendsBlockRequest
 * author         : rhkdg
 * date           : 2024-05-08
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-08        rhkdg       최초 생성
 */
@Schema(description = "친구 차단 등록 request")
@Getter
@Setter
public class MemberFriendsBlockCreateRequest {
    
    @Schema(description = "친구정보 일련번호")
    @NotBlank(message = "친구 일련번호는 필수값입니다.")
    private long idx = 0L;
    
    @Schema(description = "차단여부(Y,N)")
    @NotBlank(message = "차단여부는 필수값입니다.")
    private String blockYn = "";
}
