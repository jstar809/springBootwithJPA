package com.springboot.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.springboot.domain.Board;
import com.springboot.domain.BoardImage;
import com.springboot.domain.QBoard;
import com.springboot.domain.QBoardImage;
import com.springboot.domain.QReply;
import com.springboot.dto.BoardImageDTO;
import com.springboot.dto.BoardListAllReplyDTO;
import com.springboot.dto.BoardListReplyCountDTO;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
	
	Logger log = LogManager.getLogger();
	
	public BoardSearchImpl() {
		super(Board.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Page<Board> search1(Pageable pageable) {
		QBoard board = QBoard.board;
		
		JPQLQuery<Board> query = from(board);
		
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		
		booleanBuilder.or(board.title.contains("11"));
		booleanBuilder.or(board.content.contains("11"));
		
		query.where(booleanBuilder);
		
		query.where(board.bno.gt(0L));
		
		
		getQuerydsl().applyPagination(pageable, query);

		
		
		
		List<Board> list = query.fetch();
		Long count = query.fetchCount();
		
		
		
		
		return null;
		
	}

	@Override
	public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
		
		QBoard board = QBoard.board;
		
		JPQLQuery<Board> query = from(board);
		
		if((types != null && types.length > 0) && keyword != null) {
			
			BooleanBuilder builder = new BooleanBuilder();
			
			for (String type : types) {
				switch (type) {
				case "t":
					builder.or(board.title.contains(keyword));
					break;
				case "c":
					builder.or(board.content.contains(keyword));
					break;
				case "w":
					builder.or(board.writer.contains(keyword));
					break;
				}
			}
			
			query.where(builder);
		}
		
		query.where(board.bno.gt(0L));
		
		getQuerydsl().applyPagination(pageable, query);

		List<Board> list =  query.fetch();
		Long count =  query.fetchCount();
		
		
		
		
		return new PageImpl<>(list , pageable , count);
	}

	@Override
	public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {
		
		QBoard board = QBoard.board;
		QReply reply = QReply.reply;
		
		
		JPQLQuery<Board> query = from(board);
		
		//join
		query.leftJoin(reply).on(board.eq(reply.board));
		
		//where
		if((types != null && types.length > 0) && keyword != null) {
			
			BooleanBuilder builder = new BooleanBuilder();
			for (String type : types) {
				switch (type) {
				case "t":
					builder.or(board.title.contains(keyword));
					break;
				case "c":
					builder.or(board.content.contains(keyword));
					break;
				case "w":
					builder.or(board.writer.contains(keyword));
					break;
				}
			}
			query.where(builder);
		}
		
		query.where(board.bno.gt(0));
		
		
		//group
		query.groupBy(board.bno);
		
		//projection
		JPQLQuery<BoardListReplyCountDTO> resultQuery = query.select(
				Projections.bean(BoardListReplyCountDTO.class
						,board.bno , board.title , board.writer ,board.regDate , reply.count().as("replyCount")));
		
		
		log.info(resultQuery.toString());
		
		//paging
		getQuerydsl().applyPagination(pageable, resultQuery);
		
		
		List<BoardListReplyCountDTO> retultBoardList =  resultQuery.fetch();
		Long total = resultQuery.fetchCount();
		log.info(retultBoardList);
		
		return new PageImpl<>(retultBoardList , pageable , total);
		
		
	}

	@Override
	public Page<BoardListAllReplyDTO> searchWithAll(String[] types, String keyword, Pageable pageable) {
		
		QBoard board = QBoard.board;
		QReply reply = QReply.reply;
		
		JPQLQuery<Board> boardQuery;
		
		//from & join
		boardQuery = from(board)
					.leftJoin(reply)
					.on(board.eq(reply.board))
					;
		
		//where
		boardQuery.where(board.bno.gt(0));
		
		if((types != null && types.length > 0) && keyword != null) {
					
				BooleanBuilder builder = new BooleanBuilder();
				for (String type : types) {
					switch (type) {
					case "t":
						builder.or(board.title.contains(keyword));
						break;
					case "c":
						builder.or(board.content.contains(keyword));
						break;
					case "w":
						builder.or(board.writer.contains(keyword));
						break;
					}
				}
				boardQuery.where(builder);
		}

		//group by
		boardQuery.groupBy(board);
		
		//paging
		getQuerydsl().applyPagination(pageable, boardQuery);
		
		
		//projection
		JPQLQuery<Tuple> tupleQuery =  boardQuery.select(board , reply.rno.count());
		List<Tuple> tupleList = tupleQuery.fetch();
		
		//mapping
		List<BoardListAllReplyDTO> resultList = tupleList.stream().map((tuple)->{
			Board board1 =  tuple.get(board);
			long replyCount = tuple.get(1, Long.class);
			
			List<BoardImageDTO> imageDTO =  board1.getImageSet().stream().sorted()
			.map((image)->{
				return BoardImageDTO.builder()
						.uuid(image.getUuid())
						.fileName(image.getFileName())
						.ord(image.getOrd())
						.build();
			}).collect(Collectors.toList());
			
			return BoardListAllReplyDTO.builder()
					.bno(board1.getBno())
					.title(board1.getTitle())
					.writer(board1.getWriter())
					.regDate(board1.getRegDate())
					.replyCount(replyCount)
					.boardImage(imageDTO)
					.build() ;
		}).collect(Collectors.toList());
		
		long totalCount = tupleQuery.fetchCount();
		
		
		//log
		//List<Board> boardResult = boardQuery.fetch();
		//log.info(boardResult.toString());	
		//boardResult.forEach((b)-> log.info(b.getImageSet()));
			
					
		return new PageImpl<>(resultList ,pageable, totalCount);
		
	}
		
	
	
	

}
