package com.springboot.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString(exclude = "board")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardImage extends BaseEntity implements Comparable<BoardImage> {

	@Id
	private String uuid;
	
	private String fileName;
	
	private int ord;
	
	@ManyToOne
	private Board board;
	

	@Override
	public int compareTo(BoardImage o) {
	
		return this.ord - o.ord;
	}
	
	public void changeBoard (Board changeBoard) {
		this.board = changeBoard;
	}
	
	
	
}
