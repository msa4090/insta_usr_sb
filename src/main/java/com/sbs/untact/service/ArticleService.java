package com.sbs.untact.service;

import com.sbs.untact.dao.ArticleDao;
import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.Board;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 판단.

@Service
public class ArticleService {
	
	@Autowired
	private ArticleDao articleDao;
	
	// 삽입
	public ResultData writeArticle(int boardId, int memberId, String title, String body) {
		articleDao.writeArticle(boardId, memberId, title, body);
		int id = articleDao.getLastInsertId();
		
		return new ResultData("S-1", "게시물이 작성되었습니다.", "id", id);
	}
	
	// 조회
	public Article getArticle(int id) {		
		return articleDao.getArticleById(id);
	}
	
	// 삭제
	public ResultData deleteArticle(Integer id) {
		
		if (Util.isEmpty(id)) {
			return new ResultData("F-1", "번호를 입력하세요.");
		}
		
		Article article = articleDao.getArticleById(id);
		
		if (isEmpty(article)) {
			return new ResultData("F-2", "존재하지 않는 글 입니다.");
		}
		
		articleDao.deleteArticleById(id);
		
		
		return new ResultData("S-1", id + "번 게시물이 삭제되었습니다.", "id", id, "boardId", article.getBoardId());		
	}
	
	// 수정
	public ResultData modifyArticle(Integer id, String title, String body) {
		
		if (Util.isEmpty(id)) {
			return new ResultData("F-1", "번호를 입력하세요.");
		} else if (Util.isEmpty(title)) {
			return new ResultData("F-2", "제목을 입력하세요.");			
		} else if (Util.isEmpty(body)) {
			return new ResultData("F-3", "내용을 입력하세요.");
		}
		
		Article article = articleDao.getArticleById(id);
		
		if (isEmpty(article)) {
			return new ResultData("F-4", "존재하지 않는 글 입니다.");
		}
		
		articleDao.modifyArticle(id, title, body);
		
		return new ResultData("S-1", id + "번 게시물이 수정되었습니다.", "id", id);
	}

	private boolean isEmpty(Article article) {
		if (article == null) {
			return true;
		} else if (article.isDelStatus()) {
			return true;
		}
		return false;
	}
	
	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public Board getBoardById(int id) { 
		return articleDao.getBoardById(id);
	}

	public int getArticlesTotalCount(int boardId, String searchKeywordType, String searchKeyword) {
		if(searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}
		return articleDao.getArticlesTotalCount(boardId, searchKeywordType, searchKeyword);
	}

	public List<Article> getForPrintArticles(int boardId, String searchKeywordType, String searchKeyword, int itemsCountInAPage, int page) {
		if(searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}
		
		int limitFrom = (page - 1) * itemsCountInAPage;
		int limitTake = itemsCountInAPage;
		
		return articleDao.getForPrintArticles(boardId, searchKeywordType, searchKeyword, limitFrom, limitTake);
	}
	
}
