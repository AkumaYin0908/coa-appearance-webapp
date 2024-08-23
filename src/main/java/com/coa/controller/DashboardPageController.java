package com.coa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardPageController {


    @GetMapping(value={"","/dashboard"})
    public String showDashboard(){
        return "dashboard";
    }
}
