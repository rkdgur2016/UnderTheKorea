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
	
}
