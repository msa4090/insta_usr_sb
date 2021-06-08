package com.sbs.untact.dto;

import com.sbs.untact.util.Util;

// 데이터를 가공해서 쓰기 쉽게 한다.
public class Rq {
    private String currentUrl;
    private Member loginedMember;

    public Rq(Member loginedMember, String currentUrl) {
        this.loginedMember = loginedMember;
        this.currentUrl = currentUrl;
    }

    public boolean isLogined() {
        return loginedMember != null;
    }

    public boolean isNotLogined() {
        return isLogined() == false;
    }

    public int getLoginedMemberId() {
        if (isNotLogined()) return 0;

        return loginedMember.getId();
    }

    public Member getLoginedMember() {
        return loginedMember;
    }

    public String getLoginedMemberNickname() {
        if (isNotLogined()) return "";

        return loginedMember.getNickname();
    }

    public String getEncodedCurrentUrl() {
        return Util.getUrlEncoded(getCurrentUrl());
    }

    private String getCurrentUrl() {
        return currentUrl;
    }

    public String getLoginPageUrl() {
        return "../member/login?afterLoginUrl=" + getEncodedCurrentUrl();
    }
}