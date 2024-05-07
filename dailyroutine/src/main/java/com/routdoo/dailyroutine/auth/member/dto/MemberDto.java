package com.routdoo.dailyroutine.auth.member.dto;

import com.routdoo.dailyroutine.auth.member.domain.Member;
import com.routdoo.dailyroutine.auth.member.dto.action.MemberActionRequest;
import com.routdoo.dailyroutine.common.exception.validate.annotation.date.Date;
import com.routdoo.dailyroutine.common.exception.validate.annotation.phone.Phone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Calendar;

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
@Schema(description = "회원정보 DTO")
@Getter
@Setter
@NoArgsConstructor
public class MemberDto {

	@Schema(description = "회원 아이디")
	@NotBlank
	private String id = "";

	@Schema(description = "회원 비밀번호")
	@NotBlank
	private String pw = "";

	@Schema(description = "회원 이메일")
	@Email
	@NotNull
	private String email = "";

	@Schema(description = "회원 닉네임")
	@NotBlank
	private String nickname = "";
	
	@Schema(description = "회원 전화번호")
	@NotBlank
	@Phone
	private String phonenumber = "";

	@Schema(description = "회원 성별", example = "M(남자),W(여자)")
	@NotBlank
	private String gender = "";

	@Schema(description = "생년월일" , example = "yyyy-MM-dd")
	@NotBlank
	@Date
	private String birth = "";

	@Schema(description = "나이(생년월일로 자동생성)")
	private int age = 0;

	@Schema(description = "MBTI")
	@NotBlank
	private String mbti = "";

	@Schema(description = "자기소개")
	private String introText= "";

	@Schema(description = "회원상태")
	@NotBlank
	private String useStatus = "";

	@Schema(description = "등록일자")
	private LocalDateTime createDate;

	@Schema(description = "수정일자")
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
		this.introText = entity.getIntroText();
		this.useStatus = entity.getUseStatus();
		this.createDate = entity.getCreateDate();
		this.modifyDate = entity.getModifyDate();
	}

	public static MemberDto createOf(MemberActionRequest request){
		MemberDto dto = new MemberDto();
		dto.setId(request.getId());
		dto.setPw(request.getPw());
		dto.setEmail(request.getEmail());
		dto.setNickname(request.getNickname());
		dto.setPhonenumber(request.getPhonenumber());
		dto.setGender(request.getGender());
		dto.setAge(request.getAge());
		dto.setBirth(request.getBirth());
		dto.setMbti(request.getMbti());
		dto.setIntroText(request.getIntroText());
		dto.setUseStatus(request.getUseStatus());
		return dto;
	}

	public static MemberDto of(MemberDto memberDto) {
		MemberDto response = new MemberDto();
		response.setId(memberDto.getId());
		response.setEmail(memberDto.getEmail());
		response.setNickname(memberDto.getNickname());
		response.setPhonenumber(memberDto.getPhonenumber());
		response.setGender(memberDto.getGender());
		response.setAge(memberDto.getAge());
		response.setBirth(memberDto.getBirth());
		response.setMbti(memberDto.getMbti());
		response.setIntroText(memberDto.getIntroText());
		response.setUseStatus(memberDto.getUseStatus());
		response.setCreateDate(memberDto.getCreateDate());
		response.setModifyDate(memberDto.getModifyDate());
		return response;
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
