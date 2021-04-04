package com.sbs.untact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Article {
	private Integer id;
	private String title;
	private String body;
	private int boardId;
	private int memberId;
	private String regDate;
	private String updateDate;
	private boolean blindStatus;
	private String blindDate;
	private boolean delStatus;
	private String delDate;
	private int hitCount;
	private int repliesCount;
	private int likeCount;
	private int dislikeCount;
}
