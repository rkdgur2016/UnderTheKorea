package com.UnderTheKorea.web.service;

import java.sql.SQLException;

import com.UnderTheKorea.web.domain.Polls;
import com.UnderTheKorea.web.necessary.WorkDiv;

public interface PollService extends WorkDiv<Polls>{
	
	public Polls loadPoll(int postId) throws SQLException;
	
}
