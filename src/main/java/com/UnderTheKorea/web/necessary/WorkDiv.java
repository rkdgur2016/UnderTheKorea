package com.UnderTheKorea.web.necessary;

import java.sql.SQLException;
import java.util.List;

public interface WorkDiv<T> {
	
	/**
	 * 회원 수정
	 */
	int doUpdate(T inVO) throws SQLException;

	/**
	 * 단건 삭제
	 */
	int doDelete(T inVO) throws SQLException;

	/**
	 * 단건 등록
	 */
	int doSave(T inVO) throws SQLException;

	/**
	 * 회원 정보 단건 조회
	 */
	T doSelectOne(T inVO) throws SQLException, NullPointerException;
 
}
