package com.UnderTheKorea.web.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.UnderTheKorea.web.domain.Votes;
import com.UnderTheKorea.web.necessary.WorkDiv;

@Mapper
public interface VoteMapper extends WorkDiv<Votes>{
	
	public Votes chooseOne(Votes inVO) throws SQLException;
	
	public int isUserCheck(@Param("pollId")int pollId, @Param("userId")String userId) throws SQLException;
}
