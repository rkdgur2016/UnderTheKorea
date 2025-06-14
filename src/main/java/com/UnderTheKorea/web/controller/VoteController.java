package com.UnderTheKorea.web.controller;

import java.sql.SQLException;

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
	
	@PostMapping("/chooseVote")
	public Votes selectVote(@RequestBody Votes inVO) throws SQLException{
		log.debug("inVO : " + inVO);
		
		return  this.voteServiceImpl.chooseOne(inVO);
	};
}
