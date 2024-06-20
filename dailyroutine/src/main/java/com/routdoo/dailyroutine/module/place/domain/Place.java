package com.routdoo.dailyroutine.module.place.domain;

import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.module.place.dto.PlaceDto;
import com.routdoo.dailyroutine.module.place.service.PlaceStatusType;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	@JoinColumn(name = "member_id")
	private Member member;

	@Comment("제목")
	private String title;

	@Comment("카테고리 코드")
	private String categCd;

	@Comment("연락처")
	@Column(length= 50)
	private String tel;
	
	@Comment("주소")
	private String addr;
	
	@Comment("경도")
	private String mapx;
	
	@Comment("위도")
	private String mapy;
	
	@Comment("사용여부")
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

	@OneToMany(mappedBy = "place",orphanRemoval=true ,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE}, fetch = FetchType.LAZY)
	private List<PlaceIntro> placeIntros = new ArrayList<>();
	
	@Builder
	public Place(PlaceDto dto) {
		this.placeNum = dto.getPlaceNum();
		this.title = dto.getTitle();
		this.member = new Member();
		member.addId(dto.getMemberId());
		this.tel = dto.getTel();
		this.categCd = dto.getCategCd();
		this.addr = dto.getAddr();
		this.mapx = dto.getMapx();
		this.mapy = dto.getMapy();
		this.pstatus = PlaceStatusType.valueOf(dto.getPstatus());
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
		map.put("categCd",this.categCd);
		return map;
	}
	
	/**
	 * 변경 처리 
	 * @param dto
	 */
	public void chagnePlace(PlaceDto dto) {
		this.title = dto.getTitle();
		this.categCd = dto.getCategCd();
		this.addr = dto.getAddr();
		this.mapx = dto.getMapx();
		this.mapy = dto.getMapy();
		this.pstatus = PlaceStatusType.valueOf(dto.getPstatus());
	}
	
	public void addPlaceNum(String placeNum) {
		this.placeNum = placeNum;
	}

	public void addPlaceIntro(PlaceIntro placeIntro) {
		this.getPlaceIntros().add(placeIntro);
		placeIntro.addPlace(this);
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
