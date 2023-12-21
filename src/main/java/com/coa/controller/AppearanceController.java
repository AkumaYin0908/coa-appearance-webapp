package com.coa.controller;

import com.coa.dto.AppearanceDTO;
import com.coa.dto.VisitorDTO;
import com.coa.exceptions.ApperanceNotFoundException;
import com.coa.exceptions.LeaderNotFoundException;
import com.coa.exceptions.VisitorNotFoundException;
import com.coa.model.Appearance;
import com.coa.model.Leader;
import com.coa.model.Purpose;
import com.coa.model.Visitor;
import com.coa.service.AppearanceService;
import com.coa.service.LeaderService;
import com.coa.service.PurposeService;
import com.coa.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.time.Month;
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
    private final LeaderService leaderService;


    private AppearanceDTO editFormAppearanceDTO = new AppearanceDTO();
    @Value("${pageNums}")
    private List<Integer> pageNums;



    private static final DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("MMMM dd, yyyy");

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
            visitorId=id;
            Visitor visitor;

            if(optionalVisitor.isPresent()){
                visitor = optionalVisitor.get();
            }else{
                throw new VisitorNotFoundException("Visitor with id : " + id + " not found!");
            }

            AppearanceDTO appearanceDTO = new AppearanceDTO();
            appearanceDTO.setName(visitor.getName());
            appearanceDTO.setPosition(visitor.getPosition().getName());
            appearanceDTO.setAgency(visitor.getAgency().getName());

            List<String> purposes=purposeService.findAll().stream()
                    .map(Purpose::getPurpose).toList();

            Optional<Leader> leaderOptional=leaderService.findLeaderByInChargeStatus(true);
            Leader leader;
            if(leaderOptional.isPresent()){
                leader=leaderOptional.get();
            }else{
                throw new LeaderNotFoundException("No leader is currently active!");
            }


            model.addAttribute("editFormAppearanceDTO",editFormAppearanceDTO);
            model.addAttribute("appearanceDTO", appearanceDTO);
            model.addAttribute("leader",leader);
            model.addAttribute("purposes",purposes);
        }catch (Exception ex){
            model.addAttribute("message", ex.getMessage());

            return "redirect:/visitors";
        }
        return "appearances/appearance-form";


    }

    @PostMapping("/save")
    public String saveAppearance(@ModelAttribute("appearanceDTO") AppearanceDTO appearanceDTO,
                                 @RequestParam("save")String save, RedirectAttributes redirectAttributes, Model model){

        try{

            Appearance appearance=new Appearance();
            Optional<Visitor> visitorOptional=visitorService.findVisitorByName(appearanceDTO.getName());
            Visitor visitor=new Visitor();
            if(visitorOptional.isPresent()){
                visitor=visitorOptional.get();
            }

            appearance.setVisitor(visitor);

            String purposeString =appearanceDTO.getPurpose();

            if(purposeString.endsWith(".")){
                purposeString=purposeString.substring(0,purposeString.length()-1);
            }

            Optional<Purpose> purposeOptional = purposeService.findByPurpose(purposeString);
            Purpose purpose;
            if(purposeOptional.isPresent()){
                purpose=purposeOptional.get();
                appearance.setPurpose(purpose);
            }else{
                purpose = new Purpose(purposeString);
                appearance.setPurpose(purpose);
            }
            LocalDate dateIssued=LocalDate.parse(appearanceDTO.getDateIssued(),dateTimeFormatter);
            LocalDate dateFrom = LocalDate.parse(appearanceDTO.getDateFrom(),dateTimeFormatter);
            LocalDate dateTo = LocalDate.parse(appearanceDTO.getDateTo(),dateTimeFormatter);

            boolean isValid=validateDate(dateIssued,dateFrom,dateTo);

            if(isValid){
                appearance.setDateIssued(dateIssued);
                appearance.setDateFrom(dateFrom);
                appearance.setDateTo(dateTo);
            }else{
                redirectAttributes.addFlashAttribute("message","Invalid dating of appearance!");
                return String.format("redirect:/appearances/appearance-form/%d",visitorId);
            }

            if(save.equals("SaveOnly")) {
                appearanceService.save(appearance);
                redirectAttributes.addFlashAttribute("message", String.format("New appearance has been made for %s!", appearanceDTO.getName()));
            }else{
                appearanceService.save(appearance);

                System.out.println("save and print");
                redirectAttributes.addFlashAttribute("message", String.format("New appearance has been made for %s!", appearanceDTO.getName()));
            }
        }catch(Exception ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            ex.printStackTrace();

        }
        return "redirect:/visitors";

    }
    @GetMapping("/{id}/appearance-history")
    public String showAppearanceHistory(@PathVariable("id")Long id, Model model, @RequestParam(required = false) String searchPurpose,
                                        @RequestParam(required = false,defaultValue = "0") Integer selectedMonth,
                                        @RequestParam(required = false,defaultValue = "0") Integer selectedYear,
                                        @RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id, asc") String[] sort){

        try{
            List<Integer> years=appearanceService.findAllDistinctYear();

            String sortField=sort[0];
            String sortDirection = sort[1];
            visitorId=id;
            Sort.Direction direction=sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

            Sort.Order order=new Sort.Order(direction,sortField);

            Pageable pageable = PageRequest.of(page-1, size,Sort.by(order));

            Optional<Visitor> visitorOptional = visitorService.findById(id);
            Visitor visitor;

            if(visitorOptional.isPresent()){
                visitor=visitorOptional.get();
            }else{
                throw new VisitorNotFoundException("Visitor with id : " + id + " not found!");
            }


            Page<Appearance> appearancePage;


            if((searchPurpose == null || searchPurpose.isEmpty())
                 && (selectedMonth == null || selectedMonth == 0)
                && (selectedYear == null || selectedYear == 0)){
                appearancePage=appearanceService.findAppearanceByVisitor(visitor,pageable);
            }else{

                appearancePage=appearanceService.findByPurposeContainingIgnoreCase(searchPurpose,visitor,pageable);
                System.out.println(appearancePage.getContent());
                model.addAttribute("searchPurpose",searchPurpose);
            }

            if(selectedMonth != null && selectedMonth != 0){

                appearancePage=appearanceService.findAppearanceByMonthDateIssued(selectedMonth,visitor,pageable);
                model.addAttribute("selectedMonth",selectedMonth);
            }

            if(selectedYear != null && selectedYear !=0){

                appearancePage=appearanceService.findAppearanceByYearDateIssued(selectedYear,visitor,pageable);
                model.addAttribute("selectedYear", selectedYear);
            }

            if((searchPurpose == null || searchPurpose.isEmpty())
                    && (selectedMonth != null && selectedMonth != 0)
                    && (selectedYear != null && selectedYear !=0)
            ){
                appearancePage=appearanceService.findAppearanceByMonthAndYearDateIssued(selectedMonth,selectedYear,visitor,pageable);
                model.addAttribute("selectedMonth",selectedMonth);
                model.addAttribute("selectedYear", selectedYear);
            }



            if(!(searchPurpose == null || searchPurpose.isEmpty())
                    && (selectedMonth != null && selectedMonth != 0)
                    && (selectedYear != null && selectedYear !=0)){
                appearancePage=appearanceService.findByPurposeAndMonthAndYearContainingIgnoreCase(searchPurpose,selectedMonth,selectedYear,visitor,pageable);
                model.addAttribute("searchPurpose",searchPurpose);
                model.addAttribute("selectedMonth",selectedMonth);
                model.addAttribute("selectedYear", selectedYear);
            }


            if(!(searchPurpose == null || searchPurpose.isEmpty())
                    && (selectedMonth != null && selectedMonth != 0)
                    && !(selectedYear != null && selectedYear !=0)){
                appearancePage=appearanceService.findByPurposeAndMonthContainingIgnoreCase(searchPurpose,selectedMonth,visitor,pageable);
                model.addAttribute("searchPurpose",searchPurpose);
                model.addAttribute("selectedMonth",selectedMonth);
            }

            if(!(searchPurpose == null || searchPurpose.isEmpty())
                    && !(selectedMonth != null && selectedMonth != 0)
                    && (selectedYear != null && selectedYear !=0)){
                appearancePage=appearanceService.findByPurposeAndYearContainingIgnoreCase(searchPurpose,selectedYear,visitor,pageable);
                model.addAttribute("searchPurpose",searchPurpose);
                model.addAttribute("selectedYear", selectedYear);
            }

            VisitorDTO  visitorDTO = new VisitorDTO(visitor.getId(),
                    visitor.getName(),
                    visitor.getPosition().getName(),
                    visitor.getAgency().getName());

            List<AppearanceDTO> appearanceDTOS=appearancePage.getContent()
                    .stream()
                    .map(appearance -> new AppearanceDTO(appearance.getId(),
                            appearance.getDateIssued().format(dateTimeFormatter),
                            appearance.getDateFrom().format(dateTimeFormatter),
                            appearance.getDateTo().format(dateTimeFormatter),
                            appearance.getPurpose().getPurpose()))
                    .toList();

            List<String> purposes=purposeService.findAll().stream()
                    .map(Purpose::getPurpose).toList();

            model.addAttribute("editFormAppearanceDTO", editFormAppearanceDTO);
            model.addAttribute("purposes", purposes);
            model.addAttribute("months", Month.values());
            model.addAttribute("years", years);
            model.addAttribute("appearances",appearanceDTOS);
            model.addAttribute("visitor", visitorDTO);
            model.addAttribute("currentPage", appearancePage.getNumber() + 1);
            model.addAttribute("totalItems",appearancePage.getTotalElements());
            System.out.println(appearancePage.getTotalElements());
            model.addAttribute("totalPages",appearancePage.getTotalPages());
            model.addAttribute("pageSize", size);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
            model.addAttribute("pageNums",pageNums);
        }catch (Exception ex){
            model.addAttribute("message", ex.getMessage());
            ex.printStackTrace();
        }
        return "appearances/appearance-history";
    }



    @PostMapping("/update")
    public String updateAppearance(@ModelAttribute("editFormAppearanceDTO") AppearanceDTO appearanceDTO, RedirectAttributes redirectAttributes, Model model){
        System.out.println(appearanceDTO.getId());
       try{
           Optional<Appearance> optionalAppearance = appearanceService.findById(appearanceDTO.getId());

           if(optionalAppearance.isPresent()){
             Appearance appearance=new Appearance();
             appearance.setId(appearanceDTO.getId());


               LocalDate dateIssued=LocalDate.parse(appearanceDTO.getDateIssued(),dateTimeFormatter);
               LocalDate dateFrom = LocalDate.parse(appearanceDTO.getDateFrom(),dateTimeFormatter);
               LocalDate dateTo = LocalDate.parse(appearanceDTO.getDateTo(),dateTimeFormatter);

               boolean isValid=validateDate(dateIssued,dateFrom,dateTo);

               if(isValid){
                   appearance.setDateIssued(dateIssued);
                   appearance.setDateFrom(dateFrom);
                   appearance.setDateTo(dateTo);
               }else{
                   editFormAppearanceDTO=appearanceDTO;
                   redirectAttributes.addFlashAttribute("message","Invalid dating of appearance!");
                   return String.format("redirect:/appearances/%d/appearance-history", visitorId);
               }


             appearance.setDateIssued(LocalDate.parse(appearanceDTO.getDateIssued(),dateTimeFormatter));
             appearance.setDateFrom(LocalDate.parse(appearanceDTO.getDateFrom(),dateTimeFormatter));
             appearance.setDateTo(LocalDate.parse(appearanceDTO.getDateTo(),dateTimeFormatter));


               String purposeString =appearanceDTO.getPurpose();

               if(purposeString.endsWith(".")){
                   purposeString=purposeString.substring(0,purposeString.length()-1);
               }

             Optional<Purpose> purposeOptional = purposeService.findByPurpose(purposeString);

             if(purposeOptional.isPresent()){
                 appearance.setPurpose(purposeOptional.get());
             }else{
                 appearance.setPurpose(new Purpose(purposeString));
             }

             Optional<Visitor> visitorOptional=visitorService.findById(visitorId);

             if(visitorOptional.isPresent()){
                 appearance.setVisitor(visitorOptional.get());
             }else{
                 throw new VisitorNotFoundException("Visitor with id : " + visitorId + " not found!");
             }
             appearanceService.save(appearance);
             editFormAppearanceDTO = new AppearanceDTO();
             redirectAttributes.addFlashAttribute("message", "Appearance   has been updated!");
           }else{
               throw new ApperanceNotFoundException("Appearance not found!");

           }
       }catch (Exception ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            ex.printStackTrace();
       }
       return String.format("redirect:/appearances/%d/appearance-history", visitorId);
    }
    private boolean validateDate(LocalDate dateIssued, LocalDate dateFrom, LocalDate dateTo){
        if(dateFrom.equals(dateTo)) {
            return !dateFrom.isBefore(dateIssued) && !dateTo.isBefore(dateIssued);
        }else{
            return dateFrom.isAfter(dateTo) || dateFrom.isAfter(dateIssued);
        }
    }


