package com.UnderTheKorea.web.service;

import com.UnderTheKorea.web.domain.Users;
import com.UnderTheKorea.web.necessary.WorkDiv;

public interface UserService extends WorkDiv<Users> {
	
	public Users login(String userId, String password);
}
