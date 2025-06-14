package com.UnderTheKorea.web.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.UnderTheKorea.web.domain.Polls;
import com.UnderTheKorea.web.necessary.WorkDiv;

@Mapper
public interface PollMapper extends WorkDiv<Polls> {
	
	public Polls loadPoll(int postId) throws SQLException;
	
}
