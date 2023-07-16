package com.routdoo.dailyroutine.auth.admin.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.routdoo.dailyroutine.auth.admin.dto.AdminDto;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.admin.domain
* @fileName      : Admin.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.16
* @description   : 관리자 admin entity
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.16        ghgo       최초 생성
 */
@Entity
@Table(name="cms_admin")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Admin {

	@Id
	@Comment("아이디")
	private String id;
	
	@Comment("패스워드")
	private String pw;
	
	@Comment("이름")
	private String name;
	
	@Comment("순위")
	private int rating;
	
	@CreatedDate
	@Comment("등록일자")
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Comment("수정일자")
	private LocalDateTime modifyDate;

	/**
	 * 엔티티 생성자
	 * @param id
	 * @param pw
	 * @param name
	 * @param rating
	 * @param createDate
	 * @param modifyDate
	 */
	@Builder
	public Admin(AdminDto dto) {
		this.id = dto.getId();
		this.pw = dto.getPw();
		this.name = dto.getName();
		this.rating = dto.getRating();
		this.createDate = dto.getCreateDate();
		this.modifyDate = dto.getModifyDate();
	}
	
	/**
	 * 변경 감지
	 * @param dto
	 */
	public void changeAdmin(AdminDto dto) {
		this.id = dto.getId();
		this.pw = dto.getPw();
		this.name = dto.getName();
		this.rating = dto.getRating();
		this.createDate = dto.getCreateDate();
		this.modifyDate = dto.getModifyDate();
	}
	
}
