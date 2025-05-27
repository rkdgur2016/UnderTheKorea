package com.UnderTheKorea.web.service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.UnderTheKorea.web.domain.Users;
import com.UnderTheKorea.web.mapper.UserMapper;
import com.UnderTheKorea.web.necessary.Logging;

import lombok.RequiredArgsConstructor;

@Service("userServiceImpl")
@RequiredArgsConstructor  // final 필드를 매개변수로 하는 생성자를 자동으로 생성해줍니다. @Autowired 대신 의존성 주입
public class UserServiceImpl implements UserService, Logging{
	
	private final UserMapper userMapper;
	
	@Override
	public int doUpdate(Users inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doDelete(Users inVO) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doSave(Users inVO) throws SQLException {
		log.debug("┌─────────────────────────────────────────");
		log.debug("│ UserServiceImpl : register ");
		log.debug("└─────────────────────────────────────────");
		
		log.debug("┌ 1. param : " + inVO);
		
		int outVO = userMapper.doSave(inVO);
		log.debug("└ 2. outVO : " + outVO + " - 성공(1)/실패(0)");
		
		return outVO;
	}


	@Override
	public Users doSelectOne(Users inVO) throws SQLException, NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Users login(String userId, String password) {
		log.debug("┌───────────────────────────────────");
		log.debug("│ UserServiceImpl() : login ");
		log.debug("└───────────────────────────────────");
		
		log.debug("┌ 1. id : " + userId); 
		log.debug("│ 2. pw : " + password);
		
		Users outVO = userMapper.login(userId, password);
		log.debug("└ 3. outVO : " + outVO);
		
		return outVO;
	}

	@Override
	public int register(String userId, String password, String userName) {
		log.debug("┌───────────────────────────────────");
		log.debug("│ UserServiceImpl() : register ");
		log.debug("└───────────────────────────────────");
		
		log.debug("┌ 1. id : " + userId); 
		log.debug("│ 2. pw : " + password);
		log.debug("│ 3. userName : " + userName);
		
		int outVO = userMapper.register(userId, password, userName);
		log.debug("└ 3. outVO : " + outVO + " - 성공(1)/실패(0)");
		
		return outVO;
	}

	@Override
	public int idConfirm(String userId) {
		log.debug("┌───────────────────────────────────");
		log.debug("│ UserServiceImpl() : idConfirm ");
		log.debug("└───────────────────────────────────");
		
		log.debug("┌ 1. id : " + userId);
		
		int outVO = userMapper.idConfirm(userId);
		log.debug("└ 2. outVO : " + outVO + " - 성공(1)/실패(0)");
		
		return outVO;
	}


}
