package com.sbs.untact.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	private String msgAndBack(HttpServletRequest req, String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "common/redirect";
	}
	
	private String msgAndReplace(HttpServletRequest req, String msg, String replaceUrl) {
		req.setAttribute("msg", msg);
		req.setAttribute("replaceUrl", replaceUrl);
		
		return "common/redirect";
	}
		
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
	public String showList(HttpServletRequest req, int boardId, @RequestParam(defaultValue="1") int page) {
		Board board = articleService.getBoardById(boardId);
				
		if(board == null) {
			return msgAndBack(req, boardId + "번 게시판이 존재하지 않습니다.");
		}
		
		req.setAttribute("board", board);
		
		int totalItemsCount = articleService.getArticlesTotalCount(boardId);
		
		req.setAttribute("totalItemsCount", totalItemsCount);
		
		// 한 페이지에 보여줄 수 있는 게시물 최대 개수
		int itemsCountInAPage = 20;
		// 총 페이지 수, ceil = 나머지 올림(ceil, round, floor)
		int totalPage = (int) Math.ceil(totalItemsCount / (double) itemsCountInAPage);
		
		// 현재 페이지(임시)		
		req.setAttribute("page", page);
		req.setAttribute("totalPage", totalPage);
		
		List<Article> articles = articleService.getForPrintArticles(boardId, itemsCountInAPage, page);
		
		req.setAttribute("articles", articles);
		
		return "mpaUsr/article/list";
	}	

	@RequestMapping("/mpaUsr/article/doDelete")	
	public String doDelete(HttpServletRequest req, Integer id) {		
		if (Util.isEmpty(id)) {
			return msgAndBack(req, "id를 입력해주세요.");
		}
		
		ResultData rd = articleService.deleteArticle(id);
		
		if (rd.isFail()) {
			return msgAndBack(req, rd.getMsg()); 
		}
		
		String replaceUrl = "../article/list?boardId=" + rd.getBody().get("boardId");
		
		return msgAndReplace(req, rd.getMsg(), replaceUrl);
	}

	@RequestMapping("mpaUsr/article/doModify")
	@ResponseBody
	public ResultData doModify(Integer id, String title, String body) {
		return articleService.modifyArticle(id, title, body);
	}
	
}
