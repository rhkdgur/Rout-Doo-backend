package com.routdoo.dailyroutine.module.routine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.routdoo.dailyroutine.module.routine.domain.DailyRoutineTimeLine;

public interface DailyRoutineTimeLineRepository extends JpaRepository<DailyRoutineTimeLine, Long>{

}
