package com.UnderTheKorea.web.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.UnderTheKorea.web.domain.Comments;
import com.UnderTheKorea.web.necessary.WorkDiv;

@Mapper
public interface CommentMapper extends WorkDiv<Comments> {

	public List<Comments> loadComments(@Param("postId") int postId) throws SQLException;
	
}
