package com.routdoo.dailyroutine.module.routine.dto.action;

import com.routdoo.dailyroutine.common.exception.validate.annotation.date.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto.action
 * fileName       : DailyRoutineUpdateRequest
 * author         : rhkdg
 * date           : 2024-05-07
 * description    :일정 기본정보 수정 request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-07        rhkdg       최초 생성
 */
@Schema(description = "일정 기본정보 수정 DTO")
@Getter
@Setter
public class DailyRoutineUpdateRequest {

    /**제목*/
    @NotBlank
    @Schema(description = "일련번호")
    private long idx = 0L;

    /**제목*/
    @NotNull
    @Schema(description = "제목")
    private String title ="";

    /**태그*/
    @NotNull
    @Schema(description = "태그")
    private String tag = "";

    /**시작일자*/
    @NotEmpty
    @Schema(description = "시작일자")
    @Date
    @NotBlank
    private String startDate = "";

    /**마지막일자*/
    @NotNull
    @Date
    @NotBlank
    @Schema(description = "마지막일자")
    private String endDate = "";

    /**일정타입*/
    @NotBlank
    @Schema(description = "일정 타입", example = "DAY : 단기, LONG_DAY : 장기")
    private String dayType = "";

    /**공개여부*/
    @NotBlank
    @Schema(description = "공개범위" ,example = "PUBLIC : 공개 , PRIVATE : 비공개")
    private String rangeType = "";

}
