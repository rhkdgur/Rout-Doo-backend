package com.routdoo.dailyroutine.auth.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.routdoo.dailyroutine.auth.member.domain.MemberFriends;

/**
 * 친구목록 Repository
 * @author GAMJA
 *
 */
public interface FriendListRepository extends JpaRepository<MemberFriends, Long> {

	/**친구 목록 차단 여부 및 회원 정보로 조회*/
	List<MemberFriends> findByBlockYnAndMember_Id(String blockYn,String memberId);
}
