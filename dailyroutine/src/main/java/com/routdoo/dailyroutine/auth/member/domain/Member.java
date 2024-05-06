package com.routdoo.dailyroutine.auth.member.domain;

import com.routdoo.dailyroutine.auth.member.dto.MemberActionRequest;
import com.routdoo.dailyroutine.auth.member.dto.MemberDto;
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
import java.util.List;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.user.domain
* @fileName      : Member.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.06
* @description   : 회원 정보 entity
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.06        ghgo       최초 생성
 */
@Entity
@Table(name="member")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member implements Persistable<String> {

	@Id
	@Column(length = 30)
	@Comment("아이디")
	private String id;
	
	@Column(length = 100)
	@Comment("비밀번호")
	private String pw;
	
	@Column(length = 50)
	@Comment("이메일")
	private String email;
	
	@Column(length = 50)
	@Comment("닉네임")
	private String nickname;
	
	@Column(length = 30)
	@Comment("전화번호")
	private String phonenumber;
	
	@Column(length = 1, columnDefinition = "char")
	@Comment("성별")
	private String gender;
	
	@Comment("나이")
	private int age;
	
	@Column(length=20)
	@Comment("생년월일")
	private String birth;
	
	@Column(length=4)
	@Comment("MBTI")
	private String mbti;

	@Lob
	@Comment("자기소개글")
	private String introText;

	@Column(length=1, columnDefinition = "char default 'Y' ")
	@Comment("회원상태")
	private String useStatus;
	
	@Comment("등록일자")
	@CreatedDate
	private LocalDateTime createDate;
	
	@Comment("수정일자")
	@LastModifiedDate
	private LocalDateTime modifyDate;
	
	/**친구목록*/
	@OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
	private List<MemberFriends> friendList = new ArrayList<MemberFriends>();
	
	/**
	 * entity에 dto 정보 처리 생성자
	 * @param dto
	 */
	@Builder
	public Member(MemberDto dto) {
		this.id = dto.getId();
		this.pw = dto.getPw();
		this.email = dto.getEmail();
		this.nickname = dto.getNickname();
		this.phonenumber = dto.getPhonenumber();
		this.gender = dto.getGender();
		this.age = dto.getAge();
		this.birth = dto.getBirth();
		this.mbti = dto.getMbti();
		this.introText = dto.getIntroText();
		this.useStatus = dto.getUseStatus();
		this.createDate = dto.getCreateDate();
		this.modifyDate = dto.getModifyDate();
	}

	@Builder(builderMethodName = "createMember")
	public Member (MemberActionRequest memberActionRequest) {
		this.id = memberActionRequest.getId();
		this.pw = memberActionRequest.getPw();
		this.email = memberActionRequest.getEmail();
		this.nickname = memberActionRequest.getNickname();
		this.phonenumber = memberActionRequest.getPhonenumber();
		this.gender = memberActionRequest.getGender();
		this.age = memberActionRequest.getAge();
		this.birth = memberActionRequest.getBirth();
		this.mbti = memberActionRequest.getMbti();
		this.introText = memberActionRequest.getIntroText();
		this.useStatus = memberActionRequest.getUseStatus();
	}

	/**
	 * 전체 정보 업데이트 처리 메소드
	 * @param dto
	 */
	public void changeMember(MemberActionRequest dto) {
		this.id = dto.getId();
		this.pw = dto.getPw();
		this.email = dto.getEmail();
		this.nickname = dto.getNickname();
		this.phonenumber = dto.getPhonenumber();
		this.gender = dto.getGender();
		this.age = dto.getAge();
		this.birth = dto.getBirth();
		this.mbti = dto.getMbti();
		this.introText = dto.getIntroText();
		this.useStatus = dto.getUseStatus();
	}
	
	public void addFriendList(List<MemberFriends> list) {
		this.friendList = list;
	}
	
	public void addFreind(MemberFriends friend) {
		this.friendList.add(friend);
	}
	
	public void addId(String id) {
		this.id = id;
	}

	@Override
	public boolean isNew() {
		return this.id.isEmpty();
	}
}
