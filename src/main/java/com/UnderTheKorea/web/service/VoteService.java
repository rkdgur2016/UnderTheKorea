package com.UnderTheKorea.web.service;

import java.sql.SQLException;

import com.UnderTheKorea.web.domain.ResponseVotes;
import com.UnderTheKorea.web.domain.Votes;
import com.UnderTheKorea.web.necessary.WorkDiv;

public interface VoteService extends WorkDiv<Votes> {
	
	public int chooseOne(Votes inVO) throws SQLException;
	
	public boolean isUserCheck(int pollId, String userId) throws SQLException;
	
	public ResponseVotes loadVote(int pollId) throws SQLException;
}
