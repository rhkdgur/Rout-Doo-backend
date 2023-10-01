package com.routdoo.dailyroutine.auth.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.routdoo.dailyroutine.auth.member.domain.FriendList;

/**
 * 친구목록 Repository
 * @author GAMJA
 *
 */
public interface FriendListRepository extends JpaRepository<FriendList, Long> {

	/**회원기준으로 친구 목록 조회 */
	@EntityGraph(attributePaths = {"member","dailyRoutine"},type = EntityGraphType.LOAD)
	@Query("SELECT f FROM FriendList f where blockYn := blockYn and memberId := memberId")
	List<FriendList> findByBlockYnWithMemberById(String blockYn,String memberId);
}
