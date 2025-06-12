
package com.UnderTheKorea.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.UnderTheKorea.web.domain.Users;
import com.UnderTheKorea.web.necessary.Logging;
import com.UnderTheKorea.web.service.UserServiceImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/users") 
@RequiredArgsConstructor 
public class UserController  implements Logging{
    
	private final UserServiceImpl userServiceImpl; // UserService 주입 (생성자 주입)
    
	@PostMapping("/login")
    public Users login(@RequestBody Users loginRequest, HttpSession session) {
		
		log.debug("loginRequest : " + loginRequest.getUserId(), loginRequest.getPassword());
		
        Users outVO = userServiceImpl.login(loginRequest.getUserId(), loginRequest.getPassword());
        
        if (outVO != null) {
            session.setAttribute("user", outVO);

            if (loginRequest.isRememberMe()) {
                session.setMaxInactiveInterval(30 * 24 * 60 * 60); // 30일
                log.debug("□ (쿠키) 로그인 상태 유지: 세션 만료 시간 30일로 설정.");
                
            } else {
                session.setMaxInactiveInterval(1800); // 30분
                log.debug("□ (세션) 일반 로그인: 세션 만료 시간 30분으로 설정.");
            }
            
            return outVO;
            
        } else {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    @GetMapping("/me")
    public Users checkSession(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user != null) {
            return user;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다.");
        }
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
		
		log.debug("outVO : " + outVO);
        return outVO;
    }
	
	@PostMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
	    // 1. 세션 무효화
	    HttpSession session = request.getSession(false); // 기존 세션이 없으면 새로 생성하지 않음
	    if (session != null) {
	        session.invalidate(); 
	        log.debug("세션이 무효화되었습니다.");
	    }
	
	    // 2. HTTP Only 쿠키 (예: JSESSIONID 또는 커스텀 인증 쿠키) 제거
	    // 클라이언트에게 해당 쿠키를 삭제하도록 지시합니다.
	    // 쿠키 이름은 실제 프로젝트에서 사용하는 인증 쿠키 이름으로 변경하세요.
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            // 예시: JSESSIONID 쿠키 제거 (Spring Security를 사용한다면 자동으로 처리될 수 있음)
	            if ("JSESSIONID".equals(cookie.getName())) {
	                cookie.setValue(""); // 쿠키 값 비움
	                cookie.setPath("/"); // 모든 경로에 대해 유효하도록 설정
	                cookie.setMaxAge(0); // 쿠키의 유효 기간을 0으로 설정하여 즉시 삭제
	                cookie.setHttpOnly(true); // HTTP Only 설정 유지 (보안)
	                // cookie.setSecure(true); // HTTPS 환경인 경우 필수로 설정
	                response.addCookie(cookie);
	                log.debug("인증 쿠키 '" + cookie.getName() + "'가 제거되었습니다.");
	            }
	        }
	    }
	
	    return "로그아웃 성공";
	}
	
}