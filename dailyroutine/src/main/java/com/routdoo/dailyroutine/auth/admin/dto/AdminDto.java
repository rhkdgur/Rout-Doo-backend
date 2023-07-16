package com.routdoo.dailyroutine.auth.admin.dto;

import java.time.LocalDateTime;

import com.routdoo.dailyroutine.auth.admin.domain.Admin;

import jakarta.validation.constraints.NegativeOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.admin.dto
* @fileName      : AdminDto.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.16
* @description   : 관리자 dto
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.16        ghgo       최초 생성
 */
@Getter @Setter
@NoArgsConstructor
public class AdminDto {
	
	/**아이디*/
	private String id;
	
	/**비밀번호*/
	private String pw;
	
	/**이름*/
	private String name;
	
	/**순위*/
	private int rating;
	
	/**등록일자*/
	private LocalDateTime createDate;
	
	/**수정일자*/
	private LocalDateTime modifyDate;
	
	public Admin toEntity() {
		return Admin.builder().dto(this).build();
	}

	public AdminDto(Admin entity) {
		this.id = entity.getId();
		this.pw = entity.getPw();
		this.name = entity.getName();
		this.rating = entity.getRating();
		this.createDate = entity.getCreateDate();
		this.modifyDate = entity.getModifyDate();
	}
	

}
