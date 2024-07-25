package com.routdoo.dailyroutine.auth.jwt.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.jwt.domain
* @fileName      : JwtTokenEntity.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.30
* @description   : 임시 사용 jwt 토큰 테이블
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.30        ghgo       최초 생성
 */
@Entity
@Table(name="jwt_token")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class JwtTokenEntity implements Persistable<String>{
	
	@Id
	private String id;
	
	@Comment("접근 토큰")
	private String accessToken;
	
	@Comment("리플레시 토큰")
	private String refreshToken;
	
	@Comment("등록일자")
	@CreatedDate
	private LocalDateTime createDate;
	
	@Comment("수정일자")
	@LastModifiedDate
	private LocalDateTime modifyDate;
	
	public JwtTokenEntity(String id, String accessToken, String refreshToken) {
		this.id = id;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	@Override
	public boolean isNew() {
		return this.id.isEmpty() ? true : false;
	}
	
}
