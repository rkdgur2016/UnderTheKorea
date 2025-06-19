package com.UnderTheKorea.web.service;

import org.apache.ibatis.annotations.Param;

import com.UnderTheKorea.web.domain.Users;
import com.UnderTheKorea.web.necessary.WorkDiv;

public interface UserService extends WorkDiv<Users> {
	
	public Users login(@Param("userId") String userId, @Param("password") String password);
	
	public int register(@Param("userId") String userId, @Param("password") String password, @Param("username") String userName);
	
	public int idConfirm(@Param("userId") String userId);
	
}

