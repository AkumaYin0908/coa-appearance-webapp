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
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/appearances")
@RequiredArgsConstructor
public class AppearanceController {


    private final VisitorService visitorService;
    private final AppearanceService appearanceService;
    private final PurposeService purposeService;


    private static DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("MMMM dd, yyyy");

    @InitBinder
    public void getInitBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor=new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }


    private Long visitorId;
    @GetMapping("/appearance-form/{id}")
    public String showAppearanceForm(@PathVariable("id")Long id, Model model){
        try{
            Optional<Visitor> optionalVisitor =visitorService.findById(id);
            System.out.println(optionalVisitor.get());
            visitorId=id;
            if(optionalVisitor.isPresent()){
                Visitor visitor=optionalVisitor.get();
                AppearanceDTO appearanceDTO = new AppearanceDTO();
                appearanceDTO.setName(visitor.getName());
                appearanceDTO.setPosition(visitor.getPosition().getName());
                appearanceDTO.setAgency(visitor.getAgency().getName());

                List<Purpose> purposes=purposeService.findAll();

                model.addAttribute("appearanceDTO", appearanceDTO);
                model.addAttribute("purposes",purposes);
            }else{
                throw new VisitorNotFoundException(String.format("Visitor with id: %d not found@",id));
            }
        }catch (Exception ex){
            model.addAttribute("message", ex.getMessage());

            return "redirect:/visitors";
        }
        return "appearances/appearance-form";


    }


    @PostMapping("/save")
    public String saveAppearance(@ModelAttribute("appearanceDTO") AppearanceDTO appearanceDTO, RedirectAttributes redirectAttributes){

        try{

            Appearance appearance=new Appearance();
            System.out.println(appearanceDTO);
            Optional<Visitor> visitorOptional=visitorService.findVisitorByName(appearanceDTO.getName());
            Visitor visitor=new Visitor();
            if(visitorOptional.isPresent()){
                visitor=visitorOptional.get();
            }

            appearance.setVisitor(visitor);
            Optional<Purpose> purposeOptional = purposeService.findByPurpose(appearanceDTO.getPurpose());
            Purpose purpose;
            if(purposeOptional.isPresent()){
                purpose=purposeOptional.get();
                appearance.setPurpose(purpose);
            }else{
                purpose = new Purpose(appearanceDTO.getPurpose());
                appearance.setPurpose(purpose);
            }
            LocalDate dateIssued=LocalDate.parse(appearanceDTO.getDateIssued(),dateTimeFormatter);
            LocalDate dateFrom = LocalDate.parse(appearanceDTO.getDateFrom(),dateTimeFormatter);
            LocalDate dateTo = LocalDate.parse(appearanceDTO.getDateTo(),dateTimeFormatter);
            System.out.println(dateIssued);
            System.out.println(dateFrom);
            System.out.println(dateTo);

            boolean isValid=validateDate(dateIssued,dateFrom,dateTo);
            System.out.println(isValid);
            if(isValid){
                appearance.setDateIssued(dateIssued);
                appearance.setDateFrom(dateFrom);
                appearance.setDateTo(dateTo);
            }else{
                redirectAttributes.addFlashAttribute("message","Invalid dating of appearance!");
                return String.format("redirect:/appearances/appearance-form/%d",visitorId);
            }
            appearanceService.save(appearance);
            redirectAttributes.addFlashAttribute("message",String.format("New appearance has been made for %s!",appearanceDTO.getName()));
        }catch(Exception ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            ex.printStackTrace();

        }
        return "redirect:/visitors";

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
        if(dateFrom.equals(dateTo)) {
            if(dateFrom.isBefore(dateIssued) || dateTo.isBefore(dateIssued)){
                return false;
            }else{
                return true;
            }
        }else{
            if (dateFrom.isAfter(dateTo) || dateFrom.isAfter(dateIssued)) {
                return true;
            } else {
                return false;
            }
        }
    }


}
