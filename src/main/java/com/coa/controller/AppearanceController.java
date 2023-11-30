package com.coa.controller;

import com.coa.dto.AppearanceDTO;
import com.coa.exceptions.VisitorNotFoundException;
import com.coa.model.Appearance;
import com.coa.model.Purpose;
import com.coa.model.Visitor;
import com.coa.service.AppearanceService;
import com.coa.service.PurposeService;
import com.coa.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/appearances")
@RequiredArgsConstructor
public class AppearanceController {


    private final VisitorService visitorService;
    private final AppearanceService appearanceService;
    private final PurposeService purposeService;

    @GetMapping("/appearance-form/{id}")
    public String showApperanceForm(@PathVariable("id")Long id, Model model, RedirectAttributes redirectAttributes){
        try{
            Optional<Visitor> optionalVisitor =visitorService.findById(id);

            if(optionalVisitor.isPresent()){
                Visitor visitor=optionalVisitor.get();
                AppearanceDTO appearanceDTO = new AppearanceDTO();
                appearanceDTO.setName(visitor.getName());
                appearanceDTO.setPosition(visitor.getPosition().getName());
                appearanceDTO.setAgency(visitor.getAgency().getName());

                List<Purpose> purposes=purposeService.findAll();

                model.addAttribute("appearanceDTO", appearanceDTO);
                model.addAttribute("purposes",purposes);
                return "appearances/appearance-form";
            }else{
                throw new VisitorNotFoundException(String.format("Visitor with id: %d not found@",id));
            }
        }catch (Exception ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/visitors";
        }

    }


    @PostMapping("/save")
    public String saveAppearance(@ModelAttribute("appearanceDTO")AppearanceDTO appearanceDTO, RedirectAttributes redirectAttributes){

        try{
            Appearance appearance=new Appearance();

            Optional<Visitor> visitorOptional=visitorService.findVisitorByName(appearanceDTO.getName());
            Visitor visitor =visitorOptional.get();


            Optional<Purpose> purposeOptional = purposeService.findByPurpose(appearanceDTO.getPurpose());
            Purpose purpose;
            if(purposeOptional.isPresent()){
                purpose=purposeOptional.get();
                appearance.setPurpose(purpose);
            }else{
                purpose = new Purpose(appearanceDTO.getPurpose());
                appearance.setPurpose(purpose);
            }
            appearance.setDateIssued(appearanceDTO.getDateIssued());

            LocalDate dateFrom = appearanceDTO.getDateFrom();
            LocalDate dateTo = appearanceDTO.getDateTo();



        }catch(Exception ex){

        }



    }

    private String getFormattedDateRange(LocalDate dateFrom, LocalDate dateTo){
        DateTimeFormatter  dateTimeFormatter=DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        String firstDate ="";
        String lastDate="";

        String formattedDateRange="";

        LocalDate[]dates={dateFrom,dateTo};

        if(dateFrom.getMonth().equals(dateTo.getMonth())){
            if(dateFrom.equals(dateTo)){
                formattedDateRange=dateTimeFormatter.format(dateFrom);
            }else{
                DateTimeFormatter firstDayFormat= DateTimeFormatter.ofPattern("MMMM d");
                DateTimeFormatter lastDayFormat= DateTimeFormatter.ofPattern("d, yyyy");

                firstDate=firstDayFormat.format(dates[0]);
                lastDate=lastDayFormat.format(dates[1]);
                formattedDateRange=String.format("%s - %s",firstDate,lastDate);
            }
        }else{
            firstDate=dateTimeFormatter.format(dates[0]);
            lastDate=dateTimeFormatter.format(dates[1]);
            formattedDateRange=String.format("%s - %s",firstDate,lastDate);
        }

        return formattedDateRange;
    }

    private boolean validateDate(LocalDate dateIssued, LocalDate dateFrom, LocalDate dateTo){
        return dateFrom.isAfter(dateTo) || dateFrom.isAfter(dateIssued);
    }


}
