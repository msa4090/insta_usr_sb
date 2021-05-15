package com.sbs.untact.controller;

import com.sbs.untact.dto.Member;
import com.sbs.untact.dto.ResultData;
import com.sbs.untact.service.MemberService;
import com.sbs.untact.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class MpaUsrMemberController {
    @Autowired
    private MemberService memberService;

    @RequestMapping("/mpaUsr/member/login")
    public String showLogin (HttpServletRequest req) { return "mpaUsr/member/login"; }

    @RequestMapping("/mpaUsr/member/doLogin")
    public String doLogin(HttpServletRequest req, String loginId, String loginPw, String redirectUrl) {

        Member member = memberService.getMemberByLoginId(loginId);

        if(member == null) {
            return Util.msgAndBack(req, loginId + "(은)는 존재하지 않는 아이디입니다.");
        }

        if(member.getLoginPw().equals(loginPw) == false) {
            return Util.msgAndBack(req, loginId + "비밀번호가 일치하지 않습니다.");
        }

        HttpSession session = req.getSession();
        session.setAttribute("loginedMemberId", member.getId());

        String msg = "환영합니다.";

        return Util.msgAndReplace(req, msg, redirectUrl);
    }

    @RequestMapping("/mpaUsr/member/join")
    public String showJoin(HttpServletRequest req) { return "mpaUsr/member/join"; }

    @RequestMapping("/mpaUsr/member/doJoin")
    public String doJoin(HttpServletRequest req, String loginId, String loginPw, String name, String nickName, String cellPhoneNo, String email) {

        Member oldMember = memberService.getMemberByLoginId(loginId);

        if(oldMember != null) {
            return Util.msgAndBack(req, loginId + "(은)는 이미 사용중인 로그인아이디 입니다.");
        }

        ResultData joinRd = memberService.join(loginId, loginPw, name, nickName, cellPhoneNo, email);

        if(joinRd.isFail()) {
            return Util.msgAndBack(req, joinRd.getMsg());
        }

        return Util.msgAndReplace(req, joinRd.getMsg(), "/");
    }
}
