package com.UnderTheKorea.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Likes {
	
	private String userId;
	private int postId;
	private int likeTotalCount;
	
	@Override
	public String toString() {
		return "Likes [userId=" + userId + ", postId=" + postId + ", likeTotalCount=" + likeTotalCount + ", toString()="
				+ super.toString() + "]";
	}
}
