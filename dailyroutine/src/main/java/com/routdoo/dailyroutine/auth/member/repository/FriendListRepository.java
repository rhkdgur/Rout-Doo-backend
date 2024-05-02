package com.routdoo.dailyroutine.auth.member.repository;

import com.routdoo.dailyroutine.auth.member.domain.MemberFriends;
import com.routdoo.dailyroutine.auth.member.dto.MemberFriendsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 친구목록 Repository
 * @author GAMJA
 *
 */
public interface FriendListRepository extends JpaRepository<MemberFriends, Long> {

	/**친구 목록 차단 여부 및 회원 정보로 조회*/
	@Query("SELECT com.routdoo.dailyroutine.auth.member.dto.MemberSummaryResponse(" +
			"	mf.idx, mf.dailyIdx, mf.blockYn, m.nickname, m.gender, m.age, m.mbti" +
			")  FROM MemberFriends mf " +
			" JOIN mf.member m " +
			" WHERE mf.blockYn =:blockYn and mf.member.id =:memberId ")
	List<MemberFriendsResponse> findByBlockYnAndMember_Id(String blockYn, String memberId);
}
