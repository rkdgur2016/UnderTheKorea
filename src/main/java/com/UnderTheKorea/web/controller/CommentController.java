package com.UnderTheKorea.web.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UnderTheKorea.web.domain.Comments;
import com.UnderTheKorea.web.necessary.Logging;
import com.UnderTheKorea.web.service.CommentServiceImpl;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController implements Logging {
	
	private final CommentServiceImpl commentServiceImpl;
	
	
	@GetMapping("/loadComment/{postId}")
	public List<Comments> loadComments(@PathVariable("postId")int postId) throws SQLException {
		//@PathVariable은 URL 경로에 포함된 값을 가져옴
		//@RequestParam은 쿼리 문자열 "?"의 뒤에 들어오는 값을 가져옴
		return this.commentServiceImpl.loadComments(postId);
	}
	
	@PostMapping("/addComment")
	public int doSave(@RequestBody Comments inVO) throws SQLException{
		
		return this.commentServiceImpl.doSave(inVO);
	}

	
}
