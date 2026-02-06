package com.springboot.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

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
	
	@NotNull
	@Size(min = 3 , max = 100)
	private String title;
	
	@NotEmpty
	private String content;
	
	@NotEmpty
	private String writer;
	
	private List<String> fileNames;
	
	private LocalDateTime regDate;
	
	private LocalDateTime modDate;
	
	
}
