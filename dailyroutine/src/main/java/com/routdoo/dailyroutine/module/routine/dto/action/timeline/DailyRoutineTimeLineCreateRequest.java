package com.routdoo.dailyroutine.module.routine.dto.action.timeline;

import com.routdoo.dailyroutine.common.exception.validate.annotation.date.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.routdoo.dailyroutine.module.routine.dto.action
 * fileName       : DailyRoutineTimeLineCreateRequest
 * author         : rhkdg
 * date           : 2024-05-08
 * description    : 일정 타임라인 request
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-08        rhkdg       최초 생성
 */
@Schema(description = "일정 타임라인 등록 request ")
@Getter
@Setter
public class DailyRoutineTimeLineCreateRequest {

    /**일정 메인 정보*/
    @Schema(description = "잃정 일련번호")
    @NotNull
    private Long dailyIdx = 0L;

    /**적용일자*/
    @Schema(description = "적용일자")
    @NotBlank
    @Date
    private String applyDate;

    /**제목*/
    @Schema(description = "제목")
    @NotBlank
    private String title;

    /**장소명*/
    @Schema(description = "장소명")
    private String placeName;

    /**주소*/
    @Schema(description = "주소")
    @NotBlank
    private String addr;

    /**경도*/
    @Schema(description = "경도")
    @NotBlank
    private String mapx;

    /**위도*/
    @Schema(description = "위도")
    @NotBlank
    private String mapy;

    /**순서*/
    @Schema(description = "순서")
    private int ord = 0;

    /**내용*/
    @Schema(description = "내용")
    @NotEmpty
    private String context;

    /**시작시간*/
    @Schema(description = "시작시간" , example = "01")
    @NotNull
    private String shour;

    /**시작분*/
    @Schema(description = "시작분", example = "01")
    @NotNull
    private String smin;

    /**마지막시간*/
    @Schema(description = "마지막시간", example = "01")
    @NotNull
    private String ehour;

    /**마지막분*/
    @Schema(description = "마지막 분", example = "01")
    @NotNull
    private String emin;

    /**비용*/
    @Schema(description = "비용", defaultValue = "0",example = "1000")
    @NotNull
    private int cost = 0;

}
