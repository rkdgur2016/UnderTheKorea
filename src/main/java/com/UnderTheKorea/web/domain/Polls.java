package com.UnderTheKorea.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Polls {
	
	private int pollId;
	private int postId;
	private String userId; //생성한 유저 ID
	private String title;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private String createdAt;
	private String updatedAt;
	
	@Override
	public String toString() {
		return "Polls [pollId=" + pollId + ", postId=" + postId + ", userId=" + userId + ", title=" + title
				+ ", option1=" + option1 + ", option2=" + option2 + ", option3=" + option3 + ", option4=" + option4
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", toString()=" + super.toString() + "]";
	}
	
}
