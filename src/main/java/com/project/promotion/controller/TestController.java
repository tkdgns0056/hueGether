package com.project.promotion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/header")
    public String headTest(){
        return "fragment/header";
    }
}
