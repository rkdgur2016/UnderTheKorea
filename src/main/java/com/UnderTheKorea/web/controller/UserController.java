// src/main/java/com/example/yourprojectname/controller/UserController.java

package com.UnderTheKorea.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UnderTheKorea.web.domain.Users;
import com.UnderTheKorea.web.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController // RESTful API 컨트롤러임을 나타냅니다.
@RequestMapping("/api/users") // 기본 URL 경로 설정
@RequiredArgsConstructor 
public class UserController {
    
	private final UserServiceImpl userServiceImpl; // UserService 주입 (생성자 주입)
    
	@PostMapping("/login")
    public Users login(@RequestBody Users loginRequest) {
        // loginRequest DTO는 userId와 password만 가질 수 있도록 별도로 정의하는 것이 좋습니다.
        Users outVO = userServiceImpl.login(loginRequest.getUserId(),
        										loginRequest.getPassword());
        return outVO;
    }
}