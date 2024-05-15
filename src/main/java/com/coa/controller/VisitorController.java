package com.coa.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class VisitorController {

//    private final VisitorService visitorService;
//    private final PositionService positionService;
//    private final AgencyService agencyService;
//
//    private  VisitorDTO editFormVisitorDTO=new VisitorDTO();
//    @Value("${pageNums}")
//    private List<Integer> pageNums;
//
//    private boolean error;
//
//
//
//
//
//    @InitBinder
//    public void getInitBinder(WebDataBinder webDataBinder){
//        StringTrimmerEditor stringTrimmerEditor=new StringTrimmerEditor(true);
//        webDataBinder.registerCustomEditor(String.class,stringTrimmerEditor);
//    }
//
//    @GetMapping("/visitors")
//    public String showVisitors(Model model, @RequestParam(required = false) String searchName,
//                               @RequestParam(defaultValue = "1") int page,
//                               @RequestParam(defaultValue = "10") int size,
//                               @RequestParam(defaultValue = "id,asc") String[] sort){
//
//        try{
//            String sortField = sort[0];
//            String sortDirection = sort[1];
//
//            Sort.Direction direction=sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
//
//            Sort.Order order=new Sort.Order(direction,sortField);
//
//            Pageable pageable = PageRequest.of(page-1,size,Sort.by(order));
//
//            Page<Visitor> visitorPage;
//            if (searchName == null || searchName.isEmpty()) {
//                visitorPage = visitorService.findAll(pageable);
//            }
//            else {
//                visitorPage = visitorService.findByNameContainingIgnoreCase(searchName, pageable);
//                model.addAttribute("searchName",searchName);
//            }
//
//
//
//            List<VisitorDTO> visitorDTOS = visitorPage.getContent()
//                    .stream()
//                    .map(visitor ->
//                            new VisitorDTO(visitor.getId(),
//                                    visitor.getName(),
//                                    visitor.getPosition().getName(),
//                                    visitor.getAgency().getName()))
//                    .toList();
//
//
//            List<String> positions=positionService.findAll().stream()
//                    .map(Position::getName).toList();
//
//            List<String> agencies=agencyService.findAll().stream()
//                    .map(Agency::getName).toList();
//
//
//            model.addAttribute("addFormVisitorDTO",new VisitorDTO());
//            model.addAttribute("editFormVisitorDTO",editFormVisitorDTO);
//            model.addAttribute("visitors",visitorDTOS);
//            model.addAttribute("positions",positions);
//            model.addAttribute("agencies",agencies);
//            model.addAttribute("currentPage", visitorPage.getNumber() + 1);
//            model.addAttribute("totalPages",visitorPage.getTotalPages());
//            model.addAttribute("pageSize",size);
//            model.addAttribute("error",error);
//            model.addAttribute("sortField", sortField);
//            model.addAttribute("sortDirection", sortDirection);
//            model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
//            model.addAttribute("pageNums", pageNums);
//        }catch (Exception ex){
//            error =true;
//            model.addAttribute("message", ex.getMessage());
//        }
//
//        return "visitors/visitors";
//    }
//
//    @PostMapping("/visitors/save")
//    public String saveVisitor(@ModelAttribute("addFormVisitorDTO") VisitorDTO visitorDTO, @RequestParam("direction")String direction, RedirectAttributes redirectAttributes){
//        try{
//
//            String name=visitorDTO.getName();
//            Optional<Visitor> optionalVisitor=visitorService.findVisitorByName(name);
//
//            if(optionalVisitor.isPresent()){
//                error =true;
//                redirectAttributes.addFlashAttribute("addModalMessage", "Visitor's name already exists!");
//                return  String.format("redirect:/%s",direction);
//
//            }
//
//                Visitor visitor=new Visitor();
//                visitor.setName(visitorDTO.getName());
//
//
//                String visitorDTOPosition=visitorDTO.getPosition();
//                Position position=positionService.findPositionByName(visitorDTOPosition);
//
//                visitor.setPosition(position);
//
//                String visitorDTOAgency = visitorDTO.getAgency();
//                Agency agency=agencyService.findAgencyByName(visitorDTOAgency);
//
//                visitor.setAgency(agency);
//
//                visitorService.save(visitor);
//                error =false;
//                redirectAttributes.addFlashAttribute("message", visitor.getName() + " has been added!");
//
//        } catch (Exception ex) {
//            error =true;
//            redirectAttributes.addFlashAttribute("message", ex.getMessage());
//        }
//
//        return  String.format("redirect:/%s",direction);
//    }
//
//    @GetMapping("/visitors/delete/{id}")
//    public String deleteVisitor(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
//        try{
//            Visitor visitor =visitorService.findById(id);
//            visitorService.deleteById(id);
//            error = false;
//            redirectAttributes.addFlashAttribute("message", visitor.getName() + " has been deleted.");
//
//        } catch (Exception ex){
//            error =true;
//            redirectAttributes.addFlashAttribute("message",ex.getMessage());
//        }
//        return "redirect:/visitors";
//    }
//
//    @PostMapping("/visitors/update")
//    public String updateVisitor(@ModelAttribute("editFormVisitorDTO") VisitorDTO visitorDTO,RedirectAttributes redirectAttributes){
//
//        try{
//
//            Optional<Visitor> optionalVisitor=visitorService.findVisitorByName(
//                    visitorDTO.getId(),visitorDTO.getName());
//
//
//            if(optionalVisitor.isPresent()){
//                editFormVisitorDTO=visitorDTO;
//                redirectAttributes.addFlashAttribute("editModalMessage", "Visitor's name already exists!");
//                return  "redirect:/visitors";
//            }
//
//                Visitor visitor=new Visitor();
//                visitor.setId(visitorDTO.getId());
//                visitor.setName(visitorDTO.getName());
//
//                String visitorDTOPosition=visitorDTO.getPosition();
//                Position position=positionService.findPositionByName(visitorDTOPosition);
//
//                visitor.setPosition(position);
//
//
//                String visitorDTOAgency = visitorDTO.getAgency();
//                Agency agency=agencyService.findAgencyByName(visitorDTOAgency);
//
//                visitor.setAgency(agency);
//
//                visitorService.save(visitor);
//                editFormVisitorDTO=new VisitorDTO();
//                error = false;
//                redirectAttributes.addFlashAttribute("message", visitor.getName() + " has been updated!");
//
//
//        }catch (Exception ex){
//            error =true;
//            redirectAttributes.addFlashAttribute("message",ex.getMessage());
//
//        }
//        return "redirect:/visitors";
//
//    }


}
