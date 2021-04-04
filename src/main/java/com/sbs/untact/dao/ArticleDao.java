package com.sbs.untact.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sbs.untact.dto.Article;
import com.sbs.untact.util.Util;

// 데이터 저장, 삭제, 수정 등

@Component
public class ArticleDao {
	
	public List<Article> articles;
	public int articleLastId;
	
	public ArticleDao() {		
		articles = new ArrayList<>();
		articleLastId = 0;
	}
	
	public int writeArticle(String title, String body) {			
		int id = articleLastId + 1;
		String regDate = Util.getNowDateStr();
		String updateDate = Util.getNowDateStr();
		
		Article article = new Article(title, body, regDate, updateDate, id);
		articles.add(article);
		
		articleLastId = id;		
		
		return id;
	}
	
	public Article getArticle(int id) {
		
		for(Article article: articles) {
			if(article.getId() == id) {
				return article;
			}
		}
		
		return null;
	}
	
	public void deleteArticleById(int id) {		
		Article article = getArticle(id);
		
		articles.remove(article);
	}
	
	public void modifyArticleById(int id, String title, String body) {		
		Article article = getArticle(id);				
		
		article.setTitle(title);
		article.setBody(body);
		article.setUpdateDate(Util.getNowDateStr());	
	}

}
