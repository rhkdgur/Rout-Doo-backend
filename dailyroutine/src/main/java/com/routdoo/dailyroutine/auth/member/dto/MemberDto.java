package com.routdoo.dailyroutine.auth.member.dto;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.routdoo.dailyroutine.auth.member.domain.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.user.dto
* @fileName      : Member.java
* @author        : Gwang hyeok Go
* @date          : 2023.07.11
* @description   : 회원 정보 DTO
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.07.11        ghgo       최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberDto {

	private String id = "";
	
	private String pw = "";
	
	private String email = "";
	
	private String nickname = "";
	
	private String phonenumber = "";
	
	private String gender = "";
	
	private int age = 0;
	
	private String birth = "";
	
	private String mbti = "";
	
	private LocalDateTime createDate;
	
	private LocalDateTime modifyDate;
	
	/**
	 * dto -> entity 
	 * @return
	 */
	public Member toEntity() {
		return Member.builder().dto(this).build();
	}
	
	/**
	 * entity 파라미터 생성자
	 * @param entity
	 */
	public MemberDto(Member entity) {
		this.id = entity.getId();
		this.pw = entity.getPw();
		this.email = entity.getEmail();
		this.nickname = entity.getNickname();
		this.phonenumber = entity.getPhonenumber();
		this.gender = entity.getGender();
		this.age = entity.getAge();
		this.birth = entity.getBirth();
		this.mbti = entity.getMbti();
		this.createDate = entity.getCreateDate();
		this.modifyDate = entity.getModifyDate();
	}
	
	/**
	 * 요약 정보 조회
	 * @return
	 */
	public Map<String,Object> getSummaryInfo(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", this.id);
		map.put("nickname", this.nickname);
		map.put("gender", this.gender);
		map.put("age", this.age);
		map.put("mbti", this.mbti);
		return map;
	}
	
	/**
	 * 만 나이로 계산 
	 * @return
	 */
	public int getAge() {
		if(this.birth != null && !this.birth.isEmpty()) {
			Calendar current = Calendar.getInstance();
	        int currentYear  = current.get(Calendar.YEAR);
	        int currentMonth = current.get(Calendar.MONTH) + 1;
	        int currentDay   = current.get(Calendar.DAY_OF_MONTH);
	        int birthYear = Integer.parseInt(birth.split("-")[0]);
	        int birthMonth = Integer.parseInt(birth.split("-")[1]);
	        int birthDay = Integer.parseInt(birth.split("-")[2]);
	        int age = currentYear - birthYear;
	        // 만약 생일이 지나지 않았으면 -1
	        if (birthMonth * 100 + birthDay > currentMonth * 100 + currentDay) 
	            age--;
	        return age;
		}
		return 0;
	}
}