//    private int parseMonth(String selectedMonth){
//        for(Month month : Month.values()){
//            if(selectedMonth.equalsIgnoreCase(month.name())){
//                return  month.getValue();
//            }
//        }
//        return 0;
//    }


//    @GetMapping("/certificate?leader={leaderId}?appearance={name}")
//    public String loadCertificate(@PathVariable("leaderId")Long leaderId, @PathVariable("id")Long id, Model model){
//
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println("Accessed");
//       try {
//           Optional<Leader> leaderOptional=leaderService.findById(leaderId);
//           Leader leader;
//           if(leaderOptional.isPresent()){
//               leader=leaderOptional.get();
//           }else{
//               throw new LeaderNotFoundException("Leader not found!");
//           }
//
//           Optional<AppearanceDTO> appearanceDTOOptional = appearanceService.findAndMapToAppearanceDTO(id);
//
//
//           AppearanceDTO appearanceDTO;
//
//           if(appearanceDTOOptional.isPresent()){
//               appearanceDTO=appearanceDTOOptional.get();
//           }else{
//               throw new ApperanceNotFoundException("Appearance not found!");
//           }
//
//           model.addAttribute("appearance",appearanceDTO);
//           model.addAttribute("leader",leader);
//       }catch (Exception ex){
//           model.addAttribute("message", ex.getMessage());
//       }
//
//
//        return "appearances/certificate";
//    }


//    @GetMapping("/certificate/{name}")
//
//    public String loadCertificate(@PathVariable("name")String name, Model model){
//
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println("Accessed");
//        try {
//            Optional<Leader> leaderOptional=leaderService.findLeaderByInChargeStatus(true);
//            Leader leader;
//            if(leaderOptional.isPresent()){
//                leader=leaderOptional.get();
//            }else{
//                throw new LeaderNotFoundException("Leader not found!");
//            }
//
//            Optional<AppearanceDTO> appearanceDTOOptional = appearanceService.findAndMapToAppearanceDTO(name);
//
//
//            AppearanceDTO appearanceDTO;
//
//            if(appearanceDTOOptional.isPresent()){
//                appearanceDTO=appearanceDTOOptional.get();
//            }else{
//                throw new ApperanceNotFoundException("Appearance not found!");
//            }
//
//            model.addAttribute("appearance",appearanceDTO);
//            model.addAttribute("leader",leader);
//        }catch (Exception ex){
//            model.addAttribute("message", ex.getMessage());
//        }
//
//
//        return "appearances/certificate";
//    }


}
