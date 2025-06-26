package com.UnderTheKorea.web.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.UnderTheKorea.web.domain.Polls;
import com.UnderTheKorea.web.domain.ResponseVotes;
import com.UnderTheKorea.web.necessary.Logging;
import com.UnderTheKorea.web.service.LikeServiceImpl;
import com.UnderTheKorea.web.service.PollServiceImpl;
import com.UnderTheKorea.web.service.VoteServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("poll")
@RequiredArgsConstructor
public class PollController implements Logging {
	
	private final PollServiceImpl pollServiceImpl;
	private final VoteServiceImpl voteServiceImpl;
	private final LikeServiceImpl likeServiceImpl;
		
	@PostMapping("/createPoll")
	public int createPoll(@RequestBody Polls inVO) throws SQLException{
		log.debug("inVO : " + inVO);
		
		return this.pollServiceImpl.doSave(inVO);
	}
	
	@GetMapping("/loadPoll")
	public ResponseEntity<Map<String, Object>> loadPoll(
									@RequestParam("post_id") int postId,
									@RequestParam(value = "user_id", required = false) String userId) throws SQLException {

	    Map<String, Object> response = new HashMap<>();
	    
	    Polls poll = pollServiceImpl.loadPoll(postId);
	    boolean hasLiked = likeServiceImpl.isLikedCheck(postId, userId);

	    response.put("poll", poll);
	    response.put("hasLiked", hasLiked);

	    if (poll == null) {
	        log.debug("postId {} 에 해당하는 투표가 존재하지 않습니다.", postId);
	        response.put("message", "아직 투표가 존재하지 않습니다. 새로운 투표를 시작해보세요.");
	        log.debug("response : {}", response);
	        return ResponseEntity.ok(response);
	    } 
	    
	    try {
	        ResponseVotes vote = voteServiceImpl.loadVote(poll.getPollId());
	        boolean hasVoted = voteServiceImpl.isUserCheck(poll.getPollId(), userId);

	        log.debug("hasVoted : {}", hasVoted);
	        log.debug("vote : {}", vote);

	        response.put("hasVoted", hasVoted);
	        response.put("vote", vote);

	    } catch (Exception e) {
	        log.error("투표 로드 중 알 수 없는 오류 발생: " + e.getMessage(), e);
	        log.debug("response : {}", response);
	        response.put("message", "투표 정보를 불러오는 중 알 수 없는 오류가 발생했습니다.");
	    }
	    
	    log.debug("response : {}", response);
	    return ResponseEntity.ok(response);
	}

}
