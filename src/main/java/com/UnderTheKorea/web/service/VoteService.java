package com.UnderTheKorea.web.service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.UnderTheKorea.web.domain.Votes;
import com.UnderTheKorea.web.necessary.Logging;
import com.UnderTheKorea.web.necessary.WorkDiv;

public interface VoteService extends WorkDiv<Votes> {
	
	public Votes chooseOne(Votes inVO) throws SQLException;
	
	public boolean isUserCheck(int pollId, String userId) throws SQLException;
}
