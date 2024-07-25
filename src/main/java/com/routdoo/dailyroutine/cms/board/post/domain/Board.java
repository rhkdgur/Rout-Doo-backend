package com.routdoo.dailyroutine.cms.board.post.domain;

import com.routdoo.dailyroutine.cms.board.group.domain.BoardGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * packageName    : com.routdoo.dailyroutine.cms.board.post.domain
 * fileName       : Board
 * author         : rhkdg
 * date           : 2024-01-29
 * description    : 게시판 글 관리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-01-29        rhkdg       최초 생성
 */
@Entity
@Table(name="cms_board_data")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gcode")
    private BoardGroup boardGroup = new BoardGroup();

    @Comment("비밀번호")
    private String password;

    @Comment("작성자")
    private String name;

    @Comment("제목")
    private String title;

    @Lob
    @Comment("내용")
    private String content;
    
    @Comment("조회수")
    private int viewCnt;

    @Column(length = 20)
    @Comment("게시 시작일자")
    private String startDate;

    @Column(length = 20)
    @Comment("게시 마지막일자")
    private String endDate;

    @Column(columnDefinition = "varchar(1) default 'N'")
    @Comment("비공개 여부")
    private String privityYn;

    @Column(columnDefinition = "varchar(1) default 'N'")
    @Comment("공지글 여부")
    private String noticeYn;

    @Column(length = 20)
    @Comment("작성자 아이피")
    private String writeIp;

    @Column(columnDefinition = "varchar(1) default 'N'")
    @Comment("삭제여부")
    private String delYn;

    @Comment("파일1")
    private String file1;

    @Comment("파일2")
    private String file2;

    @Comment("파일3")
    private String file3;

    @Comment("파일4")
    private String file4;

    @Comment("파일5")
    private String file5;

    @Comment("파일1 비고")
    private String file1Alt;

    @Comment("파일2 비고")
    private String file2Alt;

    @Comment("파일3 비고")
    private String file3Alt;

    @Comment("파일4 비고")
    private String file4Alt;

    @Comment("파일5 비고")
    private String file5Alt;

    @CreatedDate
    @Comment("등록일자")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Comment("수정일자")
    private LocalDateTime modifyDate;

}
