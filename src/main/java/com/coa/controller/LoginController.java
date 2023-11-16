package com.coa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }

    @GetMapping("acess-denied")
    public String showAccessDeniedPage(){
        return "access-denied";
    }
}
