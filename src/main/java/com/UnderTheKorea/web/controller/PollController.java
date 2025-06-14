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
import com.UnderTheKorea.web.necessary.Logging;
import com.UnderTheKorea.web.service.PollServiceImpl;
import com.UnderTheKorea.web.service.VoteServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("poll")
@RequiredArgsConstructor
public class PollController implements Logging {
	
	private final PollServiceImpl pollServiceImpl;
	private final VoteServiceImpl voteServiceImpl;
	
	@PostMapping("/createPoll")
	public int createPoll(@RequestBody Polls inVO) throws SQLException{
		log.debug("inVO : " + inVO);
		
		return this.pollServiceImpl.doSave(inVO);
	}
	
	 @GetMapping("/loadPoll")
	    public ResponseEntity<Map<String, Object>> loadPoll(@RequestParam("post_id") int postId) {
	        try {
	            Polls poll = pollServiceImpl.loadPoll(postId);

	            if (poll != null) {
	                // 투표가 존재할 경우
	                boolean hasVoted = voteServiceImpl.isUserCheck(poll.getPollId(), poll.getUserId());

	                log.debug("poll : " + poll);
	                log.debug("hasVoted : " + hasVoted);

	                Map<String, Object> response = new HashMap<>();
	                response.put("poll", poll);
	                response.put("hasVoted", hasVoted);
	                return new ResponseEntity<>(response, HttpStatus.OK); // 200 OK
	            } else {
	                
	                log.debug("postId {} 에 해당하는 투표가 존재하지 않습니다.", postId);
	                Map<String, Object> errorResponse = new HashMap<>();
	                errorResponse.put("message", "아직 투표가 존재하지 않습니다. 새로운 투표를 시작해보세요.");
	                return new  ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	            }
	        } catch (SQLException e) {
	            log.error("투표 로드 중 SQLException 발생: " + e.getMessage(), e);
	            Map<String, Object> errorResponse = new HashMap<>();
	            errorResponse.put("message", "투표 정보를 불러오는 중 데이터베이스 오류가 발생했습니다.");
	            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
	        } catch (Exception e) {
	            log.error("투표 로드 중 알 수 없는 오류 발생: " + e.getMessage(), e);
	            Map<String, Object> errorResponse = new HashMap<>();
	            errorResponse.put("message", "투표 정보를 불러오는 중 알 수 없는 오류가 발생했습니다.");
	            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
	        }
	    }
	
}
