package com.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberJoinDTO {
	
	private String mid;
	
	private String mpw;

	private String email;
	
	private boolean del;
	
	private boolean social;
}
