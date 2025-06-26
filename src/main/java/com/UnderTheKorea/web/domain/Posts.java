package com.UnderTheKorea.web.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Posts {
	
	private int postId;
	private String authorId;
	private String title;
	private String category;
	private String content;
	private String imageUrl;
	private String videoUrl;
	private String shortUrl;
	private String createdAt;
	private String updatedAt;
	private long  viewCount;
	private int likeCount;
	
	@Override
	public String toString() {
		return "Posts [postId=" + postId + ", authorId=" + authorId + ", title=" + title + ", category=" + category
				+ ", content=" + content + ", imageUrl=" + imageUrl + ", videoUrl=" + videoUrl + ", shortUrl="
				+ shortUrl + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", viewCount=" + viewCount
				+ ", likeCount=" + likeCount + ", toString()=" + super.toString() + "]";
	}
	
}
