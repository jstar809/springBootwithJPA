package com.springboot.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageSet")
@Getter
public class Board extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	
	@Column(length = 500 , nullable = false)
	private String title;
	
	@Column(length = 2000 , nullable = false)
	private String content;
	
	@Column(length = 50 , nullable = false)
	private String writer;
	
	@OneToMany(mappedBy = "board" ,fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	@Builder.Default
	private Set<BoardImage> imageSet = new HashSet<>();
	
	public void addImage(String uuid , String FileName ) {
		this.imageSet.add(
			BoardImage.builder()
				.uuid(uuid)
				.fileName(FileName)
				.board(this)
				.ord(imageSet.size())
				.build()
		);
	}
	
	public void clearImages() {
		imageSet.forEach(image -> image.changeBoard(null));
		this.imageSet.clear();
	}
	
	
	public void change ( String title , String content ) {
		this.content = content;
		this.title = title;
		
	}
}
