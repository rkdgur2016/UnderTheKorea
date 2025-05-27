package com.UnderTheKorea.web.service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.UnderTheKorea.web.domain.Posts;
import com.UnderTheKorea.web.mapper.PostMapper;

import lombok.RequiredArgsConstructor;

@Service("postServiceImpl")
@RequiredArgsConstructor 
public class PostServiceImpl implements PostService {

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Posts doSelectOne(Posts inVO) throws SQLException, NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

}
