package com.UnderTheKorea.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Votes {
	
	private int voteId;
	private int pollId;
	private String userId;
	private int selectedOption;
	private String votedAt;
	
	@Override
	public String toString() {
		return "Votes [voteId=" + voteId + ", pollId=" + pollId + ", userId=" + userId + ", selectedOption="
				+ selectedOption + ", votedAt=" + votedAt + ", toString()=" + super.toString() + "]";
	}
	
}
