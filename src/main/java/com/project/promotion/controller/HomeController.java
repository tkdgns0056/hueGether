package com.project.promotion.controller;

import com.project.promotion.model.auth.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {

        return "home";
    }

    /**
     * 일반 사용자와 SNS 사용자 세션 구분
     */
    @GetMapping("/login-proc")
    public String authTest(@AuthenticationPrincipal OAuth2User oAuth2User, @AuthenticationPrincipal PrincipalDetails principalDetails,
                          Model model){
        System.out.println("로그인 성공 진입!");

        if(oAuth2User != null){
            model.addAttribute("member", oAuth2User.getName());
        } else if(principalDetails != null) {
            System.out.println("멤버 이름 = " + principalDetails.getUsername());
            model.addAttribute("member", principalDetails.getMember().getUserName());
        }

        return "loginHome";
    }
}