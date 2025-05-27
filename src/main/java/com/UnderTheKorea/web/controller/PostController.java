package com.UnderTheKorea.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UnderTheKorea.web.service.PostServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController // RESTful API 컨트롤러임을 나타냅니다.
@RequestMapping("/philosophy") // 기본 URL 경로 설정
@RequiredArgsConstructor 
public class PostController {
	
	private final PostServiceImpl postServiceImpl;
	
	
	
}
