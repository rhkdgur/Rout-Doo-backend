package com.routdoo.dailyroutine.cms.board.group.domain;

import com.routdoo.dailyroutine.cms.board.group.dto.BoardGroupDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.cms.board.group.domain
 * fileName       : BoardGroup
 * author         : rhkdg
 * date           : 2024-01-29
 * description    : 게시판 그룹 관리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-01-29        rhkdg       최초 생성
 */
@Entity
@Table(name="cms_board_group")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BoardGroup implements Persistable<String> {

    @Id
    @Comment("게시판 코드")
    private String gcode;

    @Comment("제목")
    private String title;

    @Column(columnDefinition = "Y",length = 1)
    @Comment("공개비공개여부")
    private String publicYn;

    @Column(columnDefinition = "N",length = 1)
    @Comment("비공개글 작성여부")
    private String usePrivity;

    @Comment("공지 게시글여부 버튼 사용여부")
    @Column(columnDefinition = "N",length = 1)
    private String useNotice;

    @Column(columnDefinition = "N", length = 1)
    @Comment("게시기간 사용여부")
    private String periodYn;
    
    @Comment("첨부파일 경로")
    private String uploadPath;

    @Comment("첨부 파일 개수")
    private int numFile;

    @Column(length = 400)
    @Comment("비고")
    private String remark;

    @Column(columnDefinition = "Y", length = 1)
    @Comment("사용여부")
    private String useYn;

    @CreatedDate
    @Comment("등록일자")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Comment("수정일자")
    private LocalDateTime modifyDate;

    @Builder
    public BoardGroup(BoardGroupDto dto) {
        this.gcode = dto.getGcode();
        this.title = dto.getTitle();
        this.publicYn = dto.getPublicYn();
        this.usePrivity = dto.getUsePrivity();
        this.useNotice = dto.getUseNotice();
        this.periodYn = dto.getPeriodYn();
        this.uploadPath = dto.getUploadPath();
        this.numFile = dto.getNumFile();
        this.remark = dto.getRemark();
        this.useYn = dto.getUseYn();
        this.createDate = dto.getCreateDate();
        this.modifyDate = dto.getModifyDate();
    }

    @Override
    public String getId() {
        return this.gcode;
    }

    @Override
    public boolean isNew() {
        return this.createDate != null;
    }
}
