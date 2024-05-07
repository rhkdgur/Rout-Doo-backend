package com.routdoo.dailyroutine.auth.member.dto.action;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.auth.member.dto.action
 * fileName       : MemberCheckIdRequest
 * author         : rhkdg
 * date           : 2024-05-08
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-08        rhkdg       최초 생성
 */
@Schema(description = "아이디 체크 request ")
@Getter
@Setter
public class MemberCheckIdRequest {
    private String id= "";
}
