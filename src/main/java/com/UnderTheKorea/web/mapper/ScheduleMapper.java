package com.UnderTheKorea.web.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.UnderTheKorea.web.domain.Schedule;
import com.UnderTheKorea.web.necessary.WorkDiv;

@Mapper
public interface ScheduleMapper extends WorkDiv<Schedule>{
	
	List<Schedule> loadSchedule() throws SQLException;
}
