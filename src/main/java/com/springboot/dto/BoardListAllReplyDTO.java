package com.springboot.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardListAllReplyDTO {

	private Long bno;
	
	private String title;
	
	private String writer;
	
	private LocalDateTime regDate;
	
	private Long replyCount;
	
	
	private List<BoardImageDTO> boardImage;
}
