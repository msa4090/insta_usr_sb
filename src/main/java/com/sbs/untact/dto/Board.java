package com.sbs.untact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Board {
	private Integer id;
	private String regDate;
	private String updateDate;
	private String name;
	private String code;	
	private boolean blindStatus;
	private String blindDate;
	private boolean delStatus;
	private String delDate;
	private int hitCount;
	private int repliesCount;
	private int likeCount;
	private int dislikeCount;
}
