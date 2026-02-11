package com.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String>{
	
	@EntityGraph(attributePaths = {"roleSet"} )
	@Query(value = "select m from Member m where m.mid = :mid and m.social = false")
	public Optional<Member> getWithRole(@Param("mid") String mid);
		
}
