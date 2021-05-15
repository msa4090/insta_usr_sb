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

@Controller
@Slf4j
public class MpaUsrMemberController {
    @Autowired
    private MemberService memberService;

    @RequestMapping("/mpaUsr/member/join")
    public String showWrite(HttpServletRequest req) {
        return "mpaUsr/member/join";
    }

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
