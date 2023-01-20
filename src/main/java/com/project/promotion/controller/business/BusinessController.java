package com.project.promotion.controller.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/business")
public class BusinessController {

    @GetMapping("/main")
    public String businessMain(){

        return "business/businessMain";
    }

    @GetMapping("/save")
    public String businessSaveForm(){

        return "business/businessSaveForm";
    }
}
