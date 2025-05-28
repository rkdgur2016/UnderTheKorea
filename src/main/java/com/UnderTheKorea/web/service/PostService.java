package com.UnderTheKorea.web.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.UnderTheKorea.web.domain.Posts;
import com.UnderTheKorea.web.necessary.WorkDiv;

public interface PostService extends WorkDiv<Posts> {
	
	public List<Posts> loadPosts(@Param("category") String category) throws SQLException;
}
