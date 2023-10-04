package com.routdoo.dailyroutine.module.routine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineInvite;

/**
 * 
* @packageName   : com.routdoo.dailyroutine.module.routine.repository
* @fileName      : DailyRoutineInviteRepository.java
* @author        : Gwang hyeok Go
* @date          : 2023.10.05
* @description   : daily routine 친구 초대
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2023.10.05        ghgo       최초 생성
 */
public interface DailyRoutineInviteRepository extends JpaRepository<DailyRoutineInvite, Long>{

}
