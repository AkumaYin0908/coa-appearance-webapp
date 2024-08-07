package com.coa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings")
public class SettingPageController {

    @GetMapping("/leader-page")
    public String showLeaderPage(){
        return "settings/leaders";
    }
}
