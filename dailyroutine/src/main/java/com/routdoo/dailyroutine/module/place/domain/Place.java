package com.routdoo.dailyroutine.module.place.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.routdoo.dailyroutine.module.place.dto.PlaceDto;
import com.routdoo.dailyroutine.module.place.service.PlaceStatusType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
public class Place {
	
	@Id
	@Comment("장소번호")
	private String placeNum;

	@Comment("제목")
	private String title;
	
	@Comment("해쉬태그")
	@Column(length = 100)
	private String hashtag;
	
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
	@Column(length = 1,columnDefinition = "char")
	@Enumerated(EnumType.STRING)
	private PlaceStatusType pstatus;
	
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
		this.title = dto.getTitle();
		this.hashtag = dto.getHashtag();
		this.addr = dto.getAddr();
		this.mapx = dto.getMapx();
		this.mapy = dto.getMapy();
		this.introText = dto.getIntroText();
		this.useInfo = dto.getUseInfo();
		this.detailText = dto.getDetailText();
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
		this.createDate = dto.getCreateDate();
		this.modifyDate = dto.getModifyDate();
	}
	
	public void addPlaceNum(String placeNum) {
		this.placeNum = placeNum;
	}
}
