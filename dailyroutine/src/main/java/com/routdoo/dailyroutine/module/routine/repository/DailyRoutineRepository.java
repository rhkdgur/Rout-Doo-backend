package com.routdoo.dailyroutine.module.routine.repository;

import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.routdoo.dailyroutine.module.routine.domain.DailyRoutine;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface DailyRoutineRepository extends JpaRepository<DailyRoutine, Long>, DailyRoutineCustomRepository{

    /**
     * 태그 제일 많은 종류만 조회
     * @return
     * @throws Exception
     */
    @Query(value = "SELECT new Map(tag,totCnt)" +
            "   FROM (" +
            "   SELECT tag, count(*) totCnt" +
            "   FROM daily_routine as dr" +
            "   WHERE public_yn = 'Y'" +
            "   GROUP BY tag" +
            " )T ORDER BY tag DESC LIMIT 10 ",nativeQuery = true)
    List<Map<String,String>> selectDailyRoutineTagMostList() throws Exception;

}
