package com.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.domain.Board;
import com.springboot.repository.search.BoardSearch;

public interface BoardRepository extends JpaRepository<Board, Long> , BoardSearch {

	Page<Board> findByTitle(String string, Pageable pageable);

}
