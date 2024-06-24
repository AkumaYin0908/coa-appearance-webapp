package com.coa.controller;

import com.coa.model.Visitor;
import com.coa.payload.request.VisitorRequest;
import com.coa.payload.response.VisitorResponse;
import com.coa.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AppearancePageController {

    private final VisitorService visitorService;
    @GetMapping("/visitors/{id}/single-appearance")
    public String showSingleAppearance(@PathVariable("id")Long id, Model model){
        try{
            model.addAttribute("visitor",visitorService.findById(id));
        }catch (Exception ex){
            model.addAttribute("error",ex.getMessage());
            return "redirect: /visitor-page";
        }
        return "appearances/single-appearance";
    }
    @GetMapping("/visitors/{id}/consolidated-appearance")
    public String showConsolidatedAppearance(@PathVariable("id")Long id, Model model){
        try{
            model.addAttribute("visitor",visitorService.findById(id));
        }catch (Exception ex){
            model.addAttribute("error",ex.getMessage());
            return "redirect: /visitor-page";
        }
        return "appearances/consolidated-appearance";
    }
}
