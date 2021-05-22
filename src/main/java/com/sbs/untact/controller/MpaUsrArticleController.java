package com.sbs.untact.controller;

import com.sbs.untact.dto.Article;
import com.sbs.untact.dto.Board;
import com.sbs.untact.dto.Req;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.service.ArticleService;
import com.sbs.untact.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

// 무엇을 만들기 전에 요구사항과 할 일 리스트를 먼저 만들고 작업할 것(메모장 등)
// 새로운 것을 적용하기 위해 미니 프로젝트를 따로 만들어서 테스트 하기

@Controller
@Slf4j
public class MpaUsrArticleController {
	
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/mpaUsr/article/detail")
	public String showDetail(HttpServletRequest req, int id) {

		Article article = articleService.getForPrintArticleById(id);

		if(article == null) {
			return Util.msgAndBack(req, id + "번 게시판이 존재하지 않습니다.");
		}

		Board board = articleService.getBoardById(article.getBoardId());

		req.setAttribute("article", article);
		req.setAttribute("board", board);

		return "mpaUsr/article/detail";
	}

	@RequestMapping("/mpaUsr/article/write")
	public String showWrite(HttpServletRequest req, @RequestParam(defaultValue="1") int boardId) {
		Req rq = (Req)req.getAttribute("req");

		if(rq.isNotLogined()) {
			return Util.msgAndBack(req, "로그인 후 이용해주세요.");
		}

		Board board = articleService.getBoardById(boardId);

		if(board == null) {
			return Util.msgAndBack(req, boardId + "번 게시판이 존재하지 않습니다.");
		}

		req.setAttribute("board", board);

		return "mpaUsr/article/write";
	}
		
	@RequestMapping("/mpaUsr/article/doWrite")
	public String doWrite(HttpServletRequest req, int boardId, String title, String body) {
		Req rq = (Req)req.getAttribute("req");

		if(rq.isNotLogined()) {
			return Util.msgAndBack(req, "로그인 후 이용해주세요.");
		}
		
		if (Util.isEmpty(title)) {
			return Util.msgAndBack(req, "제목을 입력하세요.");
		} else if (Util.isEmpty(body)) {
			return Util.msgAndBack(req, "내용을 입력하세요.");
		}

		int memberId = 3; // 임시 데이터
		ResultData writeArticleRd =  articleService.writeArticle(boardId, memberId, title, body);

		if(writeArticleRd.isFail()) {
			return Util.msgAndBack(req, writeArticleRd.getMsg());
		}

		String replaceUrl = "detail?id=" + writeArticleRd.getBody().get("id");
		return Util.msgAndReplace(req, writeArticleRd.getMsg(), replaceUrl);
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
	public String showList(HttpServletRequest req, @RequestParam(defaultValue="1") int boardId, String searchKeywordType, String searchKeyword, 
			@RequestParam(defaultValue="1") int page) {
		Board board = articleService.getBoardById(boardId);
		
		if (Util.isEmpty(searchKeywordType)) {
			searchKeywordType = "titleAndBody";
		}
		
		// log : 좀 더 자세하게 / application.yml - custom - logging - level = debug : 보임, info : 안보임
		// log.debug("searchKeyword : " + searchKeyword);		
				
		if(board == null) {
			return Util.msgAndBack(req, boardId + "번 게시판이 존재하지 않습니다.");
		}
		
		req.setAttribute("board", board);
		
		int totalItemsCount = articleService.getArticlesTotalCount(boardId, searchKeywordType, searchKeyword);		
		
		req.setAttribute("totalItemsCount", totalItemsCount);
		
		// 한 페이지에 보여줄 수 있는 게시물 최대 개수
		int itemsCountInAPage = 20;
		// 총 페이지 수, ceil = 나머지 올림(ceil, round, floor)
		int totalPage = (int) Math.ceil(totalItemsCount / (double) itemsCountInAPage);
		
		// 현재 페이지(임시)		
		req.setAttribute("page", page);
		req.setAttribute("totalPage", totalPage);
		
		List<Article> articles = articleService.getForPrintArticles(boardId, searchKeywordType, searchKeyword, itemsCountInAPage, page);
		
		req.setAttribute("articles", articles);
		
		return "mpaUsr/article/list";
	}	

	@RequestMapping("/mpaUsr/article/doDelete")	
	public String doDelete(HttpServletRequest req, Integer id) {		
		if (Util.isEmpty(id)) {
			return Util.msgAndBack(req, "id를 입력해주세요.");
		}
		
		ResultData rd = articleService.deleteArticle(id);
		
		if (rd.isFail()) {
			return Util.msgAndBack(req, rd.getMsg()); 
		}
		
		String replaceUrl = "../article/list?boardId=" + rd.getBody().get("boardId");
		
		return Util.msgAndReplace(req, rd.getMsg(), replaceUrl);
	}

	@RequestMapping("mpaUsr/article/doModify")
	@ResponseBody
	public ResultData doModify(Integer id, String title, String body) {
		return articleService.modifyArticle(id, title, body);
	}
	
}
