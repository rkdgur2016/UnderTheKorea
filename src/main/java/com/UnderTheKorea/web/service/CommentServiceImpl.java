package com.UnderTheKorea.web.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.UnderTheKorea.web.domain.Comments;
import com.UnderTheKorea.web.mapper.CommentMapper;
import com.UnderTheKorea.web.necessary.Logging;

import lombok.RequiredArgsConstructor;

@Service("commentServiceImpl")
@RequiredArgsConstructor 
public class CommentServiceImpl implements CommentService, Logging {

	private final CommentMapper commentMapper;
	
	@Override
	public int doUpdate(Comments inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(Comments inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doSave(Comments inVO) throws SQLException {
		log.debug("┌───────────────────────────────────────────────");
		log.debug("│ 댓글 Service : " + inVO + " 댓글 추가 ");
		log.debug("└───────────────────────────────────────────────");
		
		int flag = commentMapper.doSave(inVO);
		
		log.debug("□ flag : " + flag + "(1:성공, 0:실패)");
		
		return flag;
	}

	@Override
	public Comments doSelectOne(Comments inVO) throws SQLException, NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comments> loadComments(@Param("postId") int postId) throws SQLException{
		log.debug("┌─────────────────────────────────────────");
		log.debug("│ 댓글 Service :  전체 댓글 불러오기 ");
		log.debug("│ 1. postId : " + postId);
		log.debug("└─────────────────────────────────────────");
		
		List<Comments> allComments = commentMapper.loadComments(postId);
		
		for(Comments comment : allComments) {
			log.debug("□ comment : " + comment.toString());
		}
		
		return allComments;
	}

}
