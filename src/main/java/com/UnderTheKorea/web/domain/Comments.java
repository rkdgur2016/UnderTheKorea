package com.UnderTheKorea.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
	
	private int commentId;
	private int postId;
	private String authorId;
	private int parentCommentId;
	private String content;
	private String createdAt;
	private String updatedAt;
	
	@Override
	public String toString() {
		return "Comments [commentId=" + commentId + ", postId=" + postId + ", authorId=" + authorId
				+ ", parentCommentId=" + parentCommentId + ", content=" + content + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
