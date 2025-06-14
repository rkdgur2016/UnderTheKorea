package com.UnderTheKorea.web.service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.UnderTheKorea.web.domain.Polls;
import com.UnderTheKorea.web.mapper.PollMapper;
import com.UnderTheKorea.web.mapper.PostMapper;
import com.UnderTheKorea.web.necessary.Logging;

import lombok.RequiredArgsConstructor;

@Service("pollService")
@RequiredArgsConstructor 
public class PollServiceImpl implements PollService, Logging {
	
	
	private final PollMapper pollmapper;
	
	@Override
	public int doUpdate(Polls inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(Polls inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doSave(Polls inVO) throws SQLException {
		log.debug("┌───────────────────────────────────");
		log.debug("│ 투표 service : 투표 생성");
		log.debug("└───────────────────────────────────");
		
		int flag = pollmapper.doSave(inVO);
		log.debug("└ flag : " + flag + " - 성공(1)/실패(0)");
		
		return flag;
	}

	@Override
	public Polls doSelectOne(Polls inVO) throws SQLException, NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Polls loadPoll(int postId) throws SQLException {
		log.debug("┌───────────────────────────────────");
		log.debug("│ 투표 service : 투표 불러오기");
		log.debug("└───────────────────────────────────");
		
		Polls outVO = pollmapper.loadPoll(postId);
		log.debug("└ outVO : " + outVO);
		
		return outVO;
	}

}
