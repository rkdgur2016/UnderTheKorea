package com.UnderTheKorea.web.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.UnderTheKorea.web.domain.Schedule;
import com.UnderTheKorea.web.mapper.ScheduleMapper;
import com.UnderTheKorea.web.necessary.Logging;

import lombok.RequiredArgsConstructor;

@Service("scheduleServiceImpl")
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService, Logging{
	
	private final ScheduleMapper scheduleMapper;

	@Override
	public int doUpdate(Schedule inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(Schedule inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doSave(Schedule inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Schedule doSelectOne(Schedule inVO) throws SQLException, NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Schedule> loadSchedule() throws SQLException {
		
		List<Schedule> outVO = scheduleMapper.loadSchedule();
		
		for(Schedule schedule : outVO) {
			log.debug("스케쥴 : {}" ,  schedule);
		};
		
		log.debug("스케쥴 갯수 : {}", outVO.size());
		
		return outVO;
	}
	
	
}
