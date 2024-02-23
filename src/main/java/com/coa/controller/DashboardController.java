package com.coa.controller;

import com.coa.dto.AppearanceDTO;
import com.coa.dto.VisitorDTO;
import com.coa.exceptions.LeaderNotFoundException;
import com.coa.exceptions.VisitorNotFoundException;
import com.coa.model.Appearance;
import com.coa.model.Leader;
import com.coa.model.Visitor;
import com.coa.service.AppearanceService;
import com.coa.service.LeaderService;
import com.coa.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final VisitorService visitorService;
    private final LeaderService leaderService;
    private final AppearanceService appearanceService;


    private static final DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("MMMM dd, yyyy");


    private boolean error;
    @InitBinder
    public void getInitBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor=new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }

    @GetMapping(value = {"/dashboard","/"})
    public String showDashboard(Model model,
                                @RequestParam(required = false)String searchName,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size){
        try{
            List<String> visitors=visitorService.findAll().stream()
                    .map(Visitor::getName).toList();

            if(searchName!=null){
                Optional<Visitor> visitorOptional=visitorService.findVisitorByName(searchName);

                if(visitorOptional.isPresent()){
                    Long id=visitorOptional.get().getId();
                   // redirectAttributes.addFlashAttribute("direction","/dashboard");
                    return String.format("redirect:/appearances/appearance-form/%d",id);
                }else{
                    throw new VisitorNotFoundException(searchName +  " not found!");

                }
            }

            Leader leader = leaderService.findLeaderByInChargeStatus(true);

            List<String> leaderNames=leaderService.findAll()
                            .stream().map(Leader :: getName).toList();

            model.addAttribute("addFormVisitorDTO", new VisitorDTO());
            loadAppearanceHistory(model,page,size);
            model.addAttribute("leaderNames",leaderNames);
            model.addAttribute("error",error);
            model.addAttribute("leader",leader);
            model.addAttribute("visitors",visitors);

        }catch (Exception ex){
            error =true;
            model.addAttribute("message", ex.getMessage());
            return "redirect:/dashboard";

        }

        return "dashboard";
    }

    public void loadAppearanceHistory(Model model, int page, int size){
        try{
            Pageable pageable= PageRequest.of(page-1,size);

            Page<Appearance> appearancePage = appearanceService.findAppearanceOrderByDateIssuedDESC(pageable);

            List<AppearanceDTO> appearanceDTOS = appearancePage.getContent()
                    .stream()
                    .map(appearance -> new AppearanceDTO(appearance.getId(),
                            appearance.getVisitor().getName(),
                            appearance.getVisitor().getPosition().getName(),
                            appearance.getVisitor().getAgency().getName(),
                            appearance.getDateIssued().format(dateTimeFormatter),
                            appearance.getDateFrom().format(dateTimeFormatter),
                            appearance.getDateTo().format(dateTimeFormatter),
                            appearance.getPurpose().getPurpose())).toList();


            model.addAttribute("appearances",appearanceDTOS);
            model.addAttribute("currentPage",appearancePage.getNumber() + 1);
            model.addAttribute("totalPages",appearancePage.getTotalPages());
            model.addAttribute("pageSize",size);
        }catch(Exception ex){
            error = true;
            model.addAttribute("message",ex.getMessage());
        }
    }
}
