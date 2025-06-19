package com.UnderTheKorea.web.controller;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UnderTheKorea.web.domain.Likes;
import com.UnderTheKorea.web.necessary.Logging;
import com.UnderTheKorea.web.service.LikeServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("like")
@RequiredArgsConstructor
public class LikeController implements Logging {
	
	private final LikeServiceImpl likeServiceImpl;
	
	@PostMapping("/like")
	public int like(@RequestBody Likes inVO) throws SQLException {
		log.debug("inVO : " + inVO);
		return this.likeServiceImpl.doSave(inVO);
	}; 
	
	@PostMapping("/dislike")
	public int dislike(@RequestBody Likes inVO) throws SQLException {
		log.debug("inVO : " + inVO);
		return this.likeServiceImpl.doDelete(inVO);
	}; 
}
