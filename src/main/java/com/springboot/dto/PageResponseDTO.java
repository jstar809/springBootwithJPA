package com.springboot.dto;

import java.util.List;

import com.springboot.domain.Board;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageResponseDTO<E> {
	
	private int page; //현재 페이지 
	private int size; //페이지당 노출 게시글
	private int total; // 전체 게시글
	
	private int start; //페이지 시작 숫자
	private int end;  // 페이지 마지막 숫자
	 
	private boolean prev;  // 이전페이지 존재 여부
	private boolean next; 	// 다음페이지 존재여부
	
	private List<E> dtoList; //데이터
	
	@Builder(builderMethodName = "withAll")
	public PageResponseDTO (PageRequsetDTO pageRequsetDTO , List<E> dtoList , int total){
		this.page =  pageRequsetDTO.getPage();
		this.size =  pageRequsetDTO.getSize();
		
		this.total = total;
		this.dtoList = dtoList;
		
		this.end = (int)(Math.ceil(this.page / 10.0)) * 10;
		this.start = end -10 +1;
		
		int last = (int)(Math.ceil( total/(double)size));
		
		this.end = end > last ? last : end;
		
		if(last ==0) {
			end = 1;
		}
		
		this.prev = this.start > 1;
		this.next = total> this.end * this.size;
		
	}
	
}
