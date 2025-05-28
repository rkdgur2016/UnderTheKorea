package com.UnderTheKorea.web.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.UnderTheKorea.web.domain.Posts;
import com.UnderTheKorea.web.necessary.WorkDiv;

@Mapper
public interface PostMapper extends WorkDiv<Posts> {
	
	public List<Posts> loadPosts(@Param("category") String category) throws SQLException;
}
