package com.project.promotion.controller;

import com.project.promotion.model.auth.PrincipalDetails;
import com.project.promotion.model.business.Business;
import com.project.promotion.model.member.Member;
import com.project.promotion.service.auth.PrincipalDetailsService;
import com.project.promotion.service.member.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.nio.channels.Pipe;


@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal PrincipalDetails member, Model model) {

        if(member != null){
            model.addAttribute("member", member);
        }

        return "home";
    }

    /**
     * 일반 사용자와 SNS 사용자 세션 구분
     */
    @GetMapping("/login-proc")
    public String authTest(){

        System.out.println("로그인 성공 진입!");

        return "redirect:/";
    }
}