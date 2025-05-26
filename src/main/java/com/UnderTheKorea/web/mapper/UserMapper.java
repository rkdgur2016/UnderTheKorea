package com.UnderTheKorea.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.UnderTheKorea.web.domain.Users;
import com.UnderTheKorea.web.necessary.WorkDiv;

@Mapper
public interface UserMapper extends WorkDiv<Users> {

	public Users login(@Param("userId") String userId, @Param("password") String password);

	
}
