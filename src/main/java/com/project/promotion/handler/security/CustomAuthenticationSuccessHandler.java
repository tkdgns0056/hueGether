package com.project.promotion.handler.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("login Success!");

        // 홈페이지를 둘러보다가 로그인이 필요할 경우, 홈으로 가는게 아니라 보고 있던 페이지로 이동
        //기본 url 설정, savedRequest가 null일 경우 설정한 페이지로 보내기 위함이다.
        setDefaultTargetUrl("/login-proc");

        // 사용자가 인증을 시도하기 이전에 접근을 시도했던 자원이 없을경우 savedRequest는 null로 반환된다.
        SavedRequest savedRequest = requestCache.getRequest(request,response);

        if(savedRequest != null){
                    // 인증 받기 전 url로 이동하기
                    String targetUrl = savedRequest.getRedirectUrl();
                    redirectStrategy.sendRedirect(request,response,targetUrl);
                    return;
        } else {
            //기본 url로 가도록 함
            redirectStrategy.sendRedirect(request,response,getDefaultTargetUrl());
            return;
        }
    }

}
