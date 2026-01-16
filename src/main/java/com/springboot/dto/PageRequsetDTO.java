package com.springboot.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRequsetDTO {
	
	@Builder.Default
	private int page = 1;

	@Builder.Default
	private int size = 10 ;

	
	private String type;
	
	private String keyword;
	
	private String link;
	
	public String[] getTypes() {
		if(this.type == null || "".equals(this.type)) {
			return null;
		}
		
		return this.type.split("");
		
	}
	/**
	 * 
	 * @param sortProps - 정렬조건
	 * @return Pageable
	 */
	public Pageable getPageable(String ...sortProps) {
		return PageRequest.of(this.page-1, this.size , Sort.by(sortProps).descending());
	}
	
	public String getLink() {
		if( link == null  ) {
			StringBuilder sb = new StringBuilder();
			sb.append("&page=").append(this.page)
				.append("&size=").append(this.size);
			
			if(type != null && type.length() > 0) {
				sb.append("&type=").append(type);
			}
			
			if(keyword != null && keyword.length() > 0) {
				try {
					sb.append("&keyword=").append(URLEncoder.encode(keyword , "UTF-8"));
				} catch (UnsupportedEncodingException e) {
				}
			}
			
			this.link = sb.toString();
		}
		
		return this.link;
		
	}
	
}
