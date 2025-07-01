package com.UnderTheKorea.web.service;

import java.sql.SQLException;
import java.util.List;

import com.UnderTheKorea.web.domain.Schedule;
import com.UnderTheKorea.web.necessary.Logging;
import com.UnderTheKorea.web.necessary.WorkDiv;


public interface ScheduleService extends WorkDiv<Schedule>, Logging{
	
	public List<Schedule> loadSchedule() throws SQLException;
}
