package com.routdoo.dailyroutine.cms.board.group.dto;

import com.routdoo.dailyroutine.cms.board.group.domain.BoardGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.cms.board.group.dto
 * fileName       : BoardGroupDto
 * author         : rhkdg
 * date           : 2024-01-29
 * description    : 게시판 그룹 관리 Dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-01-29        rhkdg       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class BoardGroupDto implements Serializable {


    /*게시판 코드*/
    private String gcode = "";

    /*제목*/
    private String title = "";

    /*공개비공개여부*/
    private String publicYn = "";

    /*비공개글 작성여부*/
    private String usePrivity = "";

    /*공지 게시글여부 버튼 사용여부*/
    private String useNotice = "";

    /*게시기간 사용여부*/
    private String periodYn = "";

    /*첨부파일 경로*/
    private String uploadPath = "";

    /*첨부 파일 개수*/
    private int numFile = 0;

    /*비고*/
    private String remark = "";

    /*사용여부*/
    private String useYn = "";

    /*등록일자*/
    private LocalDateTime createDate;

    /*수정일자*/
    private LocalDateTime modifyDate;

    public BoardGroup toEntity(){
        return BoardGroup.builder().dto(this).build();
    }

    public BoardGroupDto(BoardGroup entity) {
        this.gcode = entity.getGcode();
        this.title = entity.getTitle();
        this.publicYn = entity.getPublicYn();
        this.usePrivity = entity.getUsePrivity();
        this.useNotice = entity.getUseNotice();
        this.periodYn = entity.getPeriodYn();
        this.uploadPath = entity.getUploadPath();
        this.numFile = entity.getNumFile();
        this.remark = entity.getRemark();
        this.useYn = entity.getUseYn();
        this.createDate = entity.getCreateDate();
        this.modifyDate = entity.getModifyDate();
    }
}
