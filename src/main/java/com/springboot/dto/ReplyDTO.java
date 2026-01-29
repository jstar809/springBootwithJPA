package com.springboot.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonFormat(pattern =  "yyyy-MM-dd  HH:mm:ss")
	private LocalDateTime regDate;
	
	@JsonIgnore
	private LocalDateTime modDate;
	
	
	
	
	
	
}
