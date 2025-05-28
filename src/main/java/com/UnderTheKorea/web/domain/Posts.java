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
	private int authorId;
	private String title;
	private String category;
	private String content;
	private String imageUrl;
	private String videoUrl;
	private String createdAt;
	private String updatedAt;
	private int viewCount;
	
}
