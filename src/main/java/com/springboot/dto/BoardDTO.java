package com.springboot.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
	private long bno;
	private String title;
	private String content;
	private String writer;
	
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	
	
}
