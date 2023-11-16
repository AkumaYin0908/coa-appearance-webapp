package com.coa.controller;

import org.springframework.stereotype.Controller;

@Controller
public class DashboardController {


    public String showDashboard(){
        return "showDashboard";
    }
}
