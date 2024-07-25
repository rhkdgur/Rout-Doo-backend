package com.routdoo.dailyroutine.auth.member.repository;

import java.util.List;

import org.springframework.data.domain.Page;

import com.routdoo.dailyroutine.auth.member.dto.MemberMyspotDefaultDto;
import com.routdoo.dailyroutine.auth.member.dto.MemberMyspotDto;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.auth.member.repository
* @fileName      : MemberMyspotCustomRepository.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.18
* @description   : 나만의 장소 interface
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.18        ghgo       최초 생성
 */
public interface MemberMyspotCustomRepository {

	/**
	 * 나만의 장소 목록 조회
	 * @return
	 * @throws Exception
	 */
	Page<MemberMyspotDto> selectMemberMyspotList(MemberMyspotDefaultDto searchDto) throws Exception;
	
	/**
	 * 나만의 장소 목록 조회(NOLIMIT)
	 * @param searchDto
	 * @return
	 * @throws Exception
	 */
	List<MemberMyspotDto> selectMemberMyspotNolimitList(MemberMyspotDefaultDto searchDto) throws Exception;
	
	/**
	 * 나만의 장소 상세 조회
	 * @return
	 * @throws Exception
	 */
	MemberMyspotDto selectMemberMyspot(MemberMyspotDto dto) throws Exception;
}
