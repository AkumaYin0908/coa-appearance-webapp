package com.coa.controller;

import com.coa.dto.VisitorDTO;
import com.coa.exceptions.LeaderNotFoundException;
import com.coa.exceptions.VisitorNotFoundException;
import com.coa.model.Leader;
import com.coa.model.Visitor;
import com.coa.service.LeaderService;
import com.coa.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final VisitorService visitorService;
    private final LeaderService leaderService;




    @InitBinder
    public void getInitBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor=new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, @RequestParam(required = false)String searchName){
        try{
            List<String> visitors=visitorService.findAll().stream()
                    .map(Visitor::getName).toList();

            if(searchName!=null){
                Optional<Visitor> visitorOptional=visitorService.findVisitorByName(searchName);

                if(visitorOptional.isPresent()){
                    Long id=visitorOptional.get().getId();
                    return String.format("redirect:/appearances/appearance-form/%d",id);
                }else{
                    throw new VisitorNotFoundException(searchName +  " not found!");
                }
            }

            Optional<Leader> leaderOptional = leaderService.findLeaderByInChargeStatus();
            Leader leader;
            if(leaderOptional.isPresent()){
                leader=leaderOptional.get();
            }else{
                throw new LeaderNotFoundException("Leader not found!");
            }

            List<String> leaderNames=leaderService.findAll()
                            .stream().map(Leader :: getName).toList();

            model.addAttribute("leaderNames",leaderNames);
            model.addAttribute("leader",leader);
            model.addAttribute("visitors",visitors);
            model.addAttribute("addFormVisitorDTO", new VisitorDTO());
        }catch (Exception ex){
            model.addAttribute("message", ex.getMessage());
        }

        return "dashboard";
    }
}
