package com.UnderTheKorea.web.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.UnderTheKorea.web.domain.Likes;
import com.UnderTheKorea.web.necessary.WorkDiv;

@Mapper
public interface LikeMapper extends WorkDiv<Likes>{

	public int isLikedCheck(@Param("postId") int postId,
							@Param("userId") String userId) throws SQLException;
	
	public int doSave(Likes inVO) throws SQLException;
	
}
