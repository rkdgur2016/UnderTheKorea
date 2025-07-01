package com.UnderTheKorea.web.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UnderTheKorea.web.domain.Schedule;
import com.UnderTheKorea.web.necessary.Logging;
import com.UnderTheKorea.web.service.ScheduleServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController("schedule")
@RequiredArgsConstructor
public class ScheduleController implements Logging{

	private final ScheduleServiceImpl scheduleServiceImpl;
	
	@GetMapping("/loadSchedule")
	public List<Schedule> loadSchedule() throws SQLException{
		
		log.debug("스케쥴 로드 메서드 호출");
		
		return this.scheduleServiceImpl.loadSchedule();
	};
	
}
