package com.UnderTheKorea.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
	
	private int serialNo;
	private int grade;
	private String userId;
	private String password;
	private String userName;
	private String createdAt;
	private String updatedAt;
	private boolean  rememberMe; // 로그인 상태 유지
	
	@Override
	public String toString() {
		return "Users [serialNo=" + serialNo + ", grade=" + grade + ", userId=" + userId + ", password=" + password
				+ ", userName=" + userName + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", rememberMe="
				+ rememberMe + ", toString()=" + super.toString() + "]";
	}
	
	
}
