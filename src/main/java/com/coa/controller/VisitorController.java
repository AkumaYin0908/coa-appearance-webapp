package com.coa.controller;


import com.coa.dto.VisitorDTO;
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
import org.springframework.web.bind.annotation.RequestParam;

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

        return "visitors";
    }
}
