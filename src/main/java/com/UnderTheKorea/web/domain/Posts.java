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
	
	private int post_id;
	private int author_id;
	private String title;
	private String category;
	private String content;
	private String image_url;
	private String video_url;
	private String created_at;
	private String updated_at;
	private int view_count;
	
}
