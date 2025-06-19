package com.UnderTheKorea.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVotes {
	
	private int totalCount;
	private int agreeCount;
	private int disagreeCount;
	
	@Override
	public String toString() {
		return "ResponseVotes [totalCount=" + totalCount + ", agreeCount=" + agreeCount + ", disagreeCount="
				+ disagreeCount + ", toString()=" + super.toString() + "]";
	}
	
}
