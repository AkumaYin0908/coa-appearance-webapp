package com.coa.controller;

import com.coa.dto.AppearanceDTO;
import com.coa.dto.VisitorDTO;
import com.coa.exceptions.VisitorNotFoundException;
import com.coa.model.Appearance;
import com.coa.model.Leader;
import com.coa.model.Purpose;
import com.coa.model.Visitor;
import com.coa.service.AppearanceService;
import com.coa.service.LeaderService;
import com.coa.service.PurposeService;
import com.coa.service.VisitorService;
import com.coa.utils.AppearanceIDs;
import jakarta.servlet.http.HttpServletRequest;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/appearances")
@RequiredArgsConstructor
public class AppearanceController {


    private final VisitorService visitorService;
    private final AppearanceService appearanceService;
    private final PurposeService purposeService;
    private final LeaderService leaderService;

    private Long visitorId;
    private boolean error;


    private AppearanceDTO editFormAppearanceDTO = new AppearanceDTO();
    @Value("${pageNums}")
    private List<Integer> pageNums;



    private static final DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("MMMM dd, yyyy");


    @InitBinder
    public void getInitBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor=new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
    }



    @GetMapping("/appearance-form/{id}")
    public String showAppearanceForm(@PathVariable("id")Long id,@RequestParam(defaultValue = "dashboard",name = "direction") String direction,
                                     @RequestParam(name="dateIssued",required = false) String strDateIssued,
                                     @RequestParam(name="isMultipleDate",defaultValue = "false",required = false)String isMultipleDate, Model model,RedirectAttributes redirectAttributes){
        try{


            Visitor visitor = visitorService.findById(id);
            strDateIssued=dateTimeFormatter.format(LocalDate.now());

            visitorId=id;

            AppearanceDTO appearanceDTO = new AppearanceDTO();
            appearanceDTO.setName(visitor.getName());
            appearanceDTO.setPosition(visitor.getPosition().getName());
            appearanceDTO.setAgency(visitor.getAgency().getName());




            List<String> purposes=purposeService.findAll().stream()
                    .map(Purpose::getPurpose).toList();

            LocalDate dateIssued=LocalDate.parse(strDateIssued,dateTimeFormatter);
            List<AppearanceDTO> appearanceDTOS = appearanceService.findAppearanceByVisitorAndDateIssued(visitorId,dateIssued)
                    .stream()
                    .map(appearance -> new AppearanceDTO(appearance.getId(),
                            appearance.getVisitor().getName(),
                            appearance.getVisitor().getPosition().getName(),
                            appearance.getVisitor().getAgency().getName(),
                            appearance.getDateIssued().format(dateTimeFormatter),
                            appearance.getDateFrom().format(dateTimeFormatter),
                            appearance.getDateTo().format(dateTimeFormatter),
                            appearance.getPurpose().getPurpose())).toList();


            model.addAttribute("editFormAppearanceDTO",editFormAppearanceDTO);
            model.addAttribute("appearanceDTO", appearanceDTO);
            model.addAttribute("appearanceDTOS",appearanceDTOS);
            model.addAttribute("direction",direction);
            model.addAttribute("error",error);
            model.addAttribute("visitorId",visitorId);
            model.addAttribute("visitorName",appearanceDTO.getName());
            model.addAttribute("dateIssued", strDateIssued);
            model.addAttribute("purposes",purposes);
            model.addAttribute("isMultipleDate",isMultipleDate);



        }catch (Exception ex){
            error = true;
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "appearances/appearance-form";


    }

    @PostMapping("/save")
    public String saveAppearance(@ModelAttribute("appearanceDTO") AppearanceDTO appearanceDTO,
                                 @RequestParam("save")String save,RedirectAttributes redirectAttributes, Model model, HttpServletRequest httpServletRequest) throws URISyntaxException {

        try{
            Appearance appearance=new Appearance();
            Visitor visitor = visitorService.findVisitorByName(appearanceDTO.getName()).orElseThrow(() -> new VisitorNotFoundException(appearanceDTO.getName() + " not found!"));

            appearance.setVisitor(visitor);
            String purposeString =appearanceDTO.getPurpose();

            if(purposeString.endsWith(".")){
                purposeString=purposeString.substring(0,purposeString.length()-1);
            }

            appearance.setPurpose(purposeService.findByPurpose(purposeString));

            LocalDate dateIssued=LocalDate.parse(appearanceDTO.getDateIssued(),dateTimeFormatter);
            LocalDate dateFrom = LocalDate.parse(appearanceDTO.getDateFrom(),dateTimeFormatter);
            LocalDate dateTo = LocalDate.parse(appearanceDTO.getDateTo(),dateTimeFormatter);


            appearance.setDateIssued(dateIssued);
            appearance.setDateFrom(dateFrom);
            appearance.setDateTo(dateTo);

            Optional<Appearance> appearanceOptional=appearanceService.findByDateFromAndDateToAndName(appearance.getDateFrom(),appearance.getDateTo(),appearanceDTO.getName());

            if(appearanceOptional.isPresent()){
                error=true;
                String message;
                boolean isMultipleDate= save.equals("SaveOnly");

                if(appearanceDTO.getDateFrom().equals(appearanceDTO.getDateTo())){
                    message = String.format("Appearance for %s appeared on %s already exist!",
                            appearanceDTO.getName(),appearanceDTO.getDateFrom());
                }else {
                    message = String.format("Appearance for %s appeared from %s to %s already exist!",
                            appearanceDTO.getName(), appearanceDTO.getDateFrom(), appearanceDTO.getDateTo());
                }
                redirectAttributes.addFlashAttribute("message",message);
                return String.format("redirect:/appearances/appearance-form/%d?isMultipleDate=%b", visitorId,isMultipleDate);
            }

            if(save.equals("SaveOnly")) {
                error =false;

                appearanceService.save(appearance);
                redirectAttributes.addFlashAttribute("isMultipleDate",true);
                redirectAttributes.addFlashAttribute("message", String.format("New appearance has been made for %s!", appearanceDTO.getName()));
                return String.format("redirect:/appearances/appearance-form/%d?isMultipleDate=%b",visitorId,true);
            }else{

                Long appearanceId = appearanceService.save(appearance).getId();

                String referer=httpServletRequest.getHeader("Referer");

                model.addAttribute("referer",referer);
                return String.format("redirect:/appearances/certificate?appearance=%d",appearanceId);

            }
        }catch(Exception ex){
            error =true;
            redirectAttributes.addFlashAttribute("message",ex.getMessage());

        }
        return String.format("redirect:/appearances/appearance-form/%d",visitorId);

    }

    @GetMapping("/{id}/appearance-history")
    public String showAppearanceHistory(@PathVariable("id")Long id, Model model, @RequestParam(required = false) String searchPurpose,
                                        @RequestParam(required = false,defaultValue = "0") Integer selectedMonth,
                                        @RequestParam(required = false,defaultValue = "0") Integer selectedYear,
                                        @RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id, desc") String[] sort,
                                        RedirectAttributes redirectAttributes){

        try{
            List<Integer> years=appearanceService.findAllDistinctYear();

            String sortField=sort[0];
            String sortDirection = sort[1];
            visitorId=id;
            Sort.Direction direction=sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

            Sort.Order order=new Sort.Order(direction,sortField);

            Pageable pageable = PageRequest.of(page-1, size,Sort.by(order));

            Visitor visitor = visitorService.findById(id);




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
            model.addAttribute("idList",new AppearanceIDs());
            model.addAttribute("visitorId",visitorId);
            model.addAttribute("purposes", purposes);
            model.addAttribute("months", Month.values());
            model.addAttribute("years", years);
            model.addAttribute("appearances",appearanceDTOS);
            model.addAttribute("visitor", visitorDTO);
            model.addAttribute("currentPage", appearancePage.getNumber() + 1);
            model.addAttribute("totalItems",appearancePage.getTotalElements());
            model.addAttribute("totalPages",appearancePage.getTotalPages());
            model.addAttribute("pageSize", size);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
            model.addAttribute("pageNums",pageNums);

        }catch (Exception ex){
            error=true;
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "appearances/appearance-history";
    }



    @PostMapping("/update")
    public String updateAppearance(@ModelAttribute("editFormAppearanceDTO") AppearanceDTO appearanceDTO,
                                   @RequestParam("save")String save,
                                   RedirectAttributes redirectAttributes,
                                   Model model, HttpServletRequest httpServletRequest) throws URISyntaxException {

        try{
            Appearance appearance = appearanceService.findById(appearanceDTO.getId());
            appearance.setId(appearanceDTO.getId());

            LocalDate dateIssued=LocalDate.parse(appearanceDTO.getDateIssued(),dateTimeFormatter);
            LocalDate dateFrom = LocalDate.parse(appearanceDTO.getDateFrom(),dateTimeFormatter);
            LocalDate dateTo = LocalDate.parse(appearanceDTO.getDateTo(),dateTimeFormatter);

            appearance.setDateIssued(dateIssued);
            appearance.setDateFrom(dateFrom);
            appearance.setDateTo(dateTo);

            String purposeString =appearanceDTO.getPurpose();

            if(purposeString.endsWith(".")){
                purposeString=purposeString.substring(0,purposeString.length()-1);
            }

            appearance.setPurpose(purposeService.findByPurpose(purposeString));

            Visitor visitor = visitorService.findById(visitorId);
            appearance.setVisitor(visitor);

            if(save.equals("SaveOnly")) {
                appearanceService.save(appearance);
                error =false;
            }else{
                Long appearanceId = appearanceService.save(appearance).getId();

                String referer=httpServletRequest.getHeader("Referer");

                model.addAttribute("referer",referer);
                return String.format("redirect:/appearances/certificate?appearance=%d",appearanceId);

            }
            editFormAppearanceDTO = new AppearanceDTO();
            redirectAttributes.addFlashAttribute("message", "Appearance   has been updated!");

        }catch (Exception ex){
            error=true;
            redirectAttributes.addFlashAttribute("message", ex.getMessage());


        }
        return String.format("redirect:/appearances/%d/appearance-history", visitorId);
    }
//    private boolean validateDate(LocalDate dateIssued, LocalDate dateFrom, LocalDate dateTo){
//        if(dateFrom.equals(dateTo)) {
//            return !dateFrom.isBefore(dateIssued) && !dateTo.isBefore(dateIssued);
//        }else{
//            return dateFrom.isAfter(dateTo) || dateFrom.isAfter(dateIssued);
//        }
//    }



    @GetMapping("/certificate")
    public String showCertificate(@RequestParam("appearance")Long appearanceId,
                                  Model model, HttpServletRequest httpServletRequest,RedirectAttributes redirectAttributes) throws URISyntaxException {

        String referer=httpServletRequest.getHeader("Referer");
        URI uri = new URI(referer);

        try{


            Leader leader = leaderService.findLeaderByInChargeStatus(true);

            AppearanceDTO appearanceDTO=appearanceService.findAndMapToAppearanceDTO(appearanceId);
            appearanceDTO.setFormattedStringDate(appearanceDTO.formattedStringDateRange(appearanceDTO.getDateFrom(),appearanceDTO.getDateTo()));




            model.addAttribute("referer",referer);
            model.addAttribute("appearance",appearanceDTO);
            model.addAttribute("leader",leader);
        }catch(Exception ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return String.format("redirect:%s",uri.getPath());

        }

            return "appearances/certificate";

   }

   @GetMapping("/certificate/{id}")
    public String showCertificateForMultipleDate(@PathVariable("id")Long id, @RequestParam("dateIssued") String dateIssued,
                                                 Model model, HttpServletRequest httpServletRequest,RedirectAttributes redirectAttributes) throws URISyntaxException {

       String referer=httpServletRequest.getHeader("Referer");
       URI uri = new URI(referer);

       try{

           Leader leader = leaderService.findLeaderByInChargeStatus(true);

           Visitor visitor = visitorService.findById(id);

           Set<String> datesFrom = new LinkedHashSet<>();
           Set<String> datesTo = new LinkedHashSet<>();
           Set<String> purposes = new LinkedHashSet<>();

           List<AppearanceDTO> appearanceDTOS = appearanceService.findAppearanceByVisitorAndDateIssued(visitorId,LocalDate.parse(dateIssued,dateTimeFormatter))
                   .stream()
                   .map(appearance -> new AppearanceDTO(appearance.getDateFrom().format(dateTimeFormatter),
                           appearance.getDateTo().format(dateTimeFormatter),
                           appearance.getPurpose().getPurpose())).toList();

            for(AppearanceDTO appearanceDTO : appearanceDTOS){
                datesFrom.add(appearanceDTO.getDateFrom());
                datesTo.add(appearanceDTO.getDateTo());
                purposes.add(appearanceDTO.getPurpose());

            }

           AppearanceDTO appearanceDTO= new AppearanceDTO();
           appearanceDTO.setName(visitor.getName());
           appearanceDTO.setPosition(visitor.getPosition().getName());
           appearanceDTO.setAgency(visitor.getAgency().getName());
           appearanceDTO.setDateIssued(dateIssued);
           appearanceDTO.setFormattedStringDate(appearanceDTO.formatStringMultipleDate(datesFrom,datesTo));
           appearanceDTO.setPurpose(appearanceDTO.joinPurpose(purposes));


           model.addAttribute("referer",referer);
           model.addAttribute("appearance",appearanceDTO);
           model.addAttribute("leader",leader);
       }catch(Exception ex){
           redirectAttributes.addFlashAttribute("message", ex.getMessage());
           return String.format("redirect:%s",uri.getPath());

       }

       return "appearances/certificate";

   }

   @PostMapping("/print")
    public String printCertificate(@ModelAttribute("idList")AppearanceIDs appearanceIDs, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) throws VisitorNotFoundException, URISyntaxException {


       String referer=httpServletRequest.getHeader("Referer");
       URI uri = new URI(referer);


     try{
         List<Appearance>appearances = new ArrayList<>();

         for(Long appearanceId:appearanceIDs.getAppearanceIDs()){
             appearances.add(appearanceService.findById(appearanceId));
         }

         if(appearances.size() == 1){
             return String.format("redirect:/appearances/certificate?appearance=%d",appearances.get(0).getId());
         }

         List<AppearanceDTO> appearanceDTOS = appearances.stream()
                 .map(appearance -> new AppearanceDTO(
                         appearance.getDateFrom().format(dateTimeFormatter),
                         appearance.getDateTo().format(dateTimeFormatter),
                         appearance.getPurpose().getPurpose())).toList();


         Set<String> datesFrom = new LinkedHashSet<>();
         Set<String> datesTo = new LinkedHashSet<>();
         Set<String> purposes = new LinkedHashSet<>();

         for(AppearanceDTO appearanceDTO : appearanceDTOS){
             datesFrom.add(appearanceDTO.getDateFrom());
             datesTo.add(appearanceDTO.getDateTo());
             purposes.add(appearanceDTO.getPurpose());


         }

         Leader leader = leaderService.findLeaderByInChargeStatus(true);

         Visitor visitor = visitorService.findById(visitorId);


         AppearanceDTO appearanceDTO= new AppearanceDTO();
         appearanceDTO.setName(visitor.getName());
         appearanceDTO.setPosition(visitor.getPosition().getName());
         appearanceDTO.setAgency(visitor.getAgency().getName());
         appearanceDTO.setDateIssued(dateTimeFormatter.format(LocalDate.now()));
         appearanceDTO.setFormattedStringDate(appearanceDTO.formatStringMultipleDate(datesFrom,datesTo));
         appearanceDTO.setPurpose(appearanceDTO.joinPurpose(purposes));


         model.addAttribute("referer",referer);
         model.addAttribute("appearance",appearanceDTO);
         model.addAttribute("leader",leader);


     }catch (Exception ex){

         redirectAttributes.addFlashAttribute("message", ex.getMessage());
         return String.format("redirect:%s",uri.getPath());

     }

       return "appearances/certificate";
   }

}




