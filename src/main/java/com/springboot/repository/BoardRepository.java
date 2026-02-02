package com.springboot.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.domain.Board;
import com.springboot.repository.search.BoardSearch;

public interface BoardRepository extends JpaRepository<Board, Long> , BoardSearch {

	Page<Board> findByTitle(String string, Pageable pageable);

	
	@EntityGraph(attributePaths = {"imageSet"})
	@Query(value = "select bd from Board bd where bd.bno = :bno")
	Optional<Board> findByIdWithImage(@Param("bno") Long bno);
}
