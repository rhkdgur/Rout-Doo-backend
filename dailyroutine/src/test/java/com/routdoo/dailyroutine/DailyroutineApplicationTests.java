package com.routdoo.dailyroutine;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.routdoo.dailyroutine.module.routine.RoutineServiceResult;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineDto;
import com.routdoo.dailyroutine.module.routine.dto.DailyRoutineTimeLineDto;
import com.routdoo.dailyroutine.module.routine.service.DailyRoutineService;
import com.routdoo.dailyroutine.module.routine.service.RoutineDayType;
import com.routdoo.dailyroutine.module.routine.service.RoutineWriteType;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Rollback(true)
class DailyroutineApplicationTests {
	
	@Autowired
	private DailyRoutineService dailyRoutineService; 

	@Transactional
	@Test
	void dailyRoutineTest() {
		
		//일정 등록
		DailyRoutineDto drDto = new DailyRoutineDto();
		drDto.setStartDate("2023-08-16");//시작일자
		drDto.setEndDate("2023-08-16");//마지막일자
		drDto.setDayType(RoutineDayType.DAY.name());//당일
		
		//타임라인 등록
		DailyRoutineTimeLineDto dtlDto = new DailyRoutineTimeLineDto();
		DailyRoutineTimeLineDto dtlDto2 = new DailyRoutineTimeLineDto();
		
		dtlDto.setApplyDate("2023-08-16");
		dtlDto.setOrd(1);
		dtlDto.setWriteType(RoutineWriteType.DIRECT.name());
		dtlDto.setTitle("test1");
		dtlDto.setContext("test1");
		dtlDto.setPlaceName("테스트1장소");
		dtlDto.setShour("09");
		dtlDto.setSmin("00");
		dtlDto.setEhour("10");
		dtlDto.setEmin("00");
		dtlDto.setCost(10000);
		
		dtlDto2.setApplyDate("2023-08-16");
		dtlDto2.setOrd(2);
		dtlDto2.setWriteType(RoutineWriteType.DIRECT.name());
		dtlDto2.setTitle("test2");
		dtlDto2.setContext("test2");
		dtlDto2.setPlaceName("테스트2장소");
		dtlDto2.setShour("11");
		dtlDto2.setSmin("00");
		dtlDto2.setEhour("12");
		dtlDto2.setEmin("00");
		dtlDto2.setCost(10000);
		
		drDto.getTimeList().add(dtlDto);
		drDto.getTimeList().add(dtlDto2);
		
		try {
			RoutineServiceResult<?> result = dailyRoutineService.insertDailyRoutine(drDto);
			System.out.println("@@@ : "+result.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
