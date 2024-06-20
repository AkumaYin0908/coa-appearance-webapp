package com.coa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VisitorPageController {

    @GetMapping("/visitor-page")
    public String showVisitorPage(){
        return "visitors/visitors";
    }
}
