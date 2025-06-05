
package com.UnderTheKorea.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.UnderTheKorea.web.domain.Users;
import com.UnderTheKorea.web.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController // RESTful API 컨트롤러임을 나타냅니다.
@RequestMapping("/api/users") // 기본 URL 경로 설정
@RequiredArgsConstructor 
public class UserController {
    
	private final UserServiceImpl userServiceImpl;// UserService 주입 (생성자 주입)
    
	@PostMapping("/login")
    public Users login(@RequestBody Users loginRequest, HttpSession session) {
        // loginRequest DTO는 userId와 password만 가질 수 있도록 별도로 정의하는 것이 좋습니다.
        Users outVO = userServiceImpl.login(loginRequest.getUserId(),
        										loginRequest.getPassword());
        
        session.setAttribute("user", outVO);
        session.setMaxInactiveInterval(600);
        
        return outVO;
    }
	
	@PostMapping("/register")
	public int register(@RequestBody Users registerRequest) {
		
		int outVO = userServiceImpl.register(registerRequest.getUserId(),
				registerRequest.getPassword(),
				registerRequest.getUserName());
		
		return outVO;
	}
	
	@GetMapping("/idConfirm")
    public int idConfirm(@RequestParam("userId") String userId) {
		
		int outVO = userServiceImpl.idConfirm(userId);
		
		System.out.println("outVO : " + outVO);
        return outVO;
    }
	
}