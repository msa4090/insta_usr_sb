package com.sbs.untact.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("beforeActionInterceptor") // 컴포넌트 이름 설정
@Slf4j
public class BeforeActionInterceptor implements HandlerInterceptor {
    // 요청이 들어올 때마다 실행
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        return HandlerInterceptor.super.preHandle(req, resp, handler);
    }
}