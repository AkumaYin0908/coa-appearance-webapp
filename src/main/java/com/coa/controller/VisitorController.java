package com.coa.controller;


import com.coa.dto.VisitorDTO;
import com.coa.model.Agency;
import com.coa.model.Position;
import com.coa.model.Visitor;
import com.coa.service.AgencyService;
import com.coa.service.PositionService;
import com.coa.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;
    private final PositionService positionService;
    private final AgencyService agencyService;

    private  VisitorDTO editFormVisitorDTO=new VisitorDTO();
    @Value("${pageNums}")
    private List<Integer> pageNums;





    @GetMapping("/visitors")
    public String showVisitors(Model model, @RequestParam(required = false) String searchName,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id,asc") String[] sort){

        try{
            String sortField = sort[0];
            String sortDirection = sort[1];

            Sort.Direction direction=sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

            Sort.Order order=new Sort.Order(direction,sortField);

            Pageable pageable = PageRequest.of(page-1,size,Sort.by(order));

            Page<Visitor> visitorPage;
            if (searchName == null || searchName.isEmpty()) {
                visitorPage = visitorService.findAll(pageable);
            }
            else {
                visitorPage = visitorService.findByNameContainingIgnoreCase(searchName, pageable);
                model.addAttribute("searchName",searchName);
            }



            List<VisitorDTO> visitorsDTO = visitorPage.getContent()
                    .stream()
                    .map(visitor ->
                            new VisitorDTO(visitor.getId(),
                                    visitor.getName(),
                                    visitor.getPosition().getName(),
                                    visitor.getAgency().getName()))
                    .toList();


            List<String> positions=positionService.findAll().stream()
                    .map(Position::getName).toList();

            List<String> agencies=agencyService.findAll().stream()
                    .map(Agency::getName).toList();

            System.out.println(positions);
            System.out.println(agencies);
            model.addAttribute("addFormVisitorDTO",new VisitorDTO());
            model.addAttribute("editFormVisitorDTO",editFormVisitorDTO);
            model.addAttribute("visitors",visitorsDTO);
            model.addAttribute("positions",positions);
            model.addAttribute("agencies",agencies);
            model.addAttribute("currentPage", visitorPage.getNumber() + 1);
            model.addAttribute("totalItems",visitorPage.getTotalElements());
            model.addAttribute("totalPages",visitorPage.getTotalPages());
            model.addAttribute("pageSize",size);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDirection", sortDirection);
            model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
            model.addAttribute("pageNums", pageNums);
        }catch (Exception ex){
            model.addAttribute("message", ex.getMessage());
        }

        return "visitors/visitors";
    }

    @PostMapping("/visitors/save")
    public String saveVisitor(@ModelAttribute("formVisitorDTO") VisitorDTO visitorDTO, RedirectAttributes redirectAttributes){
        try{

            String name=visitorDTO.getName();
            Optional<Visitor> optionalVisitor=visitorService.findVisitorByName(name);

            if(optionalVisitor.isPresent()){
                redirectAttributes.addFlashAttribute("addModalMessage", "Visitor's name already exists!");
                return  "redirect:/visitors";

            }else{


                Visitor visitor=new Visitor();
                visitor.setName(visitorDTO.getName());


                String position=visitorDTO.getPosition();
                Optional<Position> tempPositionOptional=positionService.findPositionByName(position);

                if(tempPositionOptional.isPresent()){
                    visitor.setPosition(tempPositionOptional.get());
                }else{
                    visitor.setPosition(new Position(visitorDTO.getPosition()));
                }


                String agency = visitorDTO.getAgency();
                Optional<Agency> tempAgencyOptional=agencyService.findAgencyByName(agency);

                if(tempAgencyOptional.isPresent()){
                    visitor.setAgency(tempAgencyOptional.get());
                }else{
                    visitor.setAgency(new Agency(visitorDTO.getAgency()));
                }


                visitorService.save(visitor);
                redirectAttributes.addFlashAttribute("message", visitor.getName() + " has been added!");
            }
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            ex.printStackTrace();
        }

        return "redirect:/visitors";
    }

    @GetMapping("/visitors/delete/{id}")
    public String deleteVisitor(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try{
           Optional<Visitor> optionalVisitor=visitorService.findById(id);

            if(optionalVisitor.isPresent()){
                Visitor visitor=optionalVisitor.get();

                String visitorName=visitor.getName();
                visitorService.deleteById(id);
                redirectAttributes.addFlashAttribute("message", visitorName + " has been deleted.");
            }

        } catch (Exception ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
        }
        return "redirect:/visitors";
    }

//    @GetMapping("/visitors/getVisitor/{id}")
//    public void editVisitor(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
//
//            try{
//                Optional<VisitorDTO> optionalVisitorDTO=visitorService.findAndMapToVisitorDTO(id);
//
//                if(optionalVisitorDTO.isPresent()){
//                    VisitorDTO visitor=optionalVisitorDTO.get();
//                    redirectAttributes.addFlashAttribute("editFormVisitorDTO",visitor);
//                }
//
//
//            }catch (Exception ex){
//                redirectAttributes.addFlashAttribute("message",ex.getMessage());
//            }
//
//
//    }
    @PostMapping("/visitors/update")
    public String updateVisitor(@ModelAttribute("formVisitorDTO") VisitorDTO visitorDTO,RedirectAttributes redirectAttributes, Model model){

        try{

            Optional<Visitor> optionalVisitor=visitorService.findVisitorByName(
                    visitorDTO.getId(),visitorDTO.getName());


            if(optionalVisitor.isPresent()){
                editFormVisitorDTO=visitorDTO;
                redirectAttributes.addFlashAttribute("editModalError", "Visitor's name already exists!");
                return  "redirect:/visitors";
            }else{
                Visitor visitor=new Visitor();
                visitor.setId(visitorDTO.getId());
                visitor.setName(visitorDTO.getName());



                String position=visitorDTO.getPosition();
                Optional<Position> tempPositionOptional=positionService.findPositionByName(position);

                if(tempPositionOptional.isPresent()){
                    visitor.setPosition(tempPositionOptional.get());
                }else{
                    visitor.setPosition(new Position(visitorDTO.getPosition()));
                }


                String agency = visitorDTO.getAgency();
                Optional<Agency> tempAgencyOptional=agencyService.findAgencyByName(agency);

                if(tempAgencyOptional.isPresent()){
                    visitor.setAgency(tempAgencyOptional.get());
                }else{
                    visitor.setAgency(new Agency(visitorDTO.getAgency()));
                }

                visitorService.save(visitor);
                editFormVisitorDTO=new VisitorDTO();
                redirectAttributes.addFlashAttribute("message", visitor.getName() + " has been updated!");
            }

        }catch (Exception ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
        }
        return "redirect:/visitors";

    }


}
