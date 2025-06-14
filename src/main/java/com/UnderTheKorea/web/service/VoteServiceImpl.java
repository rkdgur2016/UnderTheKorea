package com.UnderTheKorea.web.service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.UnderTheKorea.web.domain.Votes;
import com.UnderTheKorea.web.mapper.VoteMapper;
import com.UnderTheKorea.web.necessary.Logging;

import lombok.RequiredArgsConstructor;

@Service("VoteServiceImpl")
@RequiredArgsConstructor 
public class VoteServiceImpl implements VoteService, Logging {
	
	private final VoteMapper voteMapper;
	
	@Override
	public int doUpdate(Votes inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(Votes inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doSave(Votes inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Votes doSelectOne(Votes inVO) throws SQLException, NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Votes chooseOne(Votes inVO) throws SQLException {
		log.debug("┌───────────────────────────────────");
		log.debug("│ 투표(Vote) service : 투표하기");
		log.debug("└───────────────────────────────────");
		Votes outVO = voteMapper.chooseOne(inVO);
		log.debug("outVO : " + outVO);
		
		return outVO;
	}

	@Override
	public boolean isUserCheck(int pollId, String userId) throws SQLException {
		log.debug("┌───────────────────────────────────────────");
		log.debug("│ 투표(Vote) service : 유저의 투표 여부 확인");
		log.debug("└───────────────────────────────────────────");
		
		int outVO = voteMapper.isUserCheck(pollId, userId);
		
		if(!(outVO  == 0)) {
			boolean isTrue = true;
			return isTrue;
		}else {
			boolean isTrue = false;
			return isTrue;
		}
	}

}
