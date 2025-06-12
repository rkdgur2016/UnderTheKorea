package com.UnderTheKorea.web.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.UnderTheKorea.web.domain.Comments;
import com.UnderTheKorea.web.necessary.WorkDiv;

public interface CommentService extends WorkDiv<Comments>{
	
	public List<Comments> loadComments(@Param("postId") int postId) throws SQLException;
}
