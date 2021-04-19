package com.sbs.untact.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.Board;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.service.ArticleService;
import com.sbs.untact.util.Util;

// 삽입, 조회, 수정, 삭제

@Controller
public class MpaUsrArticleController {
	
	@Autowired
	private ArticleService articleService;
		
	@RequestMapping("/mpaUsr/article/doWrite")
	@ResponseBody
	public ResultData doWrite(String title, String body) {
		
		if (Util.isEmpty(title)) {
			return new ResultData("F-1", "제목을 입력하세요.");
		} else if (Util.isEmpty(body)) {
			return new ResultData("F-2", "내용을 입력하세요.");
		}
		
		return articleService.writeArticle(title, body);
	}
	
	@RequestMapping("/mpaUsr/article/getArticle")
	@ResponseBody
	public ResultData getArticle(Integer id) {
		
		if (Util.isEmpty(id)) {
			return new ResultData("F-1", "번호를 입력해주세요.");
		}
		
		Article article = articleService.getArticle(id);
		
		if (Util.isEmpty(article)) {
			return new ResultData("F-2", "존재하지 않는 글 입니다.");
		}
		
		return new ResultData("S-1", article.getId() + "번 글 입니다.", "article", article);
	}
	
	@RequestMapping("/mpaUsr/article/list")	
	public String showList(HttpServletRequest req, int boardId) {
		Board board = articleService.getBoardById(boardId);
				
		if(board == null) {
			return "존재하지 않는 게시판 입니다.";
		}
		
		req.setAttribute("board", board);
		
		return "mpaUsr/article/list";
	}
	
	@RequestMapping("/mpaUsr/article/doDelete")
	@ResponseBody
	public ResultData doDelete(Integer id) {		
		return articleService.deleteArticle(id);
	}
	
	@RequestMapping("mpaUsr/article/doModify")
	@ResponseBody
	public ResultData doModify(Integer id, String title, String body) {
		return articleService.modifyArticle(id, title, body);
	}
	
}
