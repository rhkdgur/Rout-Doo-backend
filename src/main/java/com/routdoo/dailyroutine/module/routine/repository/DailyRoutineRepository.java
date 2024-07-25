package com.routdoo.dailyroutine.module.routine.repository;

import com.routdoo.dailyroutine.module.routine.domain.DailyRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
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
    @Query(value = " SELECT tag as tag , count(*) as totCnt" +
            " FROM (" +
            "               SELECT" +
            " SUBSTRING_INDEX(SUBSTRING_INDEX(dr.tag, \",\", num.n), \",\" , -1) AS tag" +
            "                FROM (" +
            " select 1 as n union all" +
            " select 2 union all" +
            " select 3 union all" +
            " select 4 union all" +
            " select 5" +
            "                ) AS num" +
            "  inner join daily_routine dr" +
            "    ON (CHAR_LENGTH(dr.tag) - CHAR_LENGTH(replace(dr.tag, \",\", \"\")) >= num.n - 1 ) AND  dr.range_type = 'PUBLIC'  " +
            " )T " +
            " WHERE tag != '' " +
            " GROUP BY tag " +
            " ORDER BY totCnt DESC , tag ASC " +
            " LIMIT 10",nativeQuery = true)
    List<Map<String,Object>> selectDailyRoutineTagMostList() throws Exception;

    /**
     * 달력에 해당 일정이 있는지 표시하기 위한 목록 조회
     * @return
     * @throws Exception
     */
    @Query(value="SELECT start_date as startDate , end_date as endDate ,totCnt as totCnt" +
                    " FROM (" +
                    "   SELECT start_date, end_date , count(*) totCnt" +
                    "     FROM daily_routine as dr " +
                    "    WHERE start_date like CONCAT(:date,'%') and member_id =:memberId " +
                    "    GROUP BY start_date, end_date " +
                    ") T ORDER BY start_date ASC ",nativeQuery = true)
    List<Map<String,Object>> selectDailyRoutineExistList(@Param("date") String date,@Param("memberId") String memberId) throws Exception;

}
