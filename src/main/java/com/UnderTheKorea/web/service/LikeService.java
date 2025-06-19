package com.UnderTheKorea.web.service;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Param;

import com.UnderTheKorea.web.domain.Likes;
import com.UnderTheKorea.web.necessary.WorkDiv;

public interface LikeService extends WorkDiv<Likes>{
	
	public boolean isLikedCheck(@Param("postId") int postId,
								@Param("userId") String userId) throws SQLException;
	
}
