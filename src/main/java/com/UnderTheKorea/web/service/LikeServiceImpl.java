package com.UnderTheKorea.web.service;

import java.sql.SQLException;


import org.springframework.stereotype.Service;

import com.UnderTheKorea.web.domain.Likes;
import com.UnderTheKorea.web.mapper.LikeMapper;
import com.UnderTheKorea.web.necessary.Logging;

import lombok.RequiredArgsConstructor;

@Service("LikeServiceImpl")
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService, Logging{
	
	private final LikeMapper likeMapper;
	
	@Override
	public int doUpdate(Likes inVO) throws SQLException {
		
		return 0;
	}

	@Override
	public int doDelete(Likes inVO) throws SQLException {
		log.debug("┌───────────────────────────────────");
		log.debug("│ LikeServiceImpl() : doDelete ");
		log.debug("│ 1. inVO : " + inVO);
		
		int outVO = likeMapper.doDelete(inVO);
		
		log.debug("│3. outVO : " + outVO + " - 성공(1)/실패(0)");
		log.debug("└───────────────────────────────────");
		
		return outVO;
	}

	@Override
	public int doSave(Likes inVO) throws SQLException {
		log.debug("┌───────────────────────────────────");
		log.debug("│ LikeServiceImpl() : doSave ");
		log.debug("│ 1. inVO : " + inVO);
		
		int outVO = likeMapper.doSave(inVO);
		
		log.debug("│3. outVO : " + outVO + " - 성공(1)/실패(0)");
		log.debug("└───────────────────────────────────");
		
		return outVO;
	}

	@Override
	public Likes doSelectOne(Likes inVO) throws SQLException, NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLikedCheck(int postId, String userId) throws SQLException {
		log.debug("┌───────────────────────────────────");
		log.debug("│ LikeServiceImpl() : isLikedCheck ");
		log.debug("│ 1. postId : " + postId);
		log.debug("│ 2. userId : " + userId);
		
		int outVO = likeMapper.isLikedCheck(postId, userId);
		
		log.debug("│3. outVO : " + outVO + " - 성공(1)/실패(0)");
		log.debug("└───────────────────────────────────");
		
		if(outVO  != 0) {
			boolean isTrue = true;
			return isTrue;
		}else {
			boolean isTrue = false;
			return isTrue;
		}
	}
	

}
