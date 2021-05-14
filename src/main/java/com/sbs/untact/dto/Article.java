package com.sbs.untact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
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

	private String extra__writerName;

	public String getBodyForPrint() {
		// nl2br: 줄바꿈 처리
		String bodyForPrint = body.replaceAll("\r\n", "<br>");
		bodyForPrint = bodyForPrint.replaceAll("\r", "<br>");
		bodyForPrint = bodyForPrint.replaceAll("\n", "<br>");

		return bodyForPrint;
	}
}
