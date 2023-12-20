package com.routdoo.dailyroutine.module.routine.repository;

import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDefaultDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.routdoo.dailyroutine.module.routine.domain.DailyRoutine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface DailyRoutineRepository extends JpaRepository<DailyRoutine, Long>, DailyRoutineCustomRepository{

    /**
     * 태그 제일 많은 종류만 조회
     * @return
     * @throws Exception
     */
    @Query(value = "SELECT tag as tag , totCnt as totCnt " +
            "   FROM (" +
            "   SELECT tag, count(*) totCnt" +
            "   FROM daily_routine as dr" +
            "   WHERE range_type = 'PUBLIC'" +
            "   GROUP BY tag" +
            " )T ORDER BY tag DESC LIMIT 10 ",nativeQuery = true)
    List<Map<String,Object>> selectDailyRoutineTagMostList() throws Exception;

    /**
     * 달력에 해당 일정이 있는지 표시하기 위한 목록 조회
     * @return
     * @throws Exception
     */
    @Query(value="SELECT start_date as startDate ,totCnt as totCnt" +
                    " FROM (" +
                    "   SELECT start_date , count(*) totCnt" +
                    "     FROM daily_routine as dr " +
                    "    WHERE start_date like CONCAT(:date,'%') and member_id =:memberId " +
                    "    GROUP BY start_date " +
                    ") T ORDER BY start_date ASC ",nativeQuery = true)
    List<Map<String,Object>> selectDailyRoutineExistList(@Param("date") String date,@Param("memberId") String memberId) throws Exception;

}
