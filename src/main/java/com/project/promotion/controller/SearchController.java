package com.project.promotion.controller;

import com.project.promotion.model.auth.PrincipalDetails;
import com.project.promotion.model.business.Business;
import com.project.promotion.model.business.Room;
import com.project.promotion.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search/room/list")
    public String searchList(@AuthenticationPrincipal PrincipalDetails member,
                             @RequestParam("keyword") String keyword,
                             Business business,
                             Model model){

        List<Business> searchList = searchService.searchList(keyword);
        model.addAttribute("searchList" , searchList);
        model.addAttribute("business", business);
        model.addAttribute("member", member);


        return "accommodation/list";
    }
}
