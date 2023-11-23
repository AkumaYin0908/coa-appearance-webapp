package com.coa.controller;


import com.coa.dto.VisitorDTO;
import com.coa.exception.AgencyNotFoundException;
import com.coa.exception.PositionNotFoundException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;
    private final PositionService positionService;
    private final AgencyService agencyService;

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

            VisitorDTO formVisitorDTO = new VisitorDTO();
            model.addAttribute("formVisitorDTO",formVisitorDTO);
            model.addAttribute("visitors",visitorsDTO);
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
    public String saveVisitor(VisitorDTO visitorDTO, RedirectAttributes redirectAttributes){


        try{
            Visitor visitor=new Visitor();
            visitor.setName(visitorDTO.getName());

            String position=visitorDTO.getPosition();
            Position tempPosition=positionService.findPositionByName(position);

            if(tempPosition == null){
                tempPosition=new Position(visitorDTO.getPosition());
            }

            String agency = visitorDTO.getAgency();
            Agency tempAgency=agencyService.findAgencyByName(agency);
            if(tempAgency==null){
                tempAgency=new Agency(visitorDTO.getAgency());
            }

            visitor.setPosition(tempPosition);
            visitor.setAgency(tempAgency);
            visitorService.save(visitor);
            redirectAttributes.addFlashAttribute("message", visitor.getName() + " has been added!");

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/visitors";
    }
}
