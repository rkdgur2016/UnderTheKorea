package com.UnderTheKorea.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import com.UnderTheKorea.web.dto.LikeDTO;

import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeDTO {

	private Long likeId;
    private Long userId; // 반응한 사용자의 serial_no
    private String targetType;
    private Long targetId;
    private String reactionType;

}