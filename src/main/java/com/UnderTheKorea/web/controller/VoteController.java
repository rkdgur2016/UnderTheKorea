package com.UnderTheKorea.web.controller;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UnderTheKorea.web.domain.Votes;
import com.UnderTheKorea.web.necessary.Logging;
import com.UnderTheKorea.web.service.VoteServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("vote")
@RequiredArgsConstructor
public class VoteController implements Logging{
	
	private final VoteServiceImpl voteServiceImpl;
	
	@PostMapping("/castVote")
	public int castVote(@RequestBody Votes inVO) throws SQLException{
		log.debug("inVO : " + inVO);
		
		try {
	        return this.voteServiceImpl.chooseOne(inVO);
	    } catch (SQLException e) {
	        log.error("SQL 예외 발생: {}", e.getMessage());
	    } catch (Exception e) {
	        log.error("예기치 않은 예외 발생: {}", e.getMessage());
	    }
		
	    return 0;
	};
}
