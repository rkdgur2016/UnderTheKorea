package com.UnderTheKorea.web.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.UnderTheKorea.web.dto.PostDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
	
	@Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PostgreSQL의 SERIAL에 해당
    @Column(name = "serial_no") // 컬럼 이름
    private Long postId;
	
    private Long authorId; // 작성자의 serial_no
    private String title;
    private String category;
    private String content;
    private String imageUrl;
    private String videoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer viewCount;

}