package com.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
	
	//Page<Reply> findByBoard_Bno(Long bno, Pageable pageable); 랑 동일 
	@Query("select r from Reply r where r.board.bno = :bno " )
	public Page<Reply> listOfBoard(@Param("bno") Long bno , Pageable pageable);
	
	public void deleteByBoard_Bno(Long bno);
	
}
