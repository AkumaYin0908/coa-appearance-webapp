package com.coa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppearancePageController {

    @GetMapping("/visitors/{id}/apperance-form")
    public String showAppearanceForm(Model model){
        return "appearances/appearance-form";
    }
}
