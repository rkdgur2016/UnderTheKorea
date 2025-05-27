package com.UnderTheKorea.web.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.UnderTheKorea.web.domain.Posts;
import com.UnderTheKorea.web.necessary.WorkDiv;

@Mapper
public interface PostMapper extends WorkDiv<Posts> {
	
	
	
}
