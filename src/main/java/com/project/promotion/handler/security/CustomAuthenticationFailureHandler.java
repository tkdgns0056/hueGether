package com.project.promotion.handler.security;

import com.project.promotion.exception.MemberAuthException;
import com.project.promotion.exception.MemberIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * SimpleUrlAuthenticationFailureHandler 를 사용한 이유
 * 해당 클래스가 제공해주는 setDefaultFailureUrl() 메서드를 활용하기 위함. 해당 메서드를 활용하면 로그인 실패 시 url을 지정해준다.
 * 해당 url은 컨트롤러에 매핑되어 있어야 한다.
 */
@Component
@Slf4j
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        log.info("login Fail!");

        String errorMessage;

        if(exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 일치하지 않습니다. 다시 확인해주세요.";
        } else if (exception instanceof UsernameNotFoundException) {
            errorMessage = "존재하지 않는 계정입니다. 회원가입 후 로그인해주세요.";
        } else {
            errorMessage = "알 수 없는 오류로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        }

        /* 한글 인코딩 깨진 문제 방지 */
        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");

        setDefaultFailureUrl("/user/login?error=true&exception=" + errorMessage);

        super.onAuthenticationFailure(request, response, exception);

    }


}
