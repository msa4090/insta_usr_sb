package com.sbs.untact.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.Board;

// 데이터 저장, 삭제, 수정 등

@Mapper
public interface ArticleDao {		
	void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);
	
	void writeArticle(@Param("boardId") int boardId, @Param("memberId") int memberId, @Param("title") String title, @Param("body") String body);	
	
	Article getArticleById(@Param("id") int id);
	
	int getLastInsertId();
	
	void deleteArticleById(@Param("id") int id);

	Board getBoardById(@Param("id") int id);

	int getArticlesTotalCount(@Param("boardId") int boardId);

	List<Article> getForPrintArticles(@Param("boardId") int boardId, @Param("limitFrom") int limitFrom, @Param("limitTake") int limitTake);	
}
