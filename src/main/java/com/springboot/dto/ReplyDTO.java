package com.springboot.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {
	
	private long rno;
	
	@NotNull
	private long bno;
	
	
	@NotNull
	@Size(max = 100)
	private String replyText;
	
	@NotEmpty
	private String replyer;
	
	private LocalDateTime regDate;
	
	private LocalDateTime modDate;
	
	
	
	
	
	
}
