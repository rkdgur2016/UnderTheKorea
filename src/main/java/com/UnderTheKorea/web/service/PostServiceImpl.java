package com.UnderTheKorea.web.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.UnderTheKorea.web.domain.Posts;
import com.UnderTheKorea.web.mapper.PostMapper;
import com.UnderTheKorea.web.necessary.Logging;

import lombok.RequiredArgsConstructor;

@Service("postServiceImpl")
@RequiredArgsConstructor 
public class PostServiceImpl implements PostService, Logging {

	private final PostMapper postMapper;
	
	@Override
	public int doUpdate(Posts inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(Posts inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doSave(Posts inVO) throws SQLException {
		log.debug("┌───────────────────────────────────");
		log.debug("│ 게시물 service : 게시물 저장 ");
		log.debug("└───────────────────────────────────");
		
		int flag = postMapper.doSave(inVO);
		log.debug("└ flag : " + flag + " - 성공(1)/실패(0)");
		
		return flag;
	}

	@Override
	public Posts doSelectOne(Posts inVO) throws SQLException, NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Posts> loadPosts(@Param("category") String category) throws SQLException {
		log.debug("┌───────────────────────────────────");
		log.debug("│ 게시물 service : 포스팅 불러오기 ");
		log.debug("└───────────────────────────────────");
		
		log.debug("┌ 1. 카테고리 : " + category);
		
		List<Posts> postArr = postMapper.loadPosts(category);
		for(Posts outVO : postArr) {			
			log.debug("└ outVO : " + outVO + " - 성공(1)/실패(0)");
		}
		
		return postArr;
	}
	
}
