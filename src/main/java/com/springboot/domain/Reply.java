package com.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
@Table(name = "Reply" , indexes = {@Index(name="idx_reply_board_bno" ,columnList = "board_bno")} )
public class Reply extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long rno;
	
	@Column
	String replyText;
	
	@Column
	String replyer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	Board board;
	
	public void changeText(String replyText) {
		this.replyText = replyText;
	}
}
