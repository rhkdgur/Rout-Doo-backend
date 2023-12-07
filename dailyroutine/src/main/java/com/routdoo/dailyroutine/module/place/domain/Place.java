package com.routdoo.dailyroutine.module.place.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.routdoo.dailyroutine.auth.member.domain.Member;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.routdoo.dailyroutine.module.place.dto.PlaceDto;
import com.routdoo.dailyroutine.module.place.service.PlaceStatusType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.place.domain
* @fileName      : Place.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.17
* @description   : 장소 entity
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.17        ghgo       최초 생성
 */
@Entity
@Table(name="place")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Place implements Persistable<String> {
	
	@Id
	@Comment("장소번호")
	private String placeNum;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Member member;

	@Comment("제목")
	private String title;
	
	@Comment("해쉬태그")
	@Column(length = 100)
	private String hashtag;

	@Comment("연락처")
	@Column(length= 50)
	private String tel;
	
	@Comment("주소")
	private String addr;
	
	@Comment("경도")
	private String mapx;
	
	@Comment("위도")
	private String mapy;
	
	@Comment("소개글")
	@Lob
	private String introText;
	
	@Comment("이용안내")
	@Lob
	private String useInfo;
	
	@Comment("상세내용")
	@Lob
	private String detailText;
	
	@Comment("사용여부")
	@Enumerated(EnumType.STRING)
	private PlaceStatusType pstatus;

	@Comment("삭제요청사유")
	@Lob
	private String deleteReason;
	
	@CreatedDate
	@Comment("등록일자")
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Comment("수정일자")
	private LocalDateTime modifyDate;
	
	@OneToMany(mappedBy = "place",fetch = FetchType.LAZY)
	private List<PlaceComment> placeComments = new ArrayList<PlaceComment>();
	
	@OneToMany(mappedBy = "place",fetch = FetchType.LAZY)
	private List<PlaceLike> placeLikes = new ArrayList<PlaceLike>();
	
	@Builder
	public Place(PlaceDto dto) {
		this.placeNum = dto.getPlaceNum();
		this.member = new Member();
		member.addId(dto.getMemberId());
		this.title = dto.getTitle();
		this.tel = dto.getTel();
		this.hashtag = dto.getHashtag();
		this.addr = dto.getAddr();
		this.mapx = dto.getMapx();
		this.mapy = dto.getMapy();
		this.introText = dto.getIntroText();
		this.useInfo = dto.getUseInfo();
		this.detailText = dto.getDetailText();
		this.deleteReason = dto.getDeleteReason();
		this.createDate = dto.getCreateDate();
		this.modifyDate = dto.getModifyDate();
	}
	
	/**
	 * 변경 처리 
	 * @param dto
	 */
	public void chagnePlace(PlaceDto dto) {
		this.title = dto.getTitle();
		this.hashtag = dto.getHashtag();
		this.addr = dto.getAddr();
		this.mapx = dto.getMapx();
		this.mapy = dto.getMapy();
		this.introText = dto.getIntroText();
		this.useInfo = dto.getUseInfo();
		this.detailText = dto.getDetailText();
		this.deleteReason = dto.getDeleteReason();
		this.createDate = dto.getCreateDate();
		this.modifyDate = dto.getModifyDate();
	}

	public Map<String,Object> toSummaryMap(){
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("placeNum",this.placeNum);
		map.put("title",this.title);
		map.put("addr",this.addr);
		map.put("tel",this.tel);
		map.put("mapx",this.mapx);
		map.put("mapy",this.mapy);
		map.put("hashtag",this.hashtag);
		return map;
	}
	
	public void addPlaceNum(String placeNum) {
		this.placeNum = placeNum;
	}

	@Override
	public String getId() {
		return this.placeNum;
	}

	@Override
	public boolean isNew() {
		return this.createDate == null;
	}
}
