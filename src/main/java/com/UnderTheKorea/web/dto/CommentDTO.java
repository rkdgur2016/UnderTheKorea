package com.UnderTheKorea.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.UnderTheKorea.web.dto.CommentDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    
	private Long commentId;
    private Long postId;
    private Long authorId;
    private Long parentCommentId;
    private String content;	
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
}