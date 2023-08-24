package com.routdoo.dailyroutine.module.routine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.routdoo.dailyroutine.module.routine.domain.DailyRoutine;

public interface DailyRoutineRepository extends JpaRepository<DailyRoutine, Long>, DailyRoutineCustomRepository{

}
