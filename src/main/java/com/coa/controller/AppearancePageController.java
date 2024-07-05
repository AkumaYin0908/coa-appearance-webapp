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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AppearancePageController {

    private final VisitorService visitorService;
    @GetMapping(value = "/visitors/{id}/appearances/appearance-form",params = "!dateIssued")
    public String showAppearanceForm(@PathVariable("id")Long id, @RequestParam(value = "appearanceType")String appearanceType,
                                       Model model, RedirectAttributes redirectAttributes){

        try{
            VisitorResponse visitor = visitorService.findById(id);
            model.addAttribute("visitor",visitor);
            model.addAttribute("appearanceType",appearanceType);
        }catch (Exception ex){
            redirectAttributes.addFlashAttribute("error",ex.getMessage());
            System.out.println(ex.getMessage());
            return "redirect:/visitor-page";
        }
            return String.format("appearances/%s-appearance",appearanceType);
    }
}
