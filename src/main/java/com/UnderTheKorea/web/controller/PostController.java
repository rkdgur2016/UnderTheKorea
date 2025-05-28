package com.UnderTheKorea.web.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.UnderTheKorea.web.domain.Posts;
import com.UnderTheKorea.web.necessary.Logging;
import com.UnderTheKorea.web.service.PostServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController // RESTful API 컨트롤러임을 나타냅니다.
@RequestMapping("post") // 기본 URL 경로 설정
@RequiredArgsConstructor 
public class PostController implements Logging{
	
	private final PostServiceImpl postServiceImpl;
	
	@GetMapping("/loadPosts")
	public List<Posts> loadPosts(@RequestParam("category") String category) throws SQLException {
		log.debug("category : " + category);
		return this.postServiceImpl.loadPosts(category);
	}
	
}
